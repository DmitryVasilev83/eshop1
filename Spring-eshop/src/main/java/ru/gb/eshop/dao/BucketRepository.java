package ru.gb.eshop.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.eshop.domain.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
