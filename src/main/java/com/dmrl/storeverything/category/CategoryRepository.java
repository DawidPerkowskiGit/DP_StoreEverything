package com.dmrl.storeverything.category;

import com.dmrl.storeverything.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.categoryName = ?1")
    Category findByCategoryName(String name);
}
