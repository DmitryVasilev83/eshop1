package ru.gb.eshop.service;

import org.springframework.stereotype.Service;
import ru.gb.eshop.dao.BucketRepository;
import ru.gb.eshop.dao.ProductRepository;
import ru.gb.eshop.domain.Bucket;
import ru.gb.eshop.domain.Product;
import ru.gb.eshop.domain.User;
import ru.gb.eshop.dto.BucketDetailDto;
import ru.gb.eshop.dto.BucketDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
//    private final OrderService orderService;

    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService
//            , OrderService orderService
    ) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
//        this.orderService = orderService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                // getone вытаскивает ссылку на обьект , findById вытаскивает сам обьект
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDto getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null){
            return new BucketDto();
        }

        BucketDto bucketDto = new BucketDto();
        Map<Long, BucketDetailDto> mapByProductId = new HashMap<>();

        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDto detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDto(product));
            }
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }

        bucketDto.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();

        return bucketDto;
    }

//    @Transactional
//    public void commitBucketToOrder(String username){
//        User user = userService.findByName(username);
//        if(user == null){
//            throw new RuntimeException("User is not found");
//        }
//        Bucket bucket = user.getBucket();
//        if(bucket == null || bucket.getProducts().isEmpty()){
//            return;
//        }
//
//        Order order = new Order();
//        order.setStatus(OrderStatus.NEW);
//        order.setUser(user);
//
//        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
//                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
//
//        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
//                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
//                .collect(Collectors.toList());
//
//        BigDecimal total = new BigDecimal(orderDetails.stream()
//                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
//                .mapToDouble(BigDecimal::doubleValue).sum());
//
//        order.setDetails(orderDetails);
//        order.setSum(total);
//        order.setAddress("none");
//
//        orderService.saveOrder(order);
//        bucket.getProducts().clear();
//        bucketRepository.save(bucket);
//
//    }

//    public static void main(String[] args) {
//
//        List<Product> productList = new ArrayList<>();
//        Product milk = Product.builder().title("Milk").price(10.0).build();
//        productList.add(milk);
//        productList.add(milk);
//        Product chocolate = Product.builder().title("Chocolate").price(111.0).build();
//        productList.add(chocolate);
//
//        Map<Product, Long> productWithAmount = productList.stream()
//                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
//
//        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
//                .map(pair -> new OrderDetails(null, pair.getKey(), pair.getValue()))
//                .collect(Collectors.toList());
//
//        BigDecimal sum = new BigDecimal(orderDetails.stream()
//                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
//                .mapToDouble(BigDecimal::doubleValue).sum());
//
//        System.out.println(sum);
//
//    }

}
