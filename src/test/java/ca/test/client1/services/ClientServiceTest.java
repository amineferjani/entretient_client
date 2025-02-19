package ca.test.client1.services;

import ca.test.client1.entities.Client;
import ca.test.client1.exceptions.NotFoundResourceException;
import ca.test.client1.repositories.ClientRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @InjectMocks
    private ClientService clientService;
    @Mock
    private ClientRepo clientRepo;
    private Client client;
    @BeforeEach
    void setUp() {
        client = Client.builder()
                .firstName("John")
                .Email("john@example.com")
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getClients() {
        //given
        List<Client> clientList = Arrays.asList(client);
        when(clientRepo.findAll()).thenReturn(clientList);
        //when
        List<Client> clients = clientService.getClients();
        //then
        assertNotNull(clients);
        assertEquals(clientList.size(), clients.size());
        assertTrue(clients.contains(client));
        verify(clientRepo,times(1)).findAll();
    }

    @Test
    void addClient() {
        //given
        when(clientRepo.save(client)).thenReturn(client);
        //when
        Client clientResult = clientService.addClient(client);
        //then
        assertNotNull(clientResult);
        assertEquals(client, clientResult);
        verify(clientRepo,times(1)).save(client);
    }

    @Test
    void getClientByIdWhenExists() {
        //given
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepo.existsById(1L)).thenReturn(true);
        //when
        Client clientResult = clientService.getClientById(1L);
        //then
        assertNotNull(clientResult);
        assertEquals(client, clientResult);
        verify(clientRepo,times(1)).findById(1L);
        verify(clientRepo,times(1)).existsById(1L);
    }
    @Test
    void getClientByIdWhenDoeseNotExists() {
        //given

        when(clientRepo.existsById(1L)).thenReturn(false);
        //when //then
        NotFoundResourceException exception=assertThrows(NotFoundResourceException.class, () -> clientService.getClientById(1L));
        assertEquals("Client with id " + 1L + " not found", exception.getMessage());
        verify(clientRepo,times(1)).existsById(1L);
    }


    @Test
    void deleteClientByIdWhenExists() {
        //given
        when(clientRepo.existsById(1L)).thenReturn(true);
        //when
        clientService.deleteClientById(1L);
        //then
        verify(clientRepo,times(1)).deleteById(1L);
        verify(clientRepo,times(1)).existsById(1L);
    }
    @Test
    void deleteClientByIdWhenDoeseNotExists() {
        //given
        when(clientRepo.existsById(1L)).thenReturn(false);
        //when
        //then
        NotFoundResourceException exception=assertThrows(NotFoundResourceException.class, () -> clientService.deleteClientById(1L));
        assertEquals("Client with id " + 1L + " not found", exception.getMessage());
        verify(clientRepo,times(1)).existsById(1L);
    }
    @Test
    void updateClientByIdWhenExists() {
        //given
        when(clientRepo.existsById(1L)).thenReturn(true);
        when(clientRepo.save(client)).thenReturn(client);
        //when
        Client clientResult = clientService.updateClientById(1L, client);
        //then
        assertNotNull(clientResult);
        assertEquals(client, clientResult);
        verify(clientRepo,times(1)).existsById(1L);
        verify(clientRepo,times(1)).save(client);
    }
    @Test
    void updateClientByIdWhenDoeseNotExists() {
        //given
        when(clientRepo.existsById(1L)).thenReturn(false);
        //when//then
        NotFoundResourceException exception=assertThrows(NotFoundResourceException.class, () -> clientService.updateClientById(1L, client));
        assertEquals("Client with id " + 1L + " not found", exception.getMessage());
        verify(clientRepo,times(1)).existsById(1L);

    }
}
