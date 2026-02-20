package com.app.ClimbTracker.climb;

import com.app.ClimbTracker.user.CustomUserDetailsService;
import com.app.ClimbTracker.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ClimbController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ClimbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClimbRepository climbRepository;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testGetMyClimb_Returns200AndJsonList() throws Exception {
        Climb dummyClimb = new Climb("V5", "Slab", true, LocalDate.now());

        Mockito.when(climbRepository.findByUserUsername("john")).thenReturn(List.of(dummyClimb));

        Principal mockPrincipal = Mockito.mock(Principal.class);

        Mockito.when(mockPrincipal.getName()).thenReturn("john");

        mockMvc.perform(get("/api/climbs")
                        .principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grade").value("V5"))
                .andExpect(jsonPath("$[0].style").value("Slab"));

    }
}
