package gio.rest.webservices.restful_web_services.user;

import gio.rest.webservices.restful_web_services.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserCommandLineRunner implements CommandLineRunner {
    private final UserService userService;

    public UserCommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
//        addDummyUsersForFirstTime();
    }

    private void addDummyUsersForFirstTime() {
        this.userService.createUser(new User("gio", LocalDate.now().minusYears(25)));
        this.userService.createUser(new User("givi", LocalDate.now().minusYears(22)));
        this.userService.createUser(new User("esma", LocalDate.now().minusYears(23)));
        this.userService.createUser(new User("sandro", LocalDate.now().minusYears(23)));
        this.userService.createUser(new User("john", LocalDate.now().minusYears(45)));
    }
}

