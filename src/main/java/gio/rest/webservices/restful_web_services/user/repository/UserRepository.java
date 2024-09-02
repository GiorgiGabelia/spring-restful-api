package gio.rest.webservices.restful_web_services.user.repository;

import gio.rest.webservices.restful_web_services.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findByBirthDate(LocalDate birthDate);
}
