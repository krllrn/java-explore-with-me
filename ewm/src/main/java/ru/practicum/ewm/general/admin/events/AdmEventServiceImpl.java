package ru.practicum.ewm.general.admin.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.repositories.CommentRepository;
import ru.practicum.ewm.repositories.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdmEventServiceImpl implements AdmEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @PersistenceContext
    private final EntityManager entityManagerFactory;

    @Autowired
    public AdmEventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, CommentRepository commentRepository,
                               CommentMapper commentMapper, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.entityManagerFactory = entityManager;
    }

    private void checkEvent(Long eventId) {
        if (eventId == null) {
            throw new BadRequestHandler("Event ID can't be NULL.");
        }
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("Event not found.");
        }
    }

    @Override
    public List<EventFullDto> getEvents(Map<String, String> parameters) {
        Set<Event> eventList = new TreeSet<>();
        if (parameters.get("users") != null) {
            String[] users = parameters.get("users").split(",");
            for (String s : users) {
                eventList.addAll(eventRepository.findEventsByInitiator(Long.parseLong(s)));
            }
        }
        if (parameters.get("states") != null) {
            String[] states = parameters.get("states").split(",");
            for (String s : states) {
                eventList.addAll(eventRepository.findEventsByState(s));
            }
        }
        if (parameters.get("categories") != null) {
            String[] categories = parameters.get("categories").split(",");
            for (String s : categories) {
                eventList.addAll(eventRepository.findEventsByCategory(Long.valueOf(s)));
            }
        }
        if (parameters.get("rangeStart") != null && parameters.get("rangeEnd") != null) {
            LocalDateTime rangeStart = LocalDateTime.parse(parameters.get("rangeStart"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime rangeEnd = LocalDateTime.parse(parameters.get("rangeEnd"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            eventList.addAll(eventRepository.findAll().stream()
                    .filter(event -> event.getEventDate().isAfter(rangeStart) && event.getEventDate().isBefore(rangeEnd))
                    .sorted(Comparator.comparing(Event::getId))
                    .collect(Collectors.toList()));
        } else {
            eventList.addAll(eventRepository.findAll().stream()
                    .filter(event -> event.getEventDate().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(Event::getId))
                    .collect(Collectors.toList()));
        }

        return eventList.stream()
                .skip(Long.parseLong(parameters.get("from")))
                .limit(Long.parseLong(parameters.get("size")))
                .map(eventMapper::entityToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto editEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        checkEvent(eventId);
        Event eventToUpdate = eventRepository.findByIdIs(eventId);

        return eventMapper.entityToFullDto(eventRepository.save(
                eventMapper.updateToEntity(eventToUpdate, adminUpdateEventRequest))
        );
    }

    /*
    дата начала события должна быть не ранее чем за час от даты публикации.
    событие должно быть в состоянии ожидания публикации
     */
    @Override
    public EventFullDto publishEvent(Long eventId) {
        checkEvent(eventId);
        Event eventToUpdate = eventRepository.findByIdIs(eventId);
        if (eventToUpdate.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ForbiddenHandler("Event start is before 1 hour of publish date.");
        }
        if (!eventToUpdate.getState().equals(EventStates.PENDING)) {
            throw new ForbiddenHandler("Event isn't PENDING state.");
        }
        eventToUpdate.setState(EventStates.PUBLISHED);
        eventToUpdate.setPublishedOn(LocalDateTime.now());
        return eventMapper.entityToFullDto(eventRepository.save(eventToUpdate));
    }

    /*
    событие не должно быть опубликовано.
     */
    @Override
    public EventFullDto rejectEvent(Long eventId) {
        checkEvent(eventId);
        Event eventToUpdate = eventRepository.findByIdIs(eventId);
        if (eventToUpdate.getState().equals(EventStates.PUBLISHED)) {
            throw new ForbiddenHandler("Event can't be published again.");
        }
        eventToUpdate.setState(EventStates.CANCELED);
        return eventMapper.entityToFullDto(eventRepository.save(eventToUpdate));
    }

    @Override
    public CommentDto editComment(Long eventId, Long commentId, CommentShortDto commentShortDto) {
        checkEvent(eventId);
        if (commentId == null) {
            throw new BadRequestHandler("Comment ID can't be NULL.");
        }
        if (commentRepository.findByIdIs(commentId) == null) {
            throw new NotFoundHandler("Comment not found.");
        }
        if (!commentRepository.findByIdIs(commentId).getEventId().equals(eventId)) {
            throw new ForbiddenHandler("Incorrect comment and event ID's.");
        }
        Comment comment = commentRepository.findByIdIs(commentId);
        comment.setText(commentShortDto.getText());
        return commentMapper.entityToDto(commentRepository.save(comment));
    }
}
