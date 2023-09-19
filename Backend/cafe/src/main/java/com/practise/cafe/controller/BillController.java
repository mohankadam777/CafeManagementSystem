package com.practise.cafe.controller;
import java.util.Map;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practise.cafe.model.entity.Bill;
import com.practise.cafe.service.BillService;


@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<String> generateReport(@RequestBody Bill bill){
        return billService.generateReport(bill);
    }

    @GetMapping
    public ResponseEntity<List<Bill>> getBills(){
        return billService.getBills();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBill(@RequestParam Long id){
        return billService.deleteBill(id);
    }
    
}
