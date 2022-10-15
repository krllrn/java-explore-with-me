package ru.practicum.ewm.general.reg.requests;

import ru.practicum.ewm.models.request.RequestDto;

import java.util.List;

public interface RegRequestService {
    List<RequestDto> getUserRequests(Long userId);

    RequestDto addRequestCurrentUser(Long userId, Long eventId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
