package com.nadev.naebook.v1.service;

import com.nadev.naebook.v1.repository.library.UserBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {

  private final UserBookRepository libraryRepository;
}
