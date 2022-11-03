package ru.gb.eshop.service;

import ru.gb.eshop.domain.Bucket;
import ru.gb.eshop.domain.User;
import ru.gb.eshop.dto.BucketDto;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDto getBucketByUser(String name);
//    void commitBucketToOrder(String username);
}
