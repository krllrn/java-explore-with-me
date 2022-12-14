package ru.practicum.ewm.general.reg.requests;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exceptions.BadRequestHandler;
import ru.practicum.ewm.exceptions.ForbiddenHandler;
import ru.practicum.ewm.exceptions.NotFoundHandler;
import ru.practicum.ewm.mapper.RequestMapper;
import ru.practicum.ewm.models.event.Event;
import ru.practicum.ewm.models.event.EventStates;
import ru.practicum.ewm.models.request.Request;
import ru.practicum.ewm.models.request.RequestDto;
import ru.practicum.ewm.models.request.RequestStatus;
import ru.practicum.ewm.repositories.EventRepository;
import ru.practicum.ewm.repositories.RequestRepository;
import ru.practicum.ewm.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegRequestServiceImpl implements RegRequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

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
            throw new BadRequestHandler("User ID can't be NULL.");
        }
        if (eventRepository.findByIdIs(eventId) == null) {
            throw new NotFoundHandler("User not found.");
        }
    }

    @Override
    public List<RequestDto> getUserRequests(Long userId) {
        checkUser(userId);
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto addRequestCurrentUser(Long userId, Long eventId) {
        checkUser(userId);
        checkEvent(eventId);
        Event event = eventRepository.findByIdIs(eventId);
        Request request = new Request();
        if (requestRepository.findByRequesterAndEvent(eventId, userId) != null) {
            throw new BadRequestHandler("Request already exist.");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ForbiddenHandler("Initiator can't create request to own event");
        }
        if (!event.getState().equals(EventStates.PUBLISHED)) {
            throw new BadRequestHandler("Can't apply to non published event.");
        }
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        } else {
            request.setStatus(RequestStatus.PENDING);
        }
        request.setRequester(userId);
        request.setEvent(eventId);
        request.setCreated(LocalDateTime.now());
        return requestMapper.entityToDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        checkUser(userId);
        if (requestId == null) {
            throw new BadRequestHandler("Request ID can't be NULL.");
        }
        if (requestRepository.findByIdIs(requestId) == null) {
            throw new NotFoundHandler("Request not found.");
        }
        Request request = requestRepository.findByIdIs(requestId);
        request.setStatus(RequestStatus.CANCELED);
        return requestMapper.entityToDto(requestRepository.save(request));
    }
}
