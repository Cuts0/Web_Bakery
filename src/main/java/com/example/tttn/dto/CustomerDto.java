package com.example.tttn.dto;

import com.example.tttn.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends BaseObjectDto<CustomerDto> {
    @Size(min = 2, max = 15, message = "First name should have 3-15 characters")
    private String firstName;
    @Size(min = 2, max = 15, message = "First name should have 3-15 characters")
    private String lastName;
    private String username;
    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private String password;
    private String phone;
    private String address;

    public CustomerDto(Users entity) {
        if (entity != null) {
            this.setId(entity.getId());
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.phone = entity.getPhone();
            this.address = entity.getAddress();
            this.username = entity.getUsername();
        }
    }
}
