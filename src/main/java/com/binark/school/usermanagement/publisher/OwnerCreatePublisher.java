package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.entity.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OwnerCreatePublisher implements Ipublisher<Owner>{

    private final KafkaTemplate<String, Map> template;

    private static final String TOPIC = "create-owner";

    @Override
    public void publish(Owner ownerAccount) {

        Map<String, Object> map = new HashMap<>();
        map.put("email", ownerAccount.getEmail());
        map.put("resetPasswordKey", ownerAccount.getResetPasswordKey());
        map.put("lastname", ownerAccount.getLastname());
        map.put("schoolCount", ownerAccount.getAuthorizedSchool());
        map.put("slug", ownerAccount.getSlug());

        this.template.send(TOPIC, map);
    }
}
