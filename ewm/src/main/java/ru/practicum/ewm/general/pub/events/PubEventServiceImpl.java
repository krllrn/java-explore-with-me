package ru.practicum.ewm.general.pub.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.statistic.Statistic;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PubEventServiceImpl implements PubEventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final Statistic statistic;

    @PersistenceContext
    private final EntityManager entityManagerFactory;

    @Autowired
    public PubEventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, Statistic statistic, EntityManager entityManagerFactory) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.statistic = statistic;
        this.entityManagerFactory = entityManagerFactory;
    }

    // Need check if it works
    public List<EventShortDto> getEventsPredicate(Map<String, String> parameters, HttpServletRequest request) {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Event> criteriaQuery = criteriaBuilder.createQuery(Event.class);
        Root<Event> root = criteriaQuery.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (parameters.get("text") != null) {
            predicates.add((Predicate) criteriaQuery.select(root)
                    .where(criteriaBuilder.or(criteriaBuilder.like(root.get("annotation"), parameters.get("text").toLowerCase()),
                            criteriaBuilder.like(root.get("description"), parameters.get("text").toLowerCase()))));
        }
        if (parameters.get("categories") != null) {
            List<Long> categoriesId = Arrays.stream(parameters.get("categories").split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            predicates.add((Predicate) criteriaQuery.select(root).where(root.get("categories").in(categoriesId)));
        }
        if (parameters.get("paid") != null) {
            predicates.add((Predicate) criteriaQuery.select(root)
                    .where(criteriaBuilder.equal(root.get("paid"), parameters.get("paid"))));
        }
        if (parameters.get("rangeStart") != null && parameters.get("rangeEnd") != null) {
            LocalDateTime rangeStart = LocalDateTime.parse(parameters.get("rangeStart"));
            LocalDateTime rangeEnd = LocalDateTime.parse(parameters.get("rangeEnd"));
            predicates.add((Predicate) criteriaQuery.select(root).where(criteriaBuilder.and(
                    criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart),
                    criteriaBuilder.lessThan(root.get("eventDate"), rangeEnd))));
        } else {
            predicates.add((Predicate) criteriaQuery.select(root).where(criteriaBuilder.greaterThan(root.get("eventDate"),
                    LocalDateTime.now())));
        }
        if (parameters.get("onlyAvailable") != null) {
            predicates.add((Predicate) criteriaQuery.select(root).where(criteriaBuilder.lessThan(root.get("participantLimit"),
                    root.get("confirmedRequests"))));
        }
        if (parameters.get("sort") != null) {
            if (parameters.get("sort").equals("EVENT_DATE")) {
                predicates.add((Predicate) criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("eventDate"))));
            } else if (parameters.get("sort").equals("VIEWS"))
                predicates.add((Predicate) criteriaQuery.select(root).orderBy(criteriaBuilder.desc(root.get("views"))));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[] {}));
        List<Event> eventsList = entityManagerFactory.createQuery(criteriaQuery).getResultList();
        return eventsList.stream()
                .filter(e -> e.getState().equals(EventStates.PUBLISHED))
                .skip(Long.parseLong(parameters.get("from")))
                .limit(Long.parseLong(parameters.get("size")))
                .map(eventMapper::entityToShort)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> getEvents(Map<String, String> parameters, HttpServletRequest request) {
        Set<Event> eventList = new TreeSet<>();
        if (parameters.get("text") != null) {
            eventList.addAll(eventRepository.findAll().stream()
                    .filter(event -> event.getAnnotation().toLowerCase().equals(parameters.get("text").toLowerCase()))
                    .sorted(Comparator.comparing(Event::getId))
                    .collect(Collectors.toList()));
        }
        if (parameters.get("categories") != null) {
            List<Long> categoriesId = Arrays.stream(parameters.get("categories").split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            for (Long i : categoriesId) {
                eventList.addAll(eventRepository.findAll().stream()
                        .filter(event -> event.getCategory().getId().equals(i))
                        .sorted(Comparator.comparing(Event::getId))
                        .collect(Collectors.toList()));
            }
        }
        if (parameters.get("paid") != null) {
            Boolean paid = Boolean.valueOf(parameters.get("paid"));
            eventList.addAll(eventRepository.findAll().stream()
                    .filter(event -> event.getPaid().equals(paid))
                    .sorted(Comparator.comparing(Event::getId))
                    .collect(Collectors.toList()));
        }
        if (parameters.get("rangeStart") != null && parameters.get("rangeEnd") != null) {
            LocalDateTime rangeStart = LocalDateTime.parse(parameters.get("rangeStart"));
            LocalDateTime rangeEnd = LocalDateTime.parse(parameters.get("rangeEnd"));
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
        if (parameters.get("sort") != null) {
            if (parameters.get("sort").equals("EVENT_DATE")) {
                eventList.addAll(eventRepository.findAll().stream()
                        .filter(event -> event.getEventDate().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Event::getEventDate).reversed())
                        .collect(Collectors.toList()));
            } else if (parameters.get("sort").equals("VIEWS")) {
                eventList.addAll(eventRepository.findAll().stream()
                        .sorted(Comparator.comparing(Event::getViews).reversed())
                        .collect(Collectors.toList()));
            }
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
