package ca.test.client1.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Getter
@Setter
@Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String Email;
}
