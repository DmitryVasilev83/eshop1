package ru.gb.eshop.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.eshop.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
//    User findFirstByActivateCode(String activateCode);
}
