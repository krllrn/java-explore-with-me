package ru.practicum.ewm.general.reg.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.ConflictHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.CommentMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.models.comment.Comment;
import ru.practicum.ewm.models.comment.CommentDto;
import ru.practicum.ewm.models.comment.CommentShortDto;
import ru.practicum.ewm.models.event.*;
import ru.practicum.ewm.models.request.Request;
import ru.practicum.ewm.models.request.RequestDto;
import ru.practicum.ewm.models.request.RequestStatus;
import ru.practicum.ewm.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegEventServiceImpl implements RegEventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public RegEventServiceImpl(EventRepository eventRepository, UserRepository userRepository, RequestRepository requestRepository,
                               EventMapper eventMapper, RequestMapper requestMapper, CommentMapper commentMapper, CommentRepository commentRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
        this.eventMapper = eventMapper;
        this.requestMapper = requestMapper;
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    private void checkUser(Long userId) {
        if (userId == null) {
            throw new BadRequestHandler("User ID can't be NULL.");
        }
        if (userRepository.findByIdIs(userId) == null) {
            throw new NotFoundHandler("User not found.");
        }
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
    public List<EventShortDto> getCurrentUserEvents(Long userId, Integer from, Integer size) {
        checkUser(userId);
        return eventRepository.findEventsByInitiator(userId).stream()
                .map(eventMapper::entityToShort)
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    /*
    изменить можно только отмененные события или события в состоянии ожидания модерации
    если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
    дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @Override
    public EventFullDto editEvent(Long userId, UpdateEventRequest updateEventRequest) {
        checkUser(userId);
        Event event = eventRepository.findByIdIs(updateEventRequest.getId());
        if (event.getState().equals(EventStates.PUBLISHED)) {
            throw new BadRequestHandler("Can be changed only CANCELED or PENDING events.");
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestHandler("Event date can't be earlier than 2 hours from now.");
        }
        return eventMapper.entityToFullDto(eventRepository.save(eventMapper.updateReqToEntity(event, updateEventRequest)));
    }

    /*
    Обратите внимание: дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     */
    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        checkUser(userId);
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestHandler("Event date can't be earlier than 2 hours from now.");
        }
        Event event = eventRepository.save(eventMapper.newToEntity(userRepository.findByIdIs(userId), newEventDto));
        event.setLocation(newEventDto.getLocation());
        return eventMapper.entityToFullDto(event);
    }

    @Override
    public EventFullDto getEventByIdCurrentUser(Long userId, Long eventId) {
        checkUser(userId);
        checkEvent(eventId);
        if (!eventRepository.findByIdIs(eventId).getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Only initiator can request information.");
        }

        return eventMapper.entityToFullDto(eventRepository.findByIdIs(eventId));
    }

    /*
    Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.
     */
    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        checkUser(userId);
        checkEvent(eventId);
        if (!eventRepository.findByIdIs(eventId).getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Only initiator can request information.");
        }
        if (!eventRepository.findByIdIs(eventId).getState().equals(EventStates.PENDING)) {
            throw new BadRequestHandler("Can be canceled only event which need moderation.");
        }
        Event event = eventRepository.findByIdIs(eventId);
        event.setState(EventStates.CANCELED);

        return eventMapper.entityToFullDto(eventRepository.save(event));
    }

    @Override
    public List<RequestDto> getEventRequests(Long userId, Long eventId) {
        checkUser(userId);
        checkEvent(eventId);
        if (!eventRepository.findByIdIs(eventId).getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Only initiator can request information.");
        }

        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::entityToDto)
                .collect(Collectors.toList());
    }

    /*
    если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
    нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
    если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
    */
    @Override
    public RequestDto confirmEventRequest(Long userId, Long eventId, Long reqId) {
        checkUser(userId);
        checkEvent(eventId);
        Event event = eventRepository.findByIdIs(eventId);
        if (reqId == null) {
            throw new BadRequestHandler("Request ID can't be NULL");
        }
        if (requestRepository.findByIdIs(reqId) == null) {
            throw new NotFoundHandler("Request not found.");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Only initiator can request information.");
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictHandler("Limit of requests.");
        }
        Request request = requestRepository.findByIdIs(reqId);
        request.setStatus(RequestStatus.CONFIRMED);
        requestRepository.save(request);
        if (event.getConfirmedRequests() == event.getParticipantLimit()) {
            for (Request r : requestRepository.findAllByEventIdAndStatus(eventId, RequestStatus.PENDING)) {
                r.setStatus(RequestStatus.REJECTED);
                requestRepository.save(r);
            }
        }
        return requestMapper.entityToDto(requestRepository.findByIdIs(reqId));
    }

    @Override
    public RequestDto rejectEventRequest(Long userId, Long eventId, Long reqId) {
        checkUser(userId);
        checkEvent(eventId);
        Event event = eventRepository.findByIdIs(eventId);
        if (reqId == null) {
            throw new BadRequestHandler("Request ID can't be NULL");
        }
        if (requestRepository.findByIdIs(reqId) == null) {
            throw new NotFoundHandler("Request not found.");
        }
        if (!event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Only initiator can request information.");
        }
        Request request = requestRepository.findByIdIs(reqId);
        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);

        return requestMapper.entityToDto(requestRepository.findByIdIs(reqId));
    }

    @Override
    public CommentDto addComment(Long userId, Long eventId, CommentShortDto commentShortDto) {
        checkUser(userId);
        checkEvent(eventId);

        return commentMapper.entityToDto(commentRepository.save(
                commentMapper.shortToEntity(userRepository.findByIdIs(userId).getName(), eventId, commentShortDto)));
    }

    @Override
    public CommentDto editComment(Long userId, Long eventId, Long commentId, CommentShortDto commentShortDto) {
        checkUser(userId);
        checkEvent(eventId);
        if (!commentRepository.findByIdIs(commentId).getAuthorName().equals(userRepository.findByIdIs(userId).getName())) {
            throw new ForbiddenHandler("Comment can be changed only by owner.");
        }
        Comment comment = commentRepository.findByIdIs(commentId);
        comment.setText(commentShortDto.getText());
        return commentMapper.entityToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long eventId, Long commentId) {
        checkUser(userId);
        checkEvent(eventId);
        if (!commentRepository.findByIdIs(commentId).getAuthorName().equals(userRepository.findByIdIs(userId).getName())) {
            throw new ForbiddenHandler("Comment can be changed only by owner.");
        }
        commentRepository.delete(commentRepository.findByIdIs(commentId));
    }
}
