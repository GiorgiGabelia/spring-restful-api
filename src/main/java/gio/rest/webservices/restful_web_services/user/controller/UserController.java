package gio.rest.webservices.restful_web_services.user.controller;

import com.fasterxml.jackson.annotation.OptBoolean;
import gio.rest.webservices.restful_web_services.user.User;
import gio.rest.webservices.restful_web_services.user.dto.UserDto;
import gio.rest.webservices.restful_web_services.user.service.UserService;
import gio.rest.webservices.restful_web_services.utils.UtilService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @PostMapping(value="/create-user")
    public void createUser(@RequestBody UserDto userDto) {
        User user = new User(userDto.getName(), utilService.isoToLocalDate(userDto.getBirthDate()));
        this.userService.createUser(user);
    }

    @GetMapping(value="/users")
    @ResponseBody
    public List<UserDto> findUsers(@RequestParam(required = false, defaultValue = "") String name,
                                   @RequestParam(required = false) String bornFrom,
                                   @RequestParam(required = false) String bornTo) {
        return this.userService.findUsers(name, bornFrom, bornTo)
                .stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @GetMapping(value="/user/{id}")
    @ResponseBody
    public UserDto findUser(@PathVariable Long id) {
        // TODO: implement error handling more efficiently
        User user = this.userService.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return modelMapper.map(user, UserDto.class);
    }

    @PatchMapping(value="/update-user/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserDto userDto) {
        userService.updateById(id, userDto.getName(), utilService.isoToLocalDate(userDto.getBirthDate()));
    }

    @DeleteMapping(value="/delete-user/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}
