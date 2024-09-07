package gio.rest.webservices.restful_web_services.user.service;

import gio.rest.webservices.restful_web_services.exception.ResourceNotFoundException;
import gio.rest.webservices.restful_web_services.exception.RequestNotValidException;
import gio.rest.webservices.restful_web_services.user.User;
import gio.rest.webservices.restful_web_services.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final UtilService utilService;

    public UserService(UserRepository userRepo, UtilService utilService) {
        this.userRepo = userRepo;
        this.utilService = utilService;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    public List<User> findUsers(String name, String bornFrom, String bornTo) {
        LocalDate bornFromLocal = bornFrom != null ? utilService.isoToLocalDate(bornFrom) : null;
        LocalDate bornToLocal = bornTo != null ? utilService.isoToLocalDate(bornTo) : null;

        return userRepo.findAll().stream().filter(user -> {
            boolean nameMatches = user.getName().toLowerCase().contains(name.toLowerCase());
            boolean dateMatches = true;

            if (bornFromLocal != null) {
                dateMatches = !user.getBirthDate().isBefore(bornFromLocal);
            }

            if (bornToLocal != null) {
                dateMatches = dateMatches && !user.getBirthDate().isAfter(bornToLocal);
            }

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
        if (newName != null) {
            if (newName.length() >= 3) user.setName(newName);
            else throw new RequestNotValidException("New Name is too short");
        }
        if (newBirthDate != null) user.setBirthDate(newBirthDate);

        userRepo.save(user);
        return user;
    }
}
