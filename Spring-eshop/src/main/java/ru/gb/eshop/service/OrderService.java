package ru.gb.eshop.service;

import ru.gb.eshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
    Order getOrder(Long id);
}
