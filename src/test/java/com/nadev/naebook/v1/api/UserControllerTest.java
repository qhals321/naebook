package com.nadev.naebook.v1.api;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.v1.auth.dto.SessionUser;
import com.nadev.naebook.v1.domain.Tag;
import com.nadev.naebook.v1.domain.user.Role;
import com.nadev.naebook.v1.domain.user.User;
import com.nadev.naebook.v1.dto.ProfileRequestDto;
import com.nadev.naebook.v1.repository.TagRepository;
import com.nadev.naebook.v1.repository.user.RelationRepository;
import com.nadev.naebook.v1.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
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

  private static final String BASE_URL = "/api/v1/";
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RelationRepository relationRepository;
  @Autowired
  private TagRepository tagRepository;
  private MockHttpSession session;
  private User user;

  @BeforeEach
  void setUp() {
    Tag develop = Tag.of("develop");
    Tag hello = Tag.of("hello");
    tagRepository.save(develop);
    tagRepository.save(hello);
    user = User.builder()
        .role(Role.USER)
        .name("bom")
        .email("bomdani9302@gmail.com")
        .build();
    user.addTag(develop);
    user.addTag(hello);
    session = new MockHttpSession();
    userRepository.save(user);
    session.setAttribute("user", new SessionUser(user));
  }

  @AfterEach
  void afterEach() {
    relationRepository.deleteAll();
    userRepository.deleteAll();
    session.clearAttributes();
  }

  @Test
  @WithMockUser
  @DisplayName("Session이 잘 작동이 되는 지 테스트")
  public void userSessionTest() throws Exception {
    mockMvc.perform(
        get(BASE_URL + "profile")
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
        patch(BASE_URL + "profile/update")
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
        patch(BASE_URL + "profile/update")
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

  @Test
  @WithMockUser
  @DisplayName("이메일이 비어있을 때 오류")
  public void followFail() throws Exception {
    //given
    String followerEmail = "";
    //when
    mockMvc.perform(post(BASE_URL + "follow")
        .contentType(MediaType.APPLICATION_JSON)
        .content(followerEmail)
        .session(session)
    ).andExpect(status().is4xxClientError());
    //then
  }

  @Test
  @WithMockUser
  public void followTest() throws Exception {
    //given
    Long expectedFolloweeCount = 0L;
    Long expectedFollowerCount = 1L;
    String followerEmail = "bomin_93@naver.com";
    userRepository.save(
        User.builder()
            .email(followerEmail)
            .name("")
            .role(Role.USER)
            .build()
    );
    //when
    mockMvc.perform(post(BASE_URL + "follow")
        .contentType(MediaType.APPLICATION_JSON)
        .content(followerEmail)
        .session(session)
    ).andExpect(status().isOk());
    //then
    Long followeeCount = relationRepository.countFollowee(user);
    Long followerCount = relationRepository.countFollower(user);
    Assertions.assertThat(followeeCount).isEqualTo(expectedFolloweeCount);
    Assertions.assertThat(followerCount).isEqualTo(expectedFollowerCount);
  }

  @Test
  @WithMockUser
  @DisplayName("유저에게 태그가 잘 들어있는 지 확인")
  public void tagTest() throws Exception {
    //given
    String tagName = "한강";
    //when
    mockMvc.perform(post(BASE_URL + "userTag")
        .contentType(MediaType.APPLICATION_JSON)
        .content(tagName)
        .session(session)
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("data").isArray())
        .andExpect(jsonPath("data").value(hasItem("한강")))
        .andExpect(jsonPath("data").value(hasItem("develop")))
        .andExpect(jsonPath("data").value(hasItem("hello")));

    User user = userRepository.findAllByEmail(this.user.getEmail()).get();
    Assertions.assertThat(user.contains("한강")).isTrue();
    Assertions.assertThat(user.contains("develop")).isTrue();
    Assertions.assertThat(user.contains("hello")).isTrue();
  }
}