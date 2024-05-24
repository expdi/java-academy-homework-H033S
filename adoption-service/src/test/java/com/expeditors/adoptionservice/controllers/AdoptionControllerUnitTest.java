package com.expeditors.adoptionservice.controllers;

import com.expeditors.adoptionservice.domain.entities.Adopter;
import com.expeditors.adoptionservice.domain.entities.Adoption;
import com.expeditors.adoptionservice.domain.entities.Pet;
import com.expeditors.adoptionservice.dto.adoption.AddOrUpdateAdoptionRequestDTO;
import com.expeditors.adoptionservice.factory.JsonConverter;
import com.expeditors.adoptionservice.factory.TestFactory;
import com.expeditors.adoptionservice.service.implementation.AdopterServiceImpl;
import com.expeditors.adoptionservice.service.implementation.AdoptionServiceImpl;
import com.expeditors.adoptionservice.service.implementation.PetServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdoptionControllerUnitTest {
    
    @MockBean
    private AdoptionServiceImpl adoptionService;

    @MockBean
    private PetServiceImpl petService;

    @MockBean
    private AdopterServiceImpl adopterService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /adoption/all - Success")
    public void getAllAdoption_ReturnsOk() throws Exception{
        
        var mockAdoption1 = TestFactory.getAdoptionInstance();
        var mockAdoption2 = TestFactory.getAdoptionInstance();
        var mockListOfAdoption = List.of(
                mockAdoption1,
                mockAdoption2);

        Mockito.doReturn(mockListOfAdoption)
                .when(adoptionService)
                .getAllEntities();

        mockMvc.perform(get("/adoption/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].adopter.id", is(1)))
                .andExpect(jsonPath("$.[0].pet.id", is(1)));
    }

    @Test
    @DisplayName("GET /adoption/{id} - Found")
    public void getAdoptionById_ReturnOk_WithCorrectId() throws Exception {
        var mockAdoption1 = TestFactory.getAdoptionInstance();

        Mockito.doReturn(mockAdoption1)
                .when(adoptionService)
                .getEntityById(1);

        mockMvc.perform(get("/adoption/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.adopter.id", is(1)))
                .andExpect(jsonPath("$.pet.id", is(1)));
    }

    @Test
    @DisplayName("GET /adoption/{id} - Not Found")
    public void getAdoptionById_ReturnNotFound_WithIncorrectId() throws Exception {

        Mockito.doReturn(null)
                .when(adoptionService)
                .getEntityById(1);

        mockMvc.perform(get("/adoption/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /adoption - Success")
    public void addAdoption_ReturnsOk_WithValidObject() throws Exception {
        var mockDate = LocalDate.now().plusDays(1);
        var mockAdoptionRequest = AddOrUpdateAdoptionRequestDTO
                .builder()
                .adoptionDate(mockDate)
                .petId(1)
                .adopterId(1)
                .build();

        var mockAdopter = TestFactory.getAdopterInstance();
        var mockPet = TestFactory.getPetInstance();
        var mockAdoption = new Adoption(1, mockAdopter, mockPet, mockDate);

        Mockito.doReturn(mockAdopter)
                .when(adopterService)
                .getEntityById(1);
        Mockito.doReturn(mockPet)
                .when(petService)
                .getEntityById(1);
        Mockito.doReturn(mockAdoption)
                .when(adoptionService)
                .addEntity(any());

        var request = mockMvc.perform(
                post("/adoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.fromObject(mockAdoptionRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /adoption - Success")
    public void addAdoption_ReturnsBadRequest_WithInvalidObject() throws Exception {
        var mockDate = LocalDate.now().plusDays(1);
        var mockAdoptionRequest = AddOrUpdateAdoptionRequestDTO
                .builder()
                .adoptionDate(mockDate)
                .petId(1)
                .adopterId(1)
                .build();

        var mockAdopter = TestFactory.getAdopterInstance();
        var mockPet = TestFactory.getPetInstance();
        var mockAdoption = new Adoption(1, mockAdopter, mockPet, mockDate);

        Mockito.doReturn(null)
                .when(adopterService)
                .getEntityById(anyInt());
        Mockito.doReturn(null)
                .when(petService)
                .getEntityById(anyInt());
        Mockito.doReturn(mockAdoption)
                .when(adoptionService)
                .addEntity(any());

        mockMvc.perform(post("/adoption")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.fromObject(mockAdoptionRequest)))
                .andExpect(status().isBadRequest());

    }


    @Test
    @DisplayName("PUT /adoption/{id} - Success")
    public void updateAdoption_ReturnsOk_WhenIdIsFoundAndBodyIsValidToGenerateAdoption() throws Exception {

        var mockAdopter = TestFactory.getAdopterInstance();
        var mockPet = TestFactory.getPetInstance();
        var mockAdoption = new Adoption(1, mockAdopter, mockPet, LocalDate.now().plusDays(1));
        var mockAdoptionRequest = JsonConverter.fromObject(
                AddOrUpdateAdoptionRequestDTO
                        .builder()
                        .adoptionDate(LocalDate.now().plusDays(1))
                        .adopterId(1)
                        .petId(1)
                        .build()
        );

        setMocksForServicesThatRequestIdFor(mockAdoption, mockPet, mockAdopter);
        Mockito.doReturn(true)
                .when(adoptionService)
                .updateEntity(any());

        mockMvc.perform(
                put("/adoption/{adoptionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mockAdoptionRequest)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /adoption/{id} - Bad Request")
    public void updateAdoption_ReturnsBadRequest_WhenAdoptionIdIsNotFound() throws Exception {

        var mockAdoption = TestFactory.getAdoptionInstance();
        Mockito.doReturn(null)
                .when(adoptionService)
                .getEntityById(anyInt());

        mockMvc.perform(
                put("/adoption/{adoptionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.fromObject(mockAdoption))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /adoption/{id} - Bad Request - adoptionId Not Found")
    public void updateAdoption_ReturnsBadRequest_WhenAdopterIdIsNotFound() throws Exception {

        var mockAdoption = TestFactory.getAdoptionInstance();
        Mockito.doReturn(null)
                .when(adoptionService)
                .getEntityById(anyInt());

        mockMvc.perform(
                        put("/adoption/{adoptionId}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonConverter.fromObject(mockAdoption))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /adoption/{id} - Bad Request - adopterId not Found")
    public void updateAdoption_ReturnsBadRequest_WhenAdopterIdNotFound() throws Exception {

        var mockAdopter = TestFactory.getAdopterInstance();
        var mockPet = TestFactory.getPetInstance();
        var mockAdoption = new Adoption(1, mockAdopter, mockPet, LocalDate.now().plusDays(1));

        setMocksForServicesThatRequestIdFor(mockAdoption, mockPet, null);
        mockMvc.perform(
                put("/adoption/{adoptionId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConverter.fromObject(mockAdoption)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("PUT /adoption/{id} - Bad Request - petId not Found")
    public void updateAdoption_ReturnsBadRequest_WhenPetIdNotFound() throws Exception {

        var mockAdopter = TestFactory.getAdopterInstance();
        var mockPet = TestFactory.getPetInstance();
        var mockAdoption = new Adoption(1, mockAdopter, mockPet, LocalDate.now().plusDays(1));

        setMocksForServicesThatRequestIdFor(mockAdoption, null, mockAdopter);
        mockMvc.perform(
                        put("/adoption/{adoptionId}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(JsonConverter.fromObject(mockAdoption)))
                .andExpect(status().isBadRequest());

    }

    private void setMocksForServicesThatRequestIdFor(Adoption mockAdoption, Pet mockPet, Adopter mockAdopter) {

        Mockito.doReturn(mockAdoption)
                .when(adoptionService)
                .getEntityById(anyInt());
        Mockito.doReturn(mockPet)
                .when(petService)
                .getEntityById(anyInt());
        Mockito.doReturn(mockAdopter)
                .when(adopterService)
                .getEntityById(anyInt());
    }

    @Test
    @DisplayName("DELETE /adoption/{id} - Success")
    public void deleteAdoption_ReturnsOk_WhenIdIsFound() throws Exception {

        var mockAdoption1 = TestFactory.getAdoptionInstance();

        Mockito.doReturn(mockAdoption1)
                .when(adoptionService).getEntityById(1);
        Mockito.doReturn(true)
                .when(adoptionService).deleteEntity(1);

        mockMvc.perform(delete("/adoption/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /adoption/{id} - Not Found")
    public void deleteAdoption_ReturnsNotFound_WhenIdIsNotFound() throws Exception {

        Mockito.doReturn(null)
                .when(adoptionService).getEntityById(1);

        mockMvc.perform(delete("/adoption/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /adoption/{id} - Internal Server Error")
    public void deleteAdoption_ReturnsInternalServerError_WhenIdIsFoundButWasNotDeleted() throws Exception {
        var mockAdoption1 = TestFactory.getAdoptionInstance();

        Mockito.doReturn(mockAdoption1)
                .when(adoptionService).getEntityById(1);
        Mockito.doReturn(false)
                .when(adoptionService).deleteEntity(1);

        mockMvc.perform(delete("/adoption/{id}", 1))
                .andExpect(status().isInternalServerError());
    }


}
