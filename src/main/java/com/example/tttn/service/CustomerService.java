package com.example.tttn.service;

import com.example.tttn.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    CustomerDto saveOrUpdate(CustomerDto customerDto);

    CustomerDto findCustomer(String username);
}
