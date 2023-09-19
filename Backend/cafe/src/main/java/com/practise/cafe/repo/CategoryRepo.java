package com.practise.cafe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practise.cafe.model.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long>{

    @Query("Select c from Category c where c.id in (select p.category.id from Product p where p.status=true)")
	List<Category> getAllCategory();
}