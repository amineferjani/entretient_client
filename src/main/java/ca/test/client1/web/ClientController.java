package ca.test.client1.web;

import ca.test.client1.entities.Client;
import ca.test.client1.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
@GetMapping("clients/{id}")
 public ResponseEntity<Client> getClient(@PathVariable Long id) {
     return ResponseEntity.ok(clientService.getClientById(id));
 }
 @GetMapping("clients")
 public ResponseEntity<List<Client>> getClients() {
     return ResponseEntity.ok(clientService.getClients());
 }
 @DeleteMapping("clients/{id}")
 public ResponseEntity<String> deleteClient(@PathVariable Long id){
    clientService.deleteClientById(id);
     return ResponseEntity.status(200).body("Client with id " + id + " deleted");
 }
 @PostMapping("clients")
 public ResponseEntity<Client> addClient(@RequestBody Client client){
     return ResponseEntity.status(201).body(clientService.addClient(client));
 }
 @PutMapping("clients/{id}")
 public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
     return ResponseEntity.ok(clientService.updateClientById(id, client));
 }
}
