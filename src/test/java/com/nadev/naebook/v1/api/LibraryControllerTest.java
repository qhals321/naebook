package com.nadev.naebook.v1.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nadev.naebook.v1.auth.dto.SessionUser;
import com.nadev.naebook.v1.domain.Tag;
import com.nadev.naebook.v1.domain.library.AccessStatus;
import com.nadev.naebook.v1.domain.library.BookStatus;
import com.nadev.naebook.v1.domain.user.Role;
import com.nadev.naebook.v1.domain.user.User;
import com.nadev.naebook.v1.repository.TagRepository;
import com.nadev.naebook.v1.repository.user.RelationRepository;
import com.nadev.naebook.v1.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryControllerTest {

  private static final String BASE_URL = "/api/v1/";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  private MockHttpSession session;
  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
        .role(Role.USER)
        .name("bom")
        .email("bomdani9302@gmail.com")
        .build();
    session = new MockHttpSession();
    userRepository.save(user);
    session.setAttribute("user", new SessionUser(user));
  }

  @Test
  @WithMockUser
  @DisplayName("라이브러리 기능에서 유저북 추가시 확인")
  public void userBookCreate() throws Exception {
    mockMvc.perform(post(BASE_URL+"library/booking")
        .contentType(MediaType.APPLICATION_JSON)
        .content("1234567")
        .session(session)
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("isbn").value("1234567"))
        .andExpect(jsonPath("status").value("BOOKING"))
        .andExpect(jsonPath("access").value("PUBLIC"))
        .andExpect(jsonPath("review").isEmpty())
        .andExpect(jsonPath("score").isEmpty());
  }


}