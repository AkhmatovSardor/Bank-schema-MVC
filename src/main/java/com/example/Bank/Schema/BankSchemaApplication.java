package com.example.Bank.Schema;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Bank Schema",
				description = "bank schema",
				contact = @Contact(
						name = "My telegram",
						url = "https://t.me/java_developer09"
				),
				license = @License(
						name = "License",
						url = "http://localhost:1511/swagger-ui/index.html#/"
				),
				version = "version-3.0.9"
		),
		tags = {
				@Tag(
						name = "UniversalSearch",
						description = "page+search"),
				@Tag(
						name = "Get",
						description = "get"),
				@Tag(
						name = "Create",
						description = "create"),
				@Tag(
						name = "Update",
						description = "update"),
				@Tag(
						name = "Delete",
						description = "delete")
		},
		servers = @Server(
				url = "localhost:1511",
				description = "bank schema host"
		)
)
public class BankSchemaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankSchemaApplication.class, args);
	}

}
