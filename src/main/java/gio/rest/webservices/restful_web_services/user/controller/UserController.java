package gio.rest.webservices.restful_web_services.user.controller;

import gio.rest.webservices.restful_web_services.user.User;
import gio.rest.webservices.restful_web_services.user.dto.UserDto;
import gio.rest.webservices.restful_web_services.user.service.UserService;
import gio.rest.webservices.restful_web_services.user.service.UtilService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UtilService utilService;

    public UserController(ModelMapper modelMapper, UserService userService, UtilService utilService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.utilService = utilService;
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User user = this.userService.createUser(new User(userDto.getName(), utilService.isoToLocalDate(userDto.getBirthDate())));
        return ResponseEntity.created(getUriLocation(user.getId())).build();
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(required = false, defaultValue = "") String name,
                                                   @RequestParam(required = false) String bornFrom,
                                                   @RequestParam(required = false) String bornTo) {
        List<UserDto> users = this.userService.findUsers(name, bornFrom, bornTo)
                .stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}")
    @ResponseBody
    public ResponseEntity<UserDto> findUser(@PathVariable Long id) {
        UserDto userDto = modelMapper.map(this.userService.findUserById(id), UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping(value = "/update-user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        userService.updateById(id, userDto.getName(), utilService.isoToLocalDate(userDto.getBirthDate()));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/delete-user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private URI getUriLocation(long id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}
