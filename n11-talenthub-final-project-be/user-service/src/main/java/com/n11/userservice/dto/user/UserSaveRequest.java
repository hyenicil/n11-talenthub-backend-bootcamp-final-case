package com.n11.userservice.dto.user;

import com.n11.userservice.entity.enums.Gender;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * @author Mehmet Akif Tanisik
 */
public record UserSaveRequest(
        @NotBlank(message = "Name cant be null or blank!")
        @Size(max = 100)
        String  name,

        @NotBlank(message = "Surname cant be null or blank!")
        @Size(max = 100)
        String  surname,

        LocalDate birthDate,

        @Email
        String email,

        @NotNull
        Gender gender
) {
}
