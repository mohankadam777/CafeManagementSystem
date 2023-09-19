package com.practise.cafe.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practise.cafe.model.entity.Product;
import com.practise.cafe.repo.ProductRepo;


@Service
public class ProductService {

@Autowired
private ProductRepo productRepo;

public ResponseEntity<List<Product>> getProductByCategory(Long categoryId){
    try{
        List<Product> resultList = productRepo.getProductsByCategoryId(categoryId);
        return new ResponseEntity<List<Product>>(resultList,HttpStatus.OK);
    }catch(Exception e){
       e.printStackTrace();
    }
    return new ResponseEntity<List<Product>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
}



    
}