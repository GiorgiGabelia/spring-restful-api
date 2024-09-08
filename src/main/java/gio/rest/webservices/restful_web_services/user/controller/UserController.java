package gio.rest.webservices.restful_web_services.user.controller;

import com.fasterxml.jackson.annotation.JsonView;
import gio.rest.webservices.restful_web_services.user.User;
import gio.rest.webservices.restful_web_services.view.Views;
import gio.rest.webservices.restful_web_services.user.dto.UserDto;
import gio.rest.webservices.restful_web_services.user.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    @JsonView(Views.Public.class)
    public ResponseEntity<Object> createUser(
            @RequestBody
            @Valid UserDto userDto) {
        User user = this.userService.createUser(new User(userDto.getName(), userDto.getBirthDate()));
        return ResponseEntity.created(getUriLocation(user.getId())).build();
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public ResponseEntity<List<UserDto>> findUsers(@RequestParam(required = false, defaultValue = "")
                                                   String name,
                                                   @RequestParam(required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   LocalDate bornFrom,
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                   @RequestParam(required = false)
                                                   LocalDate bornTo) {
        List<UserDto> users = this.userService.findUsers(name, bornFrom, bornTo)
                .stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @ResponseBody
    public ResponseEntity<UserDto> findUser(@PathVariable Long id) {
        UserDto userDto = modelMapper.map(this.userService.findUserById(id), UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @ResponseBody
    public ResponseEntity<Object> findUserV2(@PathVariable Long id) {
        UserDto userDto = modelMapper.map(this.userService.findUserById(id), UserDto.class);
        return ResponseEntity.ok(new Object() {
            public final String name = userDto.getName();
            public final LocalDate birthDate = userDto.getBirthDate();
        });
    }

    @PatchMapping(value = "/update-user/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<Object> updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        User user = userService.updateById(id, userDto.getName(), userDto.getBirthDate());
        return ResponseEntity.noContent()
                .location(getUriLocation(user.getId()))
                .build();
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
