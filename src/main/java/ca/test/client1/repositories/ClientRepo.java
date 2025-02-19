package ca.test.client1.repositories;

import ca.test.client1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
}
