package ru.practicum.ewm.general.admin.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.models.user.NewUserRequest;
import ru.practicum.ewm.models.user.UserDto;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
public class AdmUsersController {
    private final AdmUsersService admUsersService;

    @Autowired
    public AdmUsersController(AdmUsersService admUsersService) {
        this.admUsersService = admUsersService;
    }

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                                  @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
                                  @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {

        log.info("Get users.");
        return admUsersService.getUsers(ids, from, size);
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
