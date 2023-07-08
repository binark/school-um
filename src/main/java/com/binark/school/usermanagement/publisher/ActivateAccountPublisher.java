package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ActivateAccountPublisher implements Ipublisher<Account> {

    private final KafkaTemplate<String, Map> template;

    private static final String TOPIC = "account-activation";

    @Override
    public void publish(Account account) {

        Map<String, Object> map = new HashMap<>();
        map.put("email", account.getIdentifier());
        map.put("lastname", account.getLastname());

        this.template.send(TOPIC, map);
    }
}
