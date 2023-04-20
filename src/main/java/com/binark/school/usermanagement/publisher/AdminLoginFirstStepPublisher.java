package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.config.AdminLoginProcess;
import com.binark.school.usermanagement.dto.AdminLoginDTO;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Lazy
@RequiredArgsConstructor
public class AdminLoginFirstStepPublisher implements Ipublisher<String> {

    private final KafkaTemplate<String, Map> template;

    private static final String TOPIC = "admin-login";

    @Autowired
    private AdminLoginProcess adminLoginProcess;

    @Override
    public void publsh(String email) {

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", this.adminLoginProcess.getCredential());

        this.template.send(TOPIC, map);
    }
}
