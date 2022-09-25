package ru.practicum.ewm.general.reg.requests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.models.request.RequestDto;
import ru.practicum.ewm.repositories.RequestRepository;

import java.util.List;

@Service
public class RegRequestServiceImpl implements RegRequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RegRequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<RequestDto> getUserRequests(Long userId) {
        return null;
    }

    @Override
    public RequestDto addRequestCurrentUser(Long userId, Long eventId) {
        return null;
    }

    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {
        return null;
    }
}
