package com.dmrl.storeverything.controllers;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.user.Role;
import com.dmrl.storeverything.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Roles controller
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

@Controller
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;


    /**
     * GET - add new role
     */
    @GetMapping("/admin/roles/add_role")
    public String showAddRoleForm(Model model) {
        model.addAttribute("role", new Role());

        return "admin/roles/add_role";
    }


    /**
     * POST - add new role
     */
    @PostMapping("/admin/roles/add_role")
    public String processAddRole(Role role) {

        roleRepository.save(role);

        return "admin/roles/add_role_success";
    }
}
