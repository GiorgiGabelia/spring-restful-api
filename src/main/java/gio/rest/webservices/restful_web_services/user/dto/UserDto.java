package gio.rest.webservices.restful_web_services.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {
    private long id;

    @Size(min = 3, max = 50)
    @NotNull(message = "Name is necessary")
    private String name;

    @NotNull(message = "Birthdate is necessary")
    private String birthDate;

    public UserDto() {
    }

    public UserDto(String name, String birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
