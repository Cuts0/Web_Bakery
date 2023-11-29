package com.example.tttn.service.impl;

import com.example.tttn.dto.CustomerDto;
import com.example.tttn.entity.Users;
import com.example.tttn.repository.RoleRepository;
import com.example.tttn.repository.UsersRepository;
import com.example.tttn.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public CustomerDto saveOrUpdate(CustomerDto dto) {
        Users customer = new Users();
        if (dto.getId() != null) {
            customer = usersRepository.findUsersById(dto.getId());
            customer.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        } else {
            customer = new Users();
            customer.setUsername(dto.getUsername());
            customer.setPassword(passwordEncoder.encode(dto.getPassword()));
            customer.setRoles(Collections.singletonList(roleRepository.findByName("CUSTOMER")));
            customer.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setPhone(dto.getPhone());
        customer.setAddress(dto.getAddress());

        customer = usersRepository.save(customer);
        return new CustomerDto(customer);
    }

    @Override
    public CustomerDto findCustomer(String username) {
        return new CustomerDto(usersRepository.findByUsername(username));
    }
}
