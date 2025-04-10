package com.appdev.allin.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Pagination {
  public static Pageable generatePageable(Integer page, Integer size, String sortBy, String direction) {
    Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    if (page < 0 || size <= 0) {
      throw new IllegalArgumentException("Page and size must be greater than 0");
    }
    return PageRequest.of(page, size, sort);
  }
}
