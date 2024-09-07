package gio.rest.webservices.restful_web_services.user.service;

import gio.rest.webservices.restful_web_services.exception.ResourceNotFoundException;
import gio.rest.webservices.restful_web_services.user.User;
import gio.rest.webservices.restful_web_services.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findUsers(String name, LocalDate bornFrom, LocalDate bornTo) {
        return userRepo.findAll().stream().filter(user -> {
            boolean nameMatches = user.getName().toLowerCase().contains(name.toLowerCase());
            boolean dateMatches = true;

            if (bornFrom != null) dateMatches = !user.getBirthDate().isBefore(bornFrom);
            if (bornTo != null) dateMatches = dateMatches && !user.getBirthDate().isAfter(bornTo);

            return nameMatches && dateMatches;
        }).toList();
    }

    public User findUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User"));
    }

    public void deleteById(long id) {
        if (!userRepo.existsById(id)) throw new ResourceNotFoundException("User");
        userRepo.deleteById(id);
    }

    public User updateById(long id, String newName, LocalDate newBirthDate) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User"));

        if (newName != null) user.setName(newName);
        if (newBirthDate != null) user.setBirthDate(newBirthDate);

        userRepo.save(user);
        return user;
    }
}
