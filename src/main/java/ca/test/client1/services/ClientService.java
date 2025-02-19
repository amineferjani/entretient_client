package ca.test.client1.services;

import ca.test.client1.entities.Client;
import ca.test.client1.exceptions.NotFoundResourceException;
import ca.test.client1.repositories.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepo clientRepo;

    public List<Client> getClients() {
        return clientRepo.findAll();
    }
    public Client addClient(Client client) {
        return clientRepo.save(client);
    }
    public Client getClientById(long id) {
        if(clientRepo.existsById(id)) {
            return clientRepo.findById(id).get();
        }
        else throw new NotFoundResourceException("Client with id " + id + " not found");
    }
    public void deleteClientById(long id) {
        if(clientRepo.existsById(id)) {
            clientRepo.deleteById(id);
        }
        else throw new NotFoundResourceException("Client with id " + id + " not found");
    }
    public Client updateClientById(long id, Client client) {
        if(clientRepo.existsById(id)) {
            client.setId(id);
            return clientRepo.save(client);
        }
        else throw new NotFoundResourceException("Client with id " + id + " not found");
    }
}
