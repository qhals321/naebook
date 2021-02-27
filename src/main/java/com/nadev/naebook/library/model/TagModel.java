package com.nadev.naebook.library.model;

import com.nadev.naebook.domain.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

@Getter @Setter
public class TagModel extends EntityModel<Tag> {

  private String title;

  public TagModel(Tag tag) {
    this.title = tag.getTitle();
  }
}
