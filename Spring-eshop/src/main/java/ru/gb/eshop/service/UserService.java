package ru.gb.eshop.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.gb.eshop.domain.User;
import ru.gb.eshop.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService { // для security
    boolean save(UserDto userDto);

    List<UserDto> getAll();

    User findByName(String name);

    void updateProfile(UserDto dto);

    void save(User user);
//
//    boolean activateUser(String activateCode);
}
