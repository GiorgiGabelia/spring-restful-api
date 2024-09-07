package gio.rest.webservices.restful_web_services.user.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UtilService {
    public LocalDate isoToLocalDate(String simpleISODate) {
        if (simpleISODate == null) return null;
        return LocalDate.parse(simpleISODate, DateTimeFormatter.ISO_DATE);
    }
}
