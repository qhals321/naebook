package com.nadev.naebook.api;

import com.nadev.naebook.auth.LoginUser;
import com.nadev.naebook.auth.dto.SessionUser;
import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.dto.ProfileRequestDto;
import com.nadev.naebook.exception.UserNotFoundException;
import com.nadev.naebook.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class UserController {

  private final UserService userService;

  @PatchMapping("/profile/update")
  public ProfileResponseDto update(@LoginUser SessionUser user,
      @RequestBody @Validated ProfileRequestDto profileRequestDto) {


    User updatedUser = userService.changeProfile(user, profileRequestDto);
    return new ProfileResponseDto(updatedUser);
  }

  @GetMapping("/profile")
  public SessionUser get(@LoginUser SessionUser user) {
    if(user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  @Data
  static class ProfileResponseDto {

    private String email;
    private String name;
    private String picture;
    private String bio;

    public ProfileResponseDto(User user) {
      this.email = user.getEmail();
      this.name = user.getName();
      this.picture = user.getPicture();
      this.bio = user.getBio();
    }
  }

}
