package gio.rest.webservices.restful_web_services.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import gio.rest.webservices.restful_web_services.user.Views;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserDto {
    @GeneratedValue
    @JsonView(Views.Internal.class)
    private long id;

    @JsonProperty("userName")
    @NotNull(message = "Name is necessary")
    @Size(min = 3, max = 50)
    private String name;

    @NotNull(message = "Birthdate is necessary")
    @Past
    private LocalDate birthDate;

    public UserDto() {
    }

    public UserDto(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
