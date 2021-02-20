package com.nadev.naebook.service;

import com.nadev.naebook.auth.dto.SessionUser;
import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.dto.ProfileRequestDto;
import com.nadev.naebook.exception.UserNotFoundException;
import com.nadev.naebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User changeProfile(SessionUser user, ProfileRequestDto requestDto) {
    User findUser = userRepository.findByEmail(user.getEmail())
        .orElseThrow(UserNotFoundException::new);

    findUser.changeProfile(requestDto.getName(), requestDto.getPicture(), requestDto.getBio());
    return findUser;
  }
}

