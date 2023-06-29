package com.binark.school.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
//@DiscriminatorValue("owner")
@AttributeOverride(name = "identifier", column = @Column(name = "email", nullable = false, unique = true))
@Data
@ToString
public class Owner extends Account{

    @PositiveOrZero(message = "School number can not be a negative number")
    private Integer authorizedSchool;

    @NotBlank(message = "You should provide a phone number")
    private String phoneNumber;

    @Transient
    private String email;

    public String getEmail() {
        return this.identifier;
    }
}
