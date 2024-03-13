package com.n11.userservice.dto.user;

import com.n11.userservice.entity.enums.Gender;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * @author Mehmet Akif Tanisik
 */
public record UserResponse(
        Long id,
        String  name,
        String  surname,
        LocalDate birthDate,
        String email,
        Gender gender
) {
}
