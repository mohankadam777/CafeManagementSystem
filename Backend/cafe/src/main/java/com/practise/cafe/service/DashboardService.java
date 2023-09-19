package com.practise.cafe.service;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practise.cafe.repo.BillRepo;
import com.practise.cafe.repo.CategoryRepo;
import com.practise.cafe.repo.ProductRepo;

@Service
public class DashboardService {


    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private BillRepo billRepo;

    public ResponseEntity<Map<String,Object>> getDashboardDetails(){
        Map<String, Object> map= new HashMap<>();
        map.put("category", categoryRepo.count());
        map.put("product", productRepo.count());
        map.put("bill", billRepo.count());
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
    
}