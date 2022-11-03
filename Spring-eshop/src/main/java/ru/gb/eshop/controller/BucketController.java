package ru.gb.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.eshop.dto.BucketDto;
import ru.gb.eshop.service.BucketService;

import java.security.Principal;

@Controller
public class BucketController {

    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("bucket", new BucketDto());
        }
        else {
            BucketDto bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }

        return "bucket";
    }

//    @PostMapping("/bucket")
//    public String commitBucket(Model model, Principal principal){
//        if(principal != null){
//            bucketService.commitBucketToOrder(principal.getName());
//        }
//        return "redirect:/bucket";
//    }
}
