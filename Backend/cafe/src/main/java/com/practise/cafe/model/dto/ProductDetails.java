package com.practise.cafe.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetails implements Serializable{

    private Long id;
    private String name;
    private String category;
    private Float price;
    private String quantity;
    private Float total;    
}