package ru.practicum.ewm.general.reg.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.request.RequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class RegRequestController {

    private final RegRequestService regRequestService;

    @Autowired
    public RegRequestController(RegRequestService regRequestService) {
        this.regRequestService = regRequestService;
    }

    @GetMapping("/{userId}/requests")
    public List<RequestDto> getUserRequests(@PathVariable Long userId) {
        log.info("Get requests from user with ID: {}", userId);
        return regRequestService.getUserRequests(userId);
    }

    /*
    нельзя добавить повторный запрос
    инициатор события не может добавить запрос на участие в своём событии
    нельзя участвовать в неопубликованном событии
    если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
    если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
     */
    @PostMapping("/{userId}/requests")
    public RequestDto addRequestCurrentUser(@PathVariable Long userId, @RequestParam Long eventId) {
        log.info("Add request to event with ID: {}", eventId);
        return regRequestService.addRequestCurrentUser(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Cancel request with ID: {}", requestId);
        return regRequestService.cancelRequest(userId, requestId);
    }
}
