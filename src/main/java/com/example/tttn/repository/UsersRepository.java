package com.example.tttn.repository;

import com.example.tttn.dto.UserDto;
import com.example.tttn.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    Users findUsersById(Long id);

    @Query("select (count(u)>0) from Users u where u.username = ?1")
    boolean existsByUsername(String username);

    @Query("select new com.example.tttn.dto.UserDto(ed) from Users ed")
    List<UserDto> getAll();
}
