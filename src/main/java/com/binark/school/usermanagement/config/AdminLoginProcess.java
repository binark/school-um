package com.binark.school.usermanagement.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class AdminLoginProcess {

    private String credential;
}
