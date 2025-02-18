package com.msa.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.msa.accounts.DTO.AccContactDTO;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication //MainClass!
@EnableFeignClients
@EnableJpaAuditing(auditorAwareRef="auditAwareImpl")
@EnableConfigurationProperties(value={AccContactDTO.class})
@OpenAPIDefinition(
	info = @Info(
		title = "Accounts MSA",
		description = "API Documentation",
		version = "v1",
		contact = @Contact(
			name = "Liza",
			email = "hhdhfd"
		),
		license = @License(
			name = "Apache 2.0"
		)
	)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
