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
public class ProductDTO implements Serializable{
    private Long id;
	private String name;
	private String description;
	private Float price;
	private Boolean status;
	private Long categoryId;
	private String categoryName;
}