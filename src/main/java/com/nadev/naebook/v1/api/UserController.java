package com.nadev.naebook.v1.api;

import com.nadev.naebook.v1.auth.LoginUser;
import com.nadev.naebook.v1.auth.dto.SessionUser;
import com.nadev.naebook.v1.dto.ProfileRequestDto;
import com.nadev.naebook.v1.dto.ResponseDto;
import com.nadev.naebook.v1.exception.UserNotFoundException;
import com.nadev.naebook.v1.service.UserService;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@CrossOrigin(value = "http://localhost:8080")
public class UserController {

  private final UserService userService;

  @PatchMapping("/profile/update")
  public SessionUser updateProfile(@LoginUser SessionUser user,
      @RequestBody @Validated ProfileRequestDto profileRequestDto) {
    user.changeProfile(profileRequestDto);
    userService.changeProfile(user);
    return user;
  }

  @GetMapping("/profile")
  public SessionUser getProfile(@LoginUser SessionUser user) {
    if(user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  @PostMapping("/follow")
  public ResponseDto<HttpStatus> followUser(@LoginUser SessionUser user, @RequestBody @NotEmpty String followeeEmail) {
    userService.follow(user, followeeEmail);
    return new ResponseDto<>(HttpStatus.OK);
  }

  @PostMapping("/userTag")
  public ResponseDto<List<String>> addUserTag(@LoginUser SessionUser user, @RequestBody @NotEmpty String title) {
    userService.addTag(user, title);
    user.addTag(title);
    return new ResponseDto<>(user.getTags());
  }

}
