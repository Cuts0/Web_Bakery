package com.example.tttn.dto;

import com.example.tttn.entity.Role;
import com.example.tttn.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseObjectDto<UserDto>{
    @Size(min = 2, max = 15, message = "First name should have 3-15 characters")
    private String firstName;
    @Size(min = 2, max = 15, message = "First name should have 3-15 characters")
    private String lastName;
    private String username;
    @Size(min = 5, max = 20, message = "Password should have 5-20 characters")
    private String password;
    private String phone;
    private String address;
    private List<Long> roleIds;
    private List<String> roles = new ArrayList<>();
    public UserDto(Users entity){
        if (entity != null){
            this.setId(entity.getId());
            this.firstName = entity.getFirstName();
            this.lastName = entity.getLastName();
            this.username = entity.getUsername();
            this.password = entity.getPassword();
            this.phone = entity.getPhone();
            this.address = entity.getAddress();
            if (entity.getRoles() != null){
                this.roleIds = new ArrayList<>();
                for (Role role : entity.getRoles()){
                    roleIds.add(role.getId());
                    roles.add(role.getName());
                }
            }
        }
    }
}
