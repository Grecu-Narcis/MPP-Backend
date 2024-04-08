package ubb.mppbackend.test_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ubb.mppbackend.business.UsersService;
import ubb.mppbackend.controllers.UsersController;
import ubb.mppbackend.exceptions.RepositoryException;
import ubb.mppbackend.exceptions.UserValidatorException;
import ubb.mppbackend.models.user.User;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@WebMvcTest(UsersController.class)
public class UsersControllerTests {
    private final String endPoint = "/api/users";
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean private UsersService usersService;

    @Test
    public void testGetUserSuccess() throws Exception {
        UUID userId = UUID.randomUUID();

        Mockito.when(usersService.getById(userId)).
            thenReturn(new User("test", "test", "test", 23));

        mockMvc.perform(get(endPoint + "/getUser/" + userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("test")));
    }

    @Test
    public void testGetUserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        Mockito.when(usersService.getById(userId)).thenThrow(new RepositoryException("User not found"));

        mockMvc.perform(get(endPoint + "/getUser/" + userId))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPage() throws Exception {
        Mockito.when(usersService.getPage(1, true, 10)).thenReturn(new ArrayList<>());

        mockMvc.perform(get(endPoint + "/getPage?page=1&isAscending=true&pageSize=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(new ArrayList<>())));
    }

    @Test
    public void testGetAll() throws Exception {
        Mockito.when(usersService.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get(endPoint + "/getAll"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(new ArrayList<>())));
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        User user = new User("test", "test", "test", 23);

        mockMvc.perform(post(endPoint + "/addUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk()).andReturn();

        verify(usersService, times(1)).addUser(user);
    }

    @Test
    public void testAddUserFailsValidator() throws Exception {
        User user = new User("te", "t", "test", 0);

        Mockito.doThrow(UserValidatorException.class).when(usersService).addUser(user);

        mockMvc.perform(post(endPoint + "/addUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUserSuccess() throws Exception {
        User user = new User("test", "test", "test", 23);

        mockMvc.perform(put(endPoint + "/updateUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk()).andReturn();

        verify(usersService, times(1)).updateUser(user);
    }

    @Test
    public void testUpdateUserNotFound() throws Exception {
        User user = new User("test", "test", "test", 23);

        Mockito.doThrow(new RepositoryException("User not found!")).when(usersService).updateUser(user);

        mockMvc.perform(put(endPoint + "/updateUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUserFailsValidator() throws Exception {
        User user = new User("test", "test", "test", 0);

        Mockito.doThrow(UserValidatorException.class).when(usersService).updateUser(user);

        mockMvc.perform(put(endPoint + "/updateUser")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteUser() throws Exception {
        UUID idToRemove = UUID.randomUUID();
        mockMvc.perform(delete(endPoint + "/delete/" + idToRemove))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetUsersCount() throws Exception {
        Mockito.when(usersService.getAll()).thenReturn(new ArrayList<>());


        mockMvc.perform(get(endPoint + "/countUsers"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", is(0)));
    }

    @Test
    public void testPingSuccess() throws Exception {
        mockMvc.perform(get(endPoint + "/ping"))
            .andExpect(status().isOk());
    }
}
