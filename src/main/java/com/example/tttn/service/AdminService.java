package com.example.tttn.service;

import com.example.tttn.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    UserDto saveOrUpdate(UserDto userDto);

    void deleteById(Long id);

    Page<UserDto> pageUsers(int pageIndex);
}
