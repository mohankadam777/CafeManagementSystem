package com.practise.cafe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practise.cafe.model.entity.Bill;


@Repository
public  interface BillRepo extends JpaRepository<Bill,Long>{

    @Query("select b from Bill b order by b.id")
    public List<Bill> getAllBill();


    @Query("select b from Bill b where b.createdBy = :username order by b.id")
    public List<Bill> getBillByUsername(@Param("username") String username);
    
}
