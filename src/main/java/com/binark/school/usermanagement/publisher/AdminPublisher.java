package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.dto.AdminLoginDTO;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

//@Component
//@RequiredArgsConstructor
public class AdminPublisher {

    private final KafkaTemplate<String, AdminLoginDTO> template;

    public AdminPublisher(KafkaTemplate<String, AdminLoginDTO> template) {
        this.template = template;
    }

    // @PostConstruct
    private void setup() {
        TopicBuilder.name("admin-login")
                .partitions(1)
                .replicas(1)
                .build();

        TopicBuilder.name("admin-wrong-email")
                .partitions(1)
                .replicas(1)
                .build();

        TopicBuilder.name("admin-password-failed")
                .partitions(1)
                .replicas(1)
                .build();
    }

    public void publishAdminLoginFirstStep(String email) {
        String password = UUID.randomUUID().toString();
        if (password.length() > 32) {
            password = password.substring(0, 31);
        }
        AdminLoginDTO loginDTO = AdminLoginDTO.builder()
                .password(password)
                .email(email)
                .build();

        this.template.send("admin-login", loginDTO);
    }

    public void publishWrongEmail(String email) {

    }
}
