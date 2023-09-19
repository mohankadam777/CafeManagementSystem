package com.practise.cafe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PutExchange;

import com.practise.cafe.model.dto.ProductDTO;
import com.practise.cafe.model.entity.Category;
import com.practise.cafe.model.entity.Product;
import com.practise.cafe.repo.ProductRepo;
import com.practise.cafe.security.JwtFilter;
import com.practise.cafe.service.CategoryService;



@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
	private ProductRepo productRepo;
	
	@Autowired 
	private CategoryService categoryService;
	
	@Autowired
	private JwtFilter jwtFilter;
	

	@GetMapping()
	public ResponseEntity<List<Product>> getProducts() {
		try {
			List<Product> productList= productRepo.findAll();
				return new ResponseEntity<List<Product>> (productList,HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<List<Product>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("")
	public ResponseEntity<String> addProducts(@RequestBody Map<String, String>reqMap) {
	try {
		if(jwtFilter.isAdmin()) {

			if(validateMap(reqMap,false)) {
				productRepo.save(mapProduct(reqMap, false));
				return new ResponseEntity<String>("Products Added successfully",HttpStatus.CREATED);
			}
		return new ResponseEntity<String>("Invalid Data",HttpStatus.BAD_REQUEST);
		}return new ResponseEntity<String> ("Unauthorized Access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping
	public ResponseEntity<String> updateProducts(@RequestBody Map<String, String>reqMap) {
	try {
		if(jwtFilter.isAdmin()) {
			if(validateMap(reqMap, true)) {
				Optional<Product> optional= productRepo.findById(Long.parseLong(reqMap.get("id")));
				if(!optional.isEmpty()) {
					Product product = mapProduct(reqMap, true);
					product.setStatus(optional.get().getStatus());
					productRepo.save(product);
					return new ResponseEntity<String>("Product updated successfully",HttpStatus.OK);
				}return new ResponseEntity<String>("Product id not found",HttpStatus.BAD_REQUEST);
			}return new ResponseEntity<String>("Invalid Data",HttpStatus.BAD_REQUEST);
		}return new ResponseEntity<String>("Unauthorized access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@DeleteMapping
	public  ResponseEntity<String> deleteById(@RequestParam Long id) {
		try {
			if (jwtFilter.isAdmin()) {
				Optional<Product> product= productRepo.findById(id);
				if(product.isPresent()) {
					productRepo.deleteById(id);
					return new ResponseEntity<String>("Product deleted successfully",HttpStatus.OK);
				}return new ResponseEntity<String>("Product not found",HttpStatus.BAD_REQUEST);
			}return new ResponseEntity<String>("Uauthorized Access",HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	@PostMapping("update-status")
	public ResponseEntity<String> updateStatus(@RequestBody Map<String, String>reqMap) {
		try {
			if(jwtFilter.isAdmin()) {
					Optional<Product> optional= productRepo.findById(Long.parseLong(reqMap.get("id")));
					if(!optional.isEmpty()) {
						System.err.println(reqMap.get("status"));
						productRepo.updateStatus(Boolean.valueOf(reqMap.get("status")), Long.parseLong(reqMap.get("id")));
						return new ResponseEntity<String>("Product Status updated successfully",HttpStatus.OK);
					}return new ResponseEntity<String>("Product id not found",HttpStatus.BAD_REQUEST);		
			}return new ResponseEntity<String>("Unauthorized access",HttpStatus.UNAUTHORIZED);
			} catch (Exception e) {
//				System.err.println(e);x
				e.printStackTrace();
			}
			return new ResponseEntity<String>("Something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
		}


		@GetMapping("/by-category")
		public ResponseEntity<List<ProductDTO>> getProductsByCategory(@RequestParam Long id) {
		try {
			List<ProductDTO> resultList=new ArrayList<>();
			List<Product>productList= productRepo.getProductsByCategoryId(id);
			for(Product p :productList){
				resultList.add(ProductDTO.builder().id(p.getId()).name(p.getName()).description(p.getDescription()).price(p.getPrice()).status(p.getStatus()).categoryId(p.getCategory().getId().longValue()).categoryName(p.getCategory().getName()).build());
			}
				return new ResponseEntity<>(resultList,HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<List<ProductDTO>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping("/id")
	public ResponseEntity<Product> getProductsByProduct(@RequestParam Long id) {
		try {
			Product pW = productRepo.getByProductId(id);;
			if(pW!=null){
				
				return new ResponseEntity<Product> (pW,HttpStatus.OK);
			}
			return new ResponseEntity<Product> (new Product(),HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return new ResponseEntity<Product>(new Product(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	/////////////////////////////////////////////////////////////////////////////
	public Boolean  validateMap(Map<String, String> reqMap,boolean isAdd) {
		if(reqMap.containsKey("name")&&reqMap.containsKey("categoryId")&&reqMap.containsKey("price")) {
			if(reqMap.containsKey("id")&&isAdd) {
				return true;
			}else if(!isAdd) {
				return true;
			}
		}	
	return false;
	} 
	public Product mapProduct(Map<String, String>reqMap,boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(reqMap.get("categoryId")));
		Product product = new Product();
		product.setCategory(category);
		if(isAdd) {
			product.setId(Long.parseLong(reqMap.get("id")));
		}else {
			product.setStatus(true);
		}
		product.setName(reqMap.get("name"));
		product.setDescription(reqMap.get("description"));
		product.setPrice(Float.parseFloat(reqMap.get("price")));
		return product;
	}

    
}
