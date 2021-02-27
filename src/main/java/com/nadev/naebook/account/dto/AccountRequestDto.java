package com.nadev.naebook.account.dto;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRequestDto {
  @NotEmpty
  private String name;
  private String picture;
  private String bio;
}
