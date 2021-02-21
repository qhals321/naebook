package com.nadev.naebook.v1.service;

import com.nadev.naebook.v1.auth.dto.SessionUser;
import com.nadev.naebook.v1.domain.user.Relation;
import com.nadev.naebook.v1.domain.Tag;
import com.nadev.naebook.v1.domain.user.User;
import com.nadev.naebook.v1.domain.user.UserTag;
import com.nadev.naebook.v1.exception.UserNotFoundException;
import com.nadev.naebook.v1.repository.user.RelationRepository;
import com.nadev.naebook.v1.repository.TagRepository;
import com.nadev.naebook.v1.repository.user.UserRepository;
import com.nadev.naebook.v1.repository.user.UserTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;
  private final RelationRepository relationRepository;
  private final TagRepository tagRepository;
  private final UserTagRepository userTagRepository;

  @Transactional
  public User changeProfile(SessionUser user) {
    User findUser = userRepository.findByEmail(user.getEmail())
        .orElseThrow(UserNotFoundException::new);

    findUser.changeProfile(user.getName(), user.getPicture(), user.getBio());
    return findUser;
  }

  @Transactional
  public Relation follow(SessionUser currentUser, String followeeEmail) {
    User follower = currentUser.toUserEntity();
    User followee = userRepository.findByEmail(followeeEmail)
        .orElseThrow(UserNotFoundException::new);

    return relationRepository.save(Relation.of(follower, followee));
  }

  @Transactional
  public UserTag addTag(SessionUser user, String title) {
    Tag tag = tagRepository.findByTitle(title)
        .orElseGet(() -> tagRepository.save(Tag.of(title)));

    UserTag userTag = UserTag.of(user.toUserEntity(), tag);

    return userTagRepository.save(userTag);
  }
}

