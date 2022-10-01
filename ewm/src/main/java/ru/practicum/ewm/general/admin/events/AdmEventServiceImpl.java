package ru.practicum.ewm.general.admin.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.event.AdminUpdateEventRequest;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventFullDto;
import ru.practicum.ewm.models.event.EventStates;
import ru.practicum.ewm.repositories.EventRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdmEventServiceImpl implements AdmEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @PersistenceContext
    private final EntityManager entityManagerFactory;

    @Autowired
    public AdmEventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.entityManagerFactory = entityManager;
    }

    @Override
    public List<EventFullDto> getEvents(Map<String, String> parameters) {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (parameters.get("users") != null) {
            List<Long> usersIds = Arrays.stream(parameters.get("users").split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            predicates.add((Predicate) criteriaQuery.select(root).where(root.get("id").in(usersIds)));
        }
        if (parameters.get("states") != null) {
            List<String> states = Arrays.stream(parameters.get("states").split(","))
                    .collect(Collectors.toList());
            predicates.add((Predicate) criteriaQuery.select(root).where(root.get("state").in(states)));
        }
        if (parameters.get("categories") != null) {
            List<Long> categoryIds = Arrays.stream(parameters.get("categories").split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            predicates.add((Predicate) criteriaQuery.select(root).where(root.get("category").in(categoryIds)));
        }
        if (parameters.get("rangeStart") != null) {
            LocalDateTime rangeStart = LocalDateTime.parse(parameters.get("rangeStart"));
            predicates.add((Predicate) criteriaQuery.select(root).where(criteriaBuilder.greaterThan(root.get("eventDate"),
                     rangeStart)));
        }
        if (parameters.get("rangeEnd") != null) {
            LocalDateTime rangeEnd = LocalDateTime.parse(parameters.get("rangeEnd"));
            predicates.add((Predicate) criteriaQuery.select(root).where(criteriaBuilder.lessThan(root.get("eventDate"),
                    rangeEnd)));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[] {}));
        List<Event> eventsList = entityManagerFactory.createQuery(criteriaQuery).getResultList();
        return eventsList.stream()
                .skip(Long.parseLong(parameters.get("from")))
                .limit(Long.parseLong(parameters.get("size")))
                .map(eventMapper::entityToFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto editEvent(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("Event not found.");
        }
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
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("Event not found.");
        }
        Event eventToUpdate = eventRepository.findByIdIs(eventId);
        if (eventToUpdate.getEventDate().minusHours(1).isBefore(LocalDateTime.now())) {
            throw new ForbiddenHandler("Event start is before 1 hour of publish date.");
        }
        if (!eventToUpdate.getState().equals(EventStates.PENDING)) {
            throw new ForbiddenHandler("Event isn't PENDING state.");
        }
        eventToUpdate.setState(EventStates.PUBLISHED);
        return eventMapper.entityToFullDto(eventRepository.save(eventToUpdate));
    }

    /*
    событие не должно быть опубликовано.
     */
    @Override
    public EventFullDto rejectEvent(Long eventId) {
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("Event not found.");
        }
        Event eventToUpdate = eventRepository.findByIdIs(eventId);
        if (eventToUpdate.getState().equals(EventStates.PUBLISHED)) {
            throw new ForbiddenHandler("Event can't be published again.");
        }
        eventToUpdate.setState(EventStates.CANCELED);
        return eventMapper.entityToFullDto(eventRepository.save(eventToUpdate));
    }
}
