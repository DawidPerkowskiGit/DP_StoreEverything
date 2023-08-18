package com.dmrl.storeverything.controllers;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.information.InformationRepository;
import com.dmrl.storeverything.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class AppController {

    private int usersCount = 20;

    @Autowired
    private UserRepository repo;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("")
    public String viewIndexPage() {
        return "index";
    }

    /**
     * User login endpoint
     *
     * @return Login view
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * User registration endpoint
     *
     * @param model Registration data model
     * @return Register from view
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        if (repo.findAll().size() > usersCount) {
            return "error/max_users";
        }

        model.addAttribute("user", new User());

        return "register_form";
    }

    /**
     * Processes registration data, if data is valid adds new user to the database
     *
     * @param user User entity data
     * @return Registration successful view
     */
    @PostMapping("/process_register")
    public String processRegistration(User user) {

        if (repo.findAll().size() > usersCount) {
            return "error/max_users";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Role roleUser = roleRepository.findByName("L_USER");
        user.addRole(roleUser);

        repo.save(user);

        return "register_success";
    }

    /**
     * Admin dashboard view
     *
     * @param model Users and their roles, Information and categoties
     * @return admin dashboard view
     */
    @GetMapping("/admin/dashboard")
    public String listCategories(Model model) {

        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        List<User> listUsers = repo.findAll();
        model.addAttribute("listUsers", listUsers);

        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);

        List<Information> listInformations = informationRepository.findAll();
        model.addAttribute("listInformations", listInformations);

        return "admin/dashboard";
    }

    /**
     * Homepage view endpoint
     *
     * @param model User information, recently posted and shared information
     * @return Homepage view
     */
    @GetMapping("/homepage")
    public String showHomePage(Model model) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        User currUser = (User) session.getAttribute("currUser");
        if (currUser == null) {
            return "index";
        }

        Long userId = currUser.getId();

        List<Information> recentlyAddedInfos = informationRepository.findFewRecentlyAddedInfos(userId);

        if (recentlyAddedInfos.size() > 0) {
            model.addAttribute("recentlyAddedInfos", recentlyAddedInfos);
        }


        List<Information> recentlyReceivedInfos = informationRepository.findFewRecentlyReceivedInfos(userId);

        if (recentlyReceivedInfos.size() > 0) {
            model.addAttribute("recentlyReceivedInfos", recentlyReceivedInfos);
        }
        return "home";
    }

    /**
     * User profile view endpoint
     *
     * @return User profile view
     */
    @RequestMapping("/user/profile")
    public String showUserProfile() {
        return "user/profile.html";
    }

    /**
     * Admin dashboard view endpoint
     *
     * @return Admin dashboard view
     */
    @RequestMapping("/admin/dashboard")
    public String showAdminDashboard() {
        return "admin/dashboard.html";
    }

    /**
     * After logging in view endpoint
     *
     * @param model Recently posted/received information
     * @return After logging in view
     */
    @GetMapping("/afterLoggingIn")
    public String showCurrentDayInfo(Model model) {


        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        User currUser = repo.findByLogin(authenticationFacade.getAuthentication().getName());

        if (currUser == null) {
            return "index";
        }
        session.setAttribute("currUser", currUser);

        Date today = new Date();

        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        List<Information> rememberTodayInfo = informationRepository.returnCurrentDayInformations(today, currUser.getId());
        if (rememberTodayInfo.size() == 0) {
            return "redirect:/homepage";
        }

        model.addAttribute("rememberTodayInfo", rememberTodayInfo);

        return "informations/todays_info";
    }
}
