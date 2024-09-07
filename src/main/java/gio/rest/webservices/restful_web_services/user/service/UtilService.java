package gio.rest.webservices.restful_web_services.user.service;

import gio.rest.webservices.restful_web_services.exception.RequestNotValidException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UtilService {
    public LocalDate isoToLocalDate(String simpleISODate) {
        if (simpleISODate == null) return null;
        try {
            return LocalDate.parse(simpleISODate, DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            throw new RequestNotValidException("Invalid date format. Please provide simple ISO date");
        }
    }
}
