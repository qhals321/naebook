package com.nadev.naebook.library.dto;

import com.nadev.naebook.domain.library.BookAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestDto {

  private BookAccess access;
}
