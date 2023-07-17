package com.dmrl.storeverything.controllers;

import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.information.InformationRepository;
import com.dmrl.storeverything.informationShare.ShareInformation;
import com.dmrl.storeverything.informationShare.ShareInformationRepository;
import com.dmrl.storeverything.user.User;
import com.dmrl.storeverything.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("")
public class ControllerRestApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ShareInformationRepository shareInformationRepository;

    /**
     * fetch all Users using REST API
     * GET
     * http://localhost:8080/api/getAllUsers
     */
    @GetMapping("/adminapi/getAllUsers")
    public List<String> getAllUsers() {
        List allUsersStringList = new ArrayList();
        for (User u: userRepository.findAll()
             ) {
            allUsersStringList.add(u.toString());

        }
        return allUsersStringList;
    }

    /**
     * fetch User by User.ID using REST API
     * GET
     * http://localhost:8080/api/getUser/1
     */
    @GetMapping("/adminapi/getUser/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") long UserId) {
        User foundUser = userRepository.findByUserId(UserId);
        return new ResponseEntity<String>(foundUser.toString(), HttpStatus.OK);
    }


    /**
     * fetch all Informations using REST API
     * GET
     * http://localhost:8080/api/getAllInformations
     */
    @GetMapping("/adminapi/getAllInformations")
    public List<String> getAllInformations() {
        List allInformationsStringList = new ArrayList();
        for (Information info: informationRepository.findAll()
        ) {
            allInformationsStringList.add(info.toString());

        }
        return allInformationsStringList;
    }

    /**
     * fetch Information by Information.ID using REST API
     * GET
     * http://localhost:8080/api/Information/1
     */
    @GetMapping("/adminapi/getInformation/{id}")
    public ResponseEntity<String> getinformationById(@PathVariable("id") long InforamtionId) {
        Information foundInformation = informationRepository.findByInformationId(InforamtionId);
        return new ResponseEntity<String>(foundInformation.toString(), HttpStatus.OK);
    }


    /**
     * fetch all Categories using REST API
     * GET
     * http://localhost:8080/api/getAllCategories
     */
    @GetMapping("/adminapi/getAllCategories")
    public List<String> getAllCategories() {
        List allInformationscategoriesStringList = new ArrayList();
        for (Category category: categoryRepository.findAll()
        ) {
            allInformationscategoriesStringList.add(category.toString());

        }
        return allInformationscategoriesStringList;
    }

    /**
     * fetch Category by Category.ID using REST API
     * GET
     * http://localhost:8080/api/Category/1
     */
    @GetMapping("/adminapi/getCategory/{id}")
    public ResponseEntity<String> getCategoryById(@PathVariable("id") long CategoryId) {
        Category foundCategory = categoryRepository.getById(CategoryId);
        return new ResponseEntity<String>(foundCategory.toString(), HttpStatus.OK);
    }



    /**
     * fetch all Informations using REST API
     * GET
     * http://localhost:8080/api/getAllInformations
     */
    @GetMapping("/api/getPosted")
    public List<String> getPostedInformations() {
        List allInformationsStringList = new ArrayList();
        allInformationsStringList.add("Informacje użytkownika testowego");
        for (Information info: informationRepository.returnAuthorsInformations(26L)
        ) {
            allInformationsStringList.add(info.toString());

        }
        return allInformationsStringList;
    }

    @GetMapping("/api/getReceived")
    public List<String> getReceivedInformations() {
        List allInformationsStringList = new ArrayList();
        allInformationsStringList.add("Otrzymane informacje przez użytkownika testowego");
        List<ShareInformation> receivedInfo = shareInformationRepository.findInfosReceivedByUser(26L);
        for (ShareInformation info: receivedInfo
        ) {
            allInformationsStringList.add(info.getInformation().toString());

        }
        return allInformationsStringList;
    }
}
