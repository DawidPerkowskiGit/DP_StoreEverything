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


/**
 * Controller for REST API
 */
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
     * Admin privileges functionality. Fetches all user's data.
     *
     * @return List of users data
     */
    @GetMapping("/adminapi/getAllUsers")
    public List<String> getAllUsers() {
        List allUsersStringList = new ArrayList();
        for (User u : userRepository.findAll()) {
            allUsersStringList.add(u.toString());

        }
        return allUsersStringList;
    }

    /**
     * Fetches single user's data
     *
     * @param UserId ID value of the user
     * @return User's data
     */
    @GetMapping("/adminapi/getUser/{id}")
    public ResponseEntity<String> getUserById(@PathVariable("id") long UserId) {
        User foundUser = userRepository.findByUserId(UserId);
        return new ResponseEntity<String>(foundUser.toString(), HttpStatus.OK);
    }


    /**
     * Fetches all Information from the database
     *
     * @return List of Information
     */
    @GetMapping("/adminapi/getAllInformations")
    public List<String> getAllInformations() {
        List allInformationsStringList = new ArrayList();
        for (Information info : informationRepository.findAll()) {
            allInformationsStringList.add(info.toString());

        }
        return allInformationsStringList;
    }

    /**
     * Fetches single information data
     *
     * @param InforamtionId ID value of an Information
     * @return Single information
     */
    @GetMapping("/adminapi/getInformation/{id}")
    public ResponseEntity<String> getinformationById(@PathVariable("id") long InforamtionId) {
        Information foundInformation = informationRepository.findByInformationId(InforamtionId);
        return new ResponseEntity<String>(foundInformation.toString(), HttpStatus.OK);
    }


    /**
     * Fetches all categories
     *
     * @return List of categories
     */
    @GetMapping("/adminapi/getAllCategories")
    public List<String> getAllCategories() {
        List allInformationscategoriesStringList = new ArrayList();
        for (Category category : categoryRepository.findAll()) {
            allInformationscategoriesStringList.add(category.toString());

        }
        return allInformationscategoriesStringList;
    }

    /**
     * Fetches single category
     *
     * @param CategoryId Category id value
     * @return Single category
     */
    @GetMapping("/adminapi/getCategory/{id}")
    public ResponseEntity<String> getCategoryById(@PathVariable("id") long CategoryId) {
        Category foundCategory = categoryRepository.getById(CategoryId);
        return new ResponseEntity<String>(foundCategory.toString(), HttpStatus.OK);
    }


    /**
     * Fetches information posted by a test user
     *
     * @return List of Information of a test user
     */
    @GetMapping("/api/getPosted")
    public List<String> getPostedInformations() {
        List allInformationsStringList = new ArrayList();
        allInformationsStringList.add("Informacje użytkownika testowego");
        for (Information info : informationRepository.returnAuthorsInformations(26L)) {
            allInformationsStringList.add(info.toString());

        }
        return allInformationsStringList;
    }

    /**
     * Fetches Information list received by a test user
     *
     * @return List of received information
     */

    @GetMapping("/api/getReceived")
    public List<String> getReceivedInformations() {
        List allInformationsStringList = new ArrayList();
        allInformationsStringList.add("Otrzymane informacje przez użytkownika testowego");
        List<ShareInformation> receivedInfo = shareInformationRepository.findInfosReceivedByUser(26L);
        for (ShareInformation info : receivedInfo) {
            allInformationsStringList.add(info.getInformation().toString());

        }
        return allInformationsStringList;
    }
}
