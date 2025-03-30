package com.restaurant.reservation;

import com.restaurant.reservation.model.User;
import com.restaurant.reservation.repository.UserRepository;
import com.restaurant.reservation.service.UserService;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository = mock(UserRepository.class);
    private UserService userService = new UserService();

    @Test
    void testFindByUsername() {
        User user = new User(1L, "testuser", "password", "USER");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void testRegisterUser() {
        User User = new User(1L, "testuser", "password", "USER");
        when(userRepository.save(User)).thenReturn(User);

        User savedUser = userService.saveUser(User);
        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
    }

}
