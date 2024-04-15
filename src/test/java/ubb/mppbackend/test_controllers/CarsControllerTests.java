package ubb.mppbackend.test_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ubb.mppbackend.business.CarsService;
import ubb.mppbackend.controllers.CarsController;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.models.car.Car;
import ubb.mppbackend.models.user.User;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarsController.class)
public class CarsControllerTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private CarsService carsService;

    @Test
    public void testGetCarSuccess() throws Exception {
        User testUser = new User("test", "test", "test", 23);
        Car testCar = new Car("mazda", "miata", 2002, 2000, "test.jpg", 24323, "diesel");
        testCar.setOwner(testUser);

        Mockito.when(carsService.getCarById(1L)).thenReturn(testCar);

        mockMvc.perform(get("/api/cars/getCar/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.brand", is("mazda")));
    }

    @Test
    public void testGetCarNotFound() throws Exception {
        Mockito.when(carsService.getCarById(1L)).thenThrow(new RepositoryException("Car not found"));

        mockMvc.perform(get("/api/cars/getCar/1"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCarSuccess() throws Exception {
        User testUser = new User("test", "test", "test", 23);
        Car testCar = new Car("mazda", "miata", 2002, 2000, "test.jpg", 24323, "diesel");
        testCar.setOwner(testUser);
        testCar.setId(1L);

        Mockito.when(carsService.getCarById(1L)).thenReturn(testCar);

        mockMvc.perform(put("/api/cars/updateCar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(testCar)))
            .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCarNotFound() throws Exception {
        User testUser = new User("test", "test", "test", 23);
        Car testCar = new Car("mazda", "miata", 2002, 2000, "test.jpg", 24323, "diesel");
        testCar.setOwner(testUser);
        testCar.setId(1L);

        Mockito.when(carsService.getCarById(1L)).thenThrow(new RepositoryException("Car not found"));

        mockMvc.perform(put("/api/cars/updateCar")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(testCar)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCarsByOwnerIdSuccess() throws Exception {
        User testUser = new User("test", "test", "test", 23);
        Car testCar = new Car("mazda", "miata", 2002, 2000, "test.jpg", 24323, "diesel");
        testCar.setOwner(testUser);

        Mockito.when(carsService.getAllCarsByOwnerId(1L)).thenReturn(List.of(testCar));

        mockMvc.perform(get("/api/cars/getAllByOwnerId/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].brand", is("mazda")));
    }
}
