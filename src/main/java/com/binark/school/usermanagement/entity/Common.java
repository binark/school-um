package com.binark.school.usermanagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("common")
public class Common extends Account{
}
