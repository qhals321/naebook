package com.nadev.naebook.library;

import com.nadev.naebook.domain.Tag;
import com.nadev.naebook.library.model.TagModel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagRepository tagRepository;

  @GetMapping
  public ResponseEntity getTags() {
    List<Tag> tags = tagRepository.findAll();
    List<TagModel> models = tags.stream()
        .map(TagModel::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(models);
  }
}
