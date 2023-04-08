package com.binark.school.usermanagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("parent")
public class Parent extends Account{
}
