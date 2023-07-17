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
     * List all categories
     */
    /*@GetMapping("/admin/dashboard")
    public String listCategories(Model model) {

        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        return "admin/dashboard";
    }*/

    /**
     * GET - add new category (Admin)
     */
    @GetMapping("/admin/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());

        return "admin/categories/add";
    }


    /**
     * POST - add new category (Admin)
     */
    @PostMapping("/admin/categories/add")
    public String processAddCategory(Category category) {

        categoryRepository.save(category);

        return "admin/categories/add_success";
    }

    /**
     * GET - add new category (Full-User)
     */
    @GetMapping("/informations/category_add")
    public String showAddCategoryFormUser(Model model) {
        model.addAttribute("category", new Category());

        return "informations/category_add";
    }


    /**
     * POST - add new category (Full-User)
     */
    @PostMapping("/informations/category_add")
    public String processAddCategoryUser(Category category) {

        categoryRepository.save(category);

        return "informations/category_add_success";
    }
}
