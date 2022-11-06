package ru.gb.eshop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.eshop.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
