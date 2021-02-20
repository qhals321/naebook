package com.nadev.naebook.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ProfileRequestDto {

  @NotEmpty
  private String name;
  private String picture;
  private String bio;
}
