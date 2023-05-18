package com.binark.school.usermanagement.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
//@DiscriminatorValue("student")
@AttributeOverride(name = "identifier", column = @Column(name = "matricule", nullable = false, unique = true, updatable = false))
public class Student extends Account{
}
