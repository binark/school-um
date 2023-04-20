package com.binark.school.usermanagement.publisher;

import org.springframework.kafka.core.ProducerFactory;

public interface Ipublisher <T> {

    void publsh(T data);

}
