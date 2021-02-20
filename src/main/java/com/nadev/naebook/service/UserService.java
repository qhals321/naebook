package com.nadev.naebook.service;

import com.nadev.naebook.auth.dto.SessionUser;
import com.nadev.naebook.domain.Relation;
import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.dto.ProfileRequestDto;
import com.nadev.naebook.exception.UserNotFoundException;
import com.nadev.naebook.repository.RelationRepository;
import com.nadev.naebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;
  private final RelationRepository relationRepository;

  @Transactional
  public User changeProfile(SessionUser user, ProfileRequestDto requestDto) {
    User findUser = userRepository.findByEmail(user.getEmail())
        .orElseThrow(UserNotFoundException::new);

    findUser.changeProfile(requestDto.getName(), requestDto.getPicture(), requestDto.getBio());
    return findUser;
  }

  @Transactional
  public Relation follow(SessionUser currentUser, String followeeEmail) {
    User follower = currentUser.toUserEntity();
    User followee = userRepository.findByEmail(followeeEmail)
        .orElseThrow(UserNotFoundException::new);

    return relationRepository.save(Relation.of(follower, followee));
  }
}

