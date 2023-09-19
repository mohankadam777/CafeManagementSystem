package com.practise.cafe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.practise.cafe.model.entity.Bill;
import com.practise.cafe.repo.BillRepo;
import com.practise.cafe.security.JwtFilter;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class BillService {


    @Autowired
    private BillRepo billRepo;

    @Autowired
    private JwtFilter jwtFilter;
    

    public ResponseEntity<String> generateReport(Bill bill){
        System.out.println("in bill service"+bill);
        try{
            bill.setCreatedBy(jwtFilter.getCurrentUser());
                billRepo.save(bill);
                return new ResponseEntity<String>("Bill saved successfully",HttpStatus.CREATED);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("Something went wrong", HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<List<Bill>> getBills(){
        try {
             List<Bill>billList;
            if(jwtFilter.isAdmin()){
                billList= billRepo.getAllBill();
            }else{
                String username=jwtFilter.getCurrentUser();
                billList= billRepo.getBillByUsername(username);
            }
                return new ResponseEntity<List<Bill>>(billList, HttpStatus.OK);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new ResponseEntity<List<Bill>>(new ArrayList(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<String> deleteBill(Long id){
        try {
            Optional<Bill> bill = billRepo.findById(id);
            if(bill!=null){
                billRepo.deleteById(id);
                return new ResponseEntity<String>("Bill deleted Successfully", HttpStatus.OK);
            }
            return new ResponseEntity<String>("Invalid data", HttpStatus.BAD_REQUEST);
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return new ResponseEntity<String>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    // private Boolean validateMap(Map<String, Object> requestMap){
    //     return requestMap.containsKey("name")&&requestMap.containsKey("contactNumber")&&
    //     requestMap.containsKey("email")&&requestMap.containsKey("paymentMethod")&&
    //     requestMap.containsKey("total")&&requestMap.containsKey("productDetails");

    // }


    
}