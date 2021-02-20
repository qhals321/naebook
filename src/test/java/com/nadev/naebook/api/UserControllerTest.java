package com.nadev.naebook.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.auth.dto.SessionUser;
import com.nadev.naebook.domain.user.Role;
import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.dto.ProfileRequestDto;
import com.nadev.naebook.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
class UserControllerTest {

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

  @AfterEach
  void afterEach() {
    userRepository.deleteAll();
    session.clearAttributes();
  }

  @Test
  @WithMockUser
  @DisplayName("Session이 잘 작동이 되는 지 테스트")
  public void userSessionTest() throws Exception {
    mockMvc.perform(
        get("/api/profile")
            .contentType(MediaType.APPLICATION_JSON)
            .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value(user.getName()))
        .andExpect(jsonPath("email").value(user.getEmail()));
  }

  @Test
  @WithMockUser
  @DisplayName("이름이 비었을 때 결과 확인")
  public void userUpdateFail() throws Exception {
    //given
    ProfileRequestDto profileRequestDto = new ProfileRequestDto();
    profileRequestDto.setName("");
    profileRequestDto.setBio("");
    profileRequestDto.setPicture("");
    //when
    mockMvc.perform(
        patch("/api/profile/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(profileRequestDto))
            .session(session)
    )
    //then
        .andExpect(status().is4xxClientError());
  }

  @Test
  @WithMockUser
  @DisplayName("user update 성공")
  public void userUpdate() throws Exception {
    //given
    String expectedBio = "hi, this is bomin";
    String expectedName = "bomin";
    String expectedPicture = "1234";
    ProfileRequestDto profileRequestDto = new ProfileRequestDto();
    profileRequestDto.setBio("hi, this is bomin");
    profileRequestDto.setName("bomin");
    profileRequestDto.setPicture("1234");

    //when
    mockMvc.perform(
        patch("/api/profile/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(profileRequestDto))
            .session(session)
    )
    //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value(expectedName))
        .andExpect(jsonPath("bio").value(expectedBio))
        .andExpect(jsonPath("picture").value(expectedPicture))
        .andExpect(jsonPath("email").value(user.getEmail()));
  }
}