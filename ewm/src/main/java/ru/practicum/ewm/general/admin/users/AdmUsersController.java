package ru.practicum.ewm.general.admin.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
public class AdmUsersController {

    private final AdmUsersService admUsersService;

    @Autowired
    public AdmUsersController(AdmUsersService admUsersService) {
        this.admUsersService = admUsersService;
    }

    /*
    Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
    либо о конкретных (учитываются указанные идентификаторы)
     */
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Integer> ids,
                                  @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Map<String, Object> parameters = Map.of(
                "ids", ids,
                "from", from,
                "size", size
        );
        log.info("Get users with parameters: {}", parameters.values());
        return admUsersService.getUsers(parameters);
    }

    @PostMapping
    public UserDto addUser(@RequestBody NewUserRequest newUserRequest) {
        log.info("Add new user with email: {}", newUserRequest.getEmail());
        return admUsersService.addUser(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long userId) {
        log.info("Delete user with ID: {}", userId);
        admUsersService.deleteUser(userId);
    }
}
