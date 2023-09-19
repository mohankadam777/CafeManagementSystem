package com.practise.cafe.model.entity;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedBy;

import com.practise.cafe.model.dto.ProductDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity 
@Builder
@Table(name="bill")
@AllArgsConstructor
@NoArgsConstructor
public class Bill implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="contact_number")
    private String contactNumber;
    
    @Column(name="email")
    private String email;
    
    @Column(name="payment_method")
    private String paymentMethod;
    
    @Column(name="total")
    private Float total;
    
    @Column(name="product_details",columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<ProductDetails>  productDetails;

    @CreatedBy
    @Column(name="created_by")
    private String createdBy;



    
}