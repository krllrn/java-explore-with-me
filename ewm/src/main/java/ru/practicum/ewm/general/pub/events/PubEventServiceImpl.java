package ru.practicum.ewm.general.pub.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.category.Category;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventShortDto;
import ru.practicum.ewm.models.event.EventStates;
import ru.practicum.ewm.models.event.Hit;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.statistic.Statistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PubEventServiceImpl implements PubEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final Statistic statistic;

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public PubEventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, Statistic statistic, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.statistic = statistic;
        this.entityManager = entityManager;
    }

    public List<EventShortDto> getEvents(Map<String, String> parameters, HttpServletRequest request) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);
        Join<Event, Category> categoryJoin = root.join("category");
        List<Predicate> predicates = new ArrayList<>();

        if (parameters.get("text") != null) {
            Predicate findText = criteriaBuilder.or(criteriaBuilder.like(root.get("annotation"), "%" + parameters.get("text") + "%"),
                    criteriaBuilder.like(root.get("description"), "%" +  parameters.get("text") + "%"));
            predicates.add(findText);
        }
        if (parameters.get("categories") != null) {
            List<Long> categoriesId = Arrays.stream(parameters.get("categories").split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            predicates.add(categoryJoin.get("id").in(categoriesId));
        }
        if (parameters.get("paid") != null) {
            predicates.add(criteriaBuilder.equal(root.get("paid"), Boolean.parseBoolean(parameters.get("paid"))));
        }
        if (parameters.get("rangeStart") != null && parameters.get("rangeEnd") != null) {
            LocalDateTime rangeStart = LocalDateTime.parse(parameters.get("rangeStart"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime rangeEnd = LocalDateTime.parse(parameters.get("rangeEnd"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Predicate start = criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
            Predicate end = criteriaBuilder.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
            predicates.add(criteriaBuilder.and(start, end));
        } else {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("eventDate"), LocalDateTime.now()));
        }
        if (parameters.get("onlyAvailable") != null) {
            if (parameters.get("onlyAvailable").equals("true")) {
                predicates.add((criteriaBuilder.lessThan(root.get("participantLimit"), root.get("confirmedRequests"))));
            }
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[] {})));

        List<Event> eventList = entityManager.createQuery(criteriaQuery).getResultList();

        if (parameters.get("sort") != null) {
            List<Event> sortList = new ArrayList<>();
            if (parameters.get("sort").equals("EVENT_DATE")) {
                sortList = eventList.stream()
                        .sorted(Comparator.comparing(Event::getEventDate).reversed())
                        .collect(Collectors.toList());
            } else if (parameters.get("sort").equals("VIEWS")) {
                sortList = eventList.stream()
                        .sorted(Comparator.comparing(Event::getViews).reversed())
                        .collect(Collectors.toList());
            }
            eventList = sortList;
        }
        Hit hit = new Hit("Get events", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        statistic.hit(hit);
        for (Event e : eventList) {
            updateViews(e.getId());
        }
        return eventList.stream()
                .filter(e -> e.getState().equals(EventStates.PUBLISHED))
                .skip(Long.parseLong(parameters.get("from")))
                .limit(Long.parseLong(parameters.get("size")))
                .map(eventMapper::entityToShort)
                .collect(Collectors.toList());
    }

    @Override
    public EventShortDto getEventById(Long eventId, HttpServletRequest request) {
        if (eventId == null) {
            throw new BadRequestHandler("Event ID can't be NULL");
        }
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("Event not found.");
        }
        Hit hit = new Hit("Get event by id", request.getRequestURI(), request.getRemoteAddr(), LocalDateTime.now());
        statistic.hit(hit);
        updateViews(eventId);
        return eventMapper.entityToShort(eventRepository.findByIdIs(eventId));
    }

    private void updateViews(Long eventId) {
        Event event = eventRepository.findByIdIs(eventId);
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
    }
}
