package ca.test.client1.web;

import ca.test.client1.entities.Client;
import ca.test.client1.exceptions.NotFoundResourceException;
import ca.test.client1.repositories.ClientRepo;
import ca.test.client1.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {
    @Mock
    ClientService clientService;
    @InjectMocks
    ClientController clientController;
    MockMvc mockMvc;
    Client client;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
        client = new Client(1L, "John Doe", "john.doe@example.com");
    }

    @Test
    void getClientByIdWhenExists() throws Exception {
        //given
        when(clientService.getClientById(1L)).thenReturn(client);
        //when
        //then
        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

    }
    @Test
    void getClientByIdWhenDoeseNotExists() throws Exception {
        //given
        when(clientService.getClientById(1L)).thenThrow(new NotFoundResourceException("Client with id 1 not found"));

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isNotFound());
               // .andExpect(content().string("Client with id 1 not found"));
        verify(clientService,times(1)).getClientById(1L);
        //when
        //then

    }

    @Test
    void getClients() throws Exception {
        client=new Client(1L, "John Doe", "john.doe@example.com");
        Client client1 = new Client(2L, "Jane Smith", "jane.smith@example.com");
        List<Client> clients = Arrays.asList(client,client1);

        when(clientService.getClients()).thenReturn(clients);

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John Doe"))
                .andExpect(jsonPath("$[1].firstName").value("Jane Smith"));
    }
    @Test
    void deleteClientWhenExists() throws Exception {

     doNothing().when(clientService).deleteClientById(1L);

        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Client with id 1 deleted"));
    }
    @Test
    void deleteClientWhenDoeseNotExists() throws Exception {

        doThrow(new NotFoundResourceException("Client with id 1 not found"))
                .when(clientService).deleteClientById(1L);
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNotFound());
                //.andExpect(content().string("Client with id 1 not found"));
    }

    @Test
    void addClient() throws Exception {
        when(clientService.addClient(any(Client.class))).thenReturn(client);

        String clientJson = "{\"firstName\":\"John Doe\",\"Email\":\"john.doe@example.com\"}";

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void updateClient() throws Exception {
        Client updatedClient = new Client(1L, "John Doe Updated", "john.updated@example.com");
        when(clientService.updateClientById(eq(1L), any(Client.class))).thenReturn(updatedClient);

        String updatedClientJson = "{\"firstName\":\"John Doe Updated\",\"Email\":\"john.updated@example.com\"}";

        mockMvc.perform(put("/api/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John Doe Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"));
    }
}
