package com.binark.school.usermanagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("owner")
@Data
public class Owner extends Account{

}
