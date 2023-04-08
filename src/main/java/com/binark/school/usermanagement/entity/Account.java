package com.binark.school.usermanagement.entity;

import com.binark.school.usermanagement.entity.listener.AuditEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class Account extends AbsctractBaseEntity{

  //  @Email(message = "You should enter a valid email address")
    @NotBlank
    @Column(unique = true, nullable = false, updatable = false)
    protected String identifier;

    @Size(min = 8, max = 32, message = "You password should have at least 8 and at most 32 characters")
    @Column(nullable = false)
    protected String password;

//    @Builder
//    public Account(Long id, String slug, LocalDateTime createAt, LocalDateTime modifiedDate, Boolean enabled, Boolean deleted, String identifier, String password) {
//        super(id, slug, createAt, modifiedDate, enabled, deleted);
//        this.identifier = identifier;
//        this.password = password;
//    }
}
