package ru.gb.eshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.eshop.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
