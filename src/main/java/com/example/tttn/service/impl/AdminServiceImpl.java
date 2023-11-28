package com.example.tttn.service.impl;

import com.example.tttn.dto.UserDto;
import com.example.tttn.entity.Role;
import com.example.tttn.entity.Users;
import com.example.tttn.repository.RoleRepository;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto saveOrUpdate(UserDto userDto) {
        Users users = new Users();
        if (userDto.getId() != null){
            users = usersRepository.findUsersById(userDto.getId());
            users.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }else {
            users = new Users();
            users.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        users.setUsername(userDto.getUsername());
        users.setPassword(passwordEncoder.encode(userDto.getPassword()));
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setPhone(userDto.getPhone());
        users.setAddress(userDto.getAddress());
        List<Role> roles = new ArrayList<>();
        for (Long roleId : userDto.getRoleIds()){
            roles.add(roleRepository.findRoleById(roleId));
        }
        users.setRoles(roles);

        users = usersRepository.save(users);
        return new UserDto(users);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Page<UserDto> pageUsers(int pageIndex) {
        return null;
    }
}
