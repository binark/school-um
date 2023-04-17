package com.binark.school.usermanagement;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
public class UserManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

	@Bean
	public NewTopic accountCreateTopic() {
		return TopicBuilder.name("account-create")
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic adminLoginTopic() {
		return TopicBuilder.name("admin-login")
				.partitions(1)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic adminWrongEmailTopic() {
		return TopicBuilder.name("admin-wrong-email")
				.partitions(1)
				.replicas(1)
				.build();
	}


	//@Bean
	public ApplicationRunner runner(KafkaTemplate<String, Map> template) {

		Map<String, Object> data = new HashMap<>();
		data.put("email", "armelknyobe@gmail.com");
		data.put("lastname", "Binark");

//		ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
//		byte[] s = mapper.writeValueAsBytes(data);

		return args -> {
			template.send("create-account-1", data);
		};
	}

//	@Bean
//	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
//		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
//				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
//				.build());
//	}

}