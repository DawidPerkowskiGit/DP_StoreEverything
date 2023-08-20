package com.dmrl.storeverything.controllers;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Categories controller
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Admin panel functionality which enables adding new category
     *
     * @param model Categories model
     * @return Category add view
     */
    @GetMapping("/admin/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());

        return "admin/categories/add";
    }


    /**
     * Saves category to the database
     *
     * @param category Category to add
     * @return Added category view
     */
    @PostMapping("/admin/categories/add")
    public String processAddCategory(Category category) {

        categoryRepository.save(category);

        return "admin/categories/add_success";
    }


    /**
     * Category add endpoint available to user
     *
     * @param model Category model
     * @return Category add view
     */
    @GetMapping("/informations/category_add")
    public String showAddCategoryFormUser(Model model) {
        model.addAttribute("category", new Category());

        return "informations/category_add";
    }

    /**
     * Saves category to the database
     *
     * @param category Category to be added
     * @return Category added view
     */
    @PostMapping("/informations/category_add")
    public String processAddCategoryUser(Category category) {

        categoryRepository.save(category);

        return "informations/category_add_success";
    }
}
