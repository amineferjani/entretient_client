package ca.test.client1;

import ca.test.client1.entities.Client;
import ca.test.client1.repositories.ClientRepo;
import ca.test.client1.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Client1Application {

    public static void main(String[] args) {
        SpringApplication.run(Client1Application.class, args);
    }
@Bean
CommandLineRunner init(ClientService clientService) {
        return args -> {
            Client client = Client.builder()
                    .firstName("John")
                    .Email("john@example.com")
                    .build();
            clientService.addClient(client);
            clientService.addClient(Client.builder()
                    .firstName("Dear")
                    .Email("Dear@example.com")
                    .build());
            clientService.addClient(Client.builder()
                    .firstName("Patric")
                    .Email("Patric@example.com")
                    .build());
            clientService.addClient(Client.builder()
                    .firstName("Marie")
                    .Email("Marie@example.com")
                    .build());
        };
}
}
