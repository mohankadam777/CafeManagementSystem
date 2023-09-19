package com.practise.cafe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practise.cafe.model.entity.Product;
import com.practise.cafe.model.dto.ProductDTO;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
  
	@Query("select new  com.example.rcbm.entity.ProductDTO(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
	List<ProductDTO> getAllProducts();
	
	@Modifying
	@Transactional
	@Query("update Product p set p.status=:status where id=:id")
	Integer updateStatus(@Param("status") Boolean status,@Param("id") Long id );
	
	@Query("select p from Product p where p.category.id=:id and p.status=true ")
	List<Product> getProductsByCategoryId(@Param("id")Long id);
	
	// @Query("select new  com.example.rcbm.entity.ProductDTO(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p where p.id=:id and p.status=true ")
	// ProductDTO getByProductId(Long id);
		@Query("select p from Product p where p.id=:id and p.status=true ")
	Product getByProductId(@Param("id") Long id);
}