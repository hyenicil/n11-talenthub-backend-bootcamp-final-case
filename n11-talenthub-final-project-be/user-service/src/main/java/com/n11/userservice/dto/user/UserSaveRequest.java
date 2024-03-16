package com.n11.userservice.dto.user;

import com.n11.userservice.entity.enums.Gender;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

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

        @NotNull(message = "Birth date cant be null")
        @Past(message = "Birth date must be in the past")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate birthDate,

        @NotBlank(message = "Email cant be null or blank!")
        @Email
        String email,

        @NotNull
        Gender gender
) {
}
