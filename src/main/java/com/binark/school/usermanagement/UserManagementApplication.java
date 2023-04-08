package com.binark.school.usermanagement;

import com.binark.school.usermanagement.config.IamServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class UserManagementApplication {


	public static void main(String[] args) {
//		ConfigurableApplicationContext context =
				SpringApplication.run(UserManagementApplication.class, args);
//		IamServerConfig iamServerConfig = context.getBean(IamServerConfig.class);
//		System.out.println("iamServerConfig = " + iamServerConfig);
	}

}
