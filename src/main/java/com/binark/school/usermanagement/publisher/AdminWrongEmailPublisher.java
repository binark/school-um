package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.dto.AdminLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Lazy
@RequiredArgsConstructor
public class AdminWrongEmailPublisher{

    private final KafkaTemplate<String, Map<String, String>> template;

    private static final String TOPIC = "admin-wrong-email";

    public void publsh(String adminMail, String wrongMail) {

        Map<String, String> data = new HashMap<>();
        data.put("admin", adminMail);
        data.put("email", wrongMail);

        this.template.send(TOPIC, data);
    }
}
