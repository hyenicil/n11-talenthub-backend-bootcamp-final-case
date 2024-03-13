package com.n11.userservice.entity;

import com.n11.userservice.common.base.BaseEntity;
import com.n11.userservice.entity.enums.Gender;
import com.n11.userservice.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mehmet Akif Tanisik
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 100)
    @NotNull
    private String  name;
    @Column(name = "surname", length = 100)
    @NotNull
    private String  surname;
    @Column(name = "birth_date")
    @NotNull
    private LocalDate birthDate;
    @Column(name = "email")
    @Email
    @NotNull
    private String email;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addressList;

}
