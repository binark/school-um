package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.dto.AdminLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Lazy
@RequiredArgsConstructor
public class AdminWrongEmailPublisher implements Ipublisher<String>{

    private final KafkaTemplate<String, Map<String, String>> template;

    private static final String TOPIC = "admin-wrong-email";

    @Override
    public void publsh(String email) {

        Map<String, String> data = new HashMap<>();
        data.put("admin", "armelknyobe@gmail.com");
        data.put("email", email);

        this.template.send(TOPIC, data);
    }
}
