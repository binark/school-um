package com.binark.school.usermanagement.publisher;

import com.binark.school.usermanagement.config.AdminLoginProcess;
import com.binark.school.usermanagement.dto.OwnerAccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OwnerCreatePublisher implements Ipublisher<OwnerAccountDTO>{

    private final KafkaTemplate<String, Map> template;

    private static final String TOPIC = "owner-create";

    @Override
    public void publsh(OwnerAccountDTO ownerAccountDTO) {

        Map<String, Object> map = new HashMap<>();
        map.put("email", ownerAccountDTO.getEmail());
        map.put("password", ownerAccountDTO.getPassword());
        map.put("lastname", ownerAccountDTO.getLastname());
        map.put("schoolCount", ownerAccountDTO.getAuthorizedSchool());
        map.put("slug", ownerAccountDTO.getSlug());

        this.template.send(TOPIC, map);
    }
}
