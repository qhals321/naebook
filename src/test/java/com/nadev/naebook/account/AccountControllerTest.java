package com.nadev.naebook.account;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.account.auth.Role;
import com.nadev.naebook.account.dto.AccountRequestDto;
import com.nadev.naebook.account.dto.RelationRequestDto;
import com.nadev.naebook.account.dto.TagRequestDto;
import com.nadev.naebook.config.RestDocsConfiguration;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import com.nadev.naebook.domain.Relation;
import com.nadev.naebook.domain.Tag;
import com.nadev.naebook.library.TagRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@AutoConfigureRestDocs
class AccountControllerTest {

    private static final String BASE_URL = "/api/account";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTagRepository accountTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private TestUser testUser;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    private void afterEach() {
        testUser.deleteAll();
    }

    @Test
    @DisplayName("회원 검색에 실패한다.")
    void search_notFound() throws Exception {
        mockMvc.perform(get("/api/account/-1000")
            .with(oauth2Login())
        )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("회원 검색 성공")
    void search_found() throws Exception {
        Account account = testUser.getAccount();
        mockMvc.perform(get(BASE_URL + "/" + account.getId())
            .with(oauth2Login())
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("email").value(account.getEmail()))
            .andExpect(jsonPath("picture").value(account.getPicture()))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.update-account").exists())
            .andDo(document("account-search"));
    }

    @Test
    @DisplayName("로그인 되어있는 회원 검색")
    void search_self() throws Exception {
        Account account = testUser.getAccount();
        mockMvc.perform(get(BASE_URL + "/me")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("email").value(account.getEmail()))
            .andExpect(jsonPath("picture").value(account.getPicture()))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.update-account").exists())
            .andDo(document("account-me"));
    }

    @Test
    @DisplayName("이름을 비우고 프로필 업데이트하는 경우")
    void updateAccount_notValid() throws Exception {
        String newBio = "changed bio";
        AccountRequestDto requestDto = AccountRequestDto.builder()
            .bio(newBio)
            .build();

        mockMvc.perform(put(BASE_URL)
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto))
        )
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$[*].field").value(Matchers.containsInAnyOrder("name")))
            .andExpect(
                jsonPath("$[*].objectName").value(Matchers.containsInAnyOrder("accountRequestDto")))
            .andExpect(jsonPath("$[*].code").value(Matchers.containsInAnyOrder("NotEmpty")))
            .andExpect(jsonPath("$[*].defaultMessage")
                .value(Matchers.containsInAnyOrder("must not be empty")))
            .andDo(document("account-update-4xx"));
    }

    @Test
    void updateAccount_success() throws Exception {
        String newName = "nabom";
        String newBio = "changed bio";
        String newPicture = "new picture";
        AccountRequestDto requestDto = AccountRequestDto.builder()
            .name(newName)
            .bio(newBio)
            .picture(newPicture)
            .build();

        mockMvc.perform(put(BASE_URL)
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(newName))
            .andExpect(jsonPath("bio").value(newBio))
            .andExpect(jsonPath("picture").value(newPicture))
            .andExpect(jsonPath("_links.self").exists())
            .andDo(document("account-update"));

        Account newAccount = accountRepository.findById(testUser.getAccount().getId())
            .orElseThrow();

        Assertions.assertThat(newAccount.getBio()).isEqualTo(newBio);
        Assertions.assertThat(newAccount.getName()).isEqualTo(newName);
        Assertions.assertThat(newAccount.getPicture()).isEqualTo(newPicture);
    }

    @Test
    void createAccountTag() throws Exception {

        TagRequestDto newTitle = new TagRequestDto("newTitle");

        mockMvc.perform(post(BASE_URL + "/tags")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newTitle))
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("title").value(newTitle.getTitle()))
            .andDo(document("accountTag-create"))
        ;

        Tag tag = tagRepository.findByTitle(newTitle.getTitle()).orElseThrow();
        Assertions.assertThat(tag.getTitle()).isEqualTo(newTitle.getTitle());
        Assertions.assertThat(tag.getInterest()).isEqualTo(1);
    }

    @Test
    void findAccountTagById() throws Exception {
        String newTitle = "newTitle";
        AccountTag accountTag = accountService
            .createAccountTag(testUser.getAccount().getId(), newTitle);
        mockMvc.perform(get(BASE_URL + "/tags/" + accountTag.getId())
            .with(oauth2Login())
        )
            .andDo(print())
            .andExpect(jsonPath("title").value(newTitle))
            .andExpect(jsonPath("_links.self").exists())
            .andExpect(jsonPath("_links.remove-accountTag").exists())
            .andDo(document("accountTag-find"))
        ;
    }

    @Test
    @DisplayName("없는 태그를 삭제 시도시")
    void removeAccountTag_invalidTag() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/tags/-100")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        )
            .andDo(print())
            .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("로그인 되어 있는 유저의 태그가 아닌 다른 태그를 삭제 시도시")
    void removeAccountTag_forbidden() throws Exception {
        Account newAccount = Account.builder()
            .name("newUser")
            .email("bombom")
            .role(Role.USER)
            .build();
        Account savedAccount = accountRepository.save(newAccount);
        AccountTag newTag = accountService.createAccountTag(savedAccount.getId(), "newTag");
        mockMvc.perform(delete(BASE_URL + "/tags/" + newTag.getId())
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        )
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    void removeAccountTag() throws Exception {
        String tag = "newTag";
        AccountTag accountTag = accountService.createAccountTag(testUser.getAccount().getId(), tag);
        mockMvc.perform(delete(BASE_URL + "/tags/" + accountTag.getId())
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("accountTag-remove"));
        Assertions.assertThat(
            accountTagRepository.findById(accountTag.getId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("로그인 되어 있는 유저의 태그들을 가져오기")
    void findAccountTags() throws Exception {
        mockMvc.perform(get(BASE_URL + "/me/tags")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("data").isArray())
            .andDo(document("accountTag-find-me"))
        ;
    }

    @Test
    @DisplayName("없는 유저의 태그들을 요청할 시")
    void findAccountTagByAccountId_fail() throws Exception {
        mockMvc.perform(get(BASE_URL + "/-100/tags")
            .with(oauth2Login())
        )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void findAccountTagByAccountId() throws Exception {
        Long id = testUser.getAccount().getId();
        mockMvc.perform(get(BASE_URL + "/" + id + "/tags")
            .with(oauth2Login())
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("data").isArray())
            .andDo(document("accountTag-findAll"));
    }

    @Test
    void followAccount_NotFound() throws Exception {
        mockMvc.perform(post(BASE_URL + "/follow")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new RelationRequestDto(-100L)))
        )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void followAccount_AlreadyFollow() throws Exception {
        Account account = Account.builder()
            .email("bombom")
            .name("test")
            .bio("hello")
            .role(Role.USER)
            .build();
        Account followee = accountRepository.save(account);

        Relation rel = Relation.of(testUser.getAccount(), followee);
        relationRepository.save(rel);

        mockMvc.perform(post(BASE_URL + "/follow")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new RelationRequestDto(followee.getId())))
        )
            .andDo(print())
            .andExpect(status().is4xxClientError());
    }

    @Test
    void followAccount() throws Exception {
        Account account = Account.builder()
            .email("bombom")
            .name("test")
            .bio("hello")
            .role(Role.USER)
            .build();
        Account followee = accountRepository.save(account);

        mockMvc.perform(post(BASE_URL + "/follow")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new RelationRequestDto(followee.getId())))
        )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("account-follow"));

        Account updatedAccount = accountRepository.findById(followee.getId()).orElseThrow();
        Assertions.assertThat(updatedAccount.getInterest()).isEqualTo(1);
    }


    @Test
    void findFollow() throws Exception {
        Account account = Account.builder()
            .email("bombom")
            .name("test")
            .bio("hello")
            .role(Role.USER)
            .build();
        Account followee = accountRepository.save(account);

        Relation rel = Relation.of(testUser.getAccount(), followee);
        relationRepository.save(rel);

        mockMvc.perform(get(BASE_URL + "/relation/" + rel.getId())
            .with(oauth2Login())
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("account-follows"));
    }

    @Test
    void unfollow_notFound() throws Exception {
        Account account = Account.builder()
            .email("bombom")
            .name("test")
            .bio("hello")
            .role(Role.USER)
            .build();
        Account followee = accountRepository.save(account);

        mockMvc.perform(delete(BASE_URL+"/unfollow")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new RelationRequestDto(followee.getId())))
        )
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void unfollow() throws Exception {
        Account account = Account.builder()
            .email("bombom")
            .name("test")
            .bio("hello")
            .role(Role.USER)
            .build();
        Account followee = accountRepository.save(account);

        Relation relation = accountService
            .followAccount(testUser.getAccount().getId(), followee.getId());

        mockMvc.perform(delete(BASE_URL + "/unfollow")
            .with(oauth2Login().oauth2User(testUser.getAuth2User()))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new RelationRequestDto(account.getId())))
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("account-unfollow"));

        Optional<Relation> deleted = relationRepository.findById(relation.getId());
        Account changedAccount = accountRepository.findById(followee.getId()).orElseThrow();
        Assertions.assertThat(deleted.isEmpty()).isTrue();
        Assertions.assertThat(changedAccount.getInterest()).isEqualTo(0);
    }
}