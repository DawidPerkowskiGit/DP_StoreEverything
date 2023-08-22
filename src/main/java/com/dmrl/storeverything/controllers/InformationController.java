package com.dmrl.storeverything.controllers;

import com.dmrl.storeverything.DataPickerStringConverter;
import com.dmrl.storeverything.category.Category;
import com.dmrl.storeverything.category.CategoryRepository;
import com.dmrl.storeverything.information.Information;
import com.dmrl.storeverything.information.InformationRepository;
import com.dmrl.storeverything.informationShare.ShareInformation;
import com.dmrl.storeverything.informationShare.ShareInformationRepository;
import com.dmrl.storeverything.user.IAuthenticationFacade;
import com.dmrl.storeverything.user.User;
import com.dmrl.storeverything.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Controller for handling all the Information entity requests
 */

@Controller
public class InformationController {

    private int informationsCount = 100;

    @Autowired
    private UserRepository repo;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ShareInformationRepository shareInformationRepository;


    @PersistenceContext
    private EntityManager em;


    /**
     * Return currently logged in user data
     * @return Currently logged-in user login
     */
    public String currentUserNameSimple() {
        Authentication authentication = authenticationFacade.getAuthentication();
        return authentication.getName();
    }

    /**
     * Create a view with list of Information
     * @param model Information model
     * @return View displaying list of all Information
     */

    @GetMapping("/informations/list")
    public String listInformations(Model model) {

        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        return "informations/list";
    }

    /**
     * Accessing this endpoint provides view where user can add new information
     * @param model Information model
     * @return Send POST request at /informations/add endpoint to save new user into the database
     */

    @GetMapping("/informations/add")
    public String showAddInformationForm(Model model) {

        if (informationRepository.findAll().size() > informationsCount) {
            return "error/max_infos";
        }

        String query = ("SELECT c, COUNT(i.category.id) as ccount FROM Category c LEFT OUTER JOIN Information i on i.category = c GROUP BY c ORDER BY ccount DESC");
        List<Object[]> result = em.createQuery(query).getResultList();
        List<Category> listCategories = new ArrayList<>();

        Boolean categoryObj = true;

        for (int i = 0; i < result.size() ; i++) {
            for (Object singleObj: result.get(i)
            ) {
                if (categoryObj) {
                    listCategories.add((Category) singleObj);
                }
                categoryObj = !categoryObj;
            }
        }


        model.addAttribute("listCategories", listCategories);

        Information information = new Information();
        model.addAttribute("information", information);

        String stringDate = "";
        DataPickerStringConverter dataPickerStringConverter = new DataPickerStringConverter(stringDate);
        model.addAttribute("tempStringClass", dataPickerStringConverter);


        return "informations/add";
    }

    /**
     * Save information to the database
     * @param dataPickerStringConverter convert date to String format
     * @param information Information entity object
     * @return Information added successfully view.
     * @throws ParseException Date to String parsing error
     */

    @PostMapping("/informations/add")
    public String processAddInformation(DataPickerStringConverter dataPickerStringConverter,
                                        Information information) throws ParseException {

        if (informationRepository.findAll().size() > informationsCount) {
            return "error/max_infos";
        }

        String userName = currentUserNameSimple();
        User currUser = repo.findByLogin(userName);
        information.setUser(currUser);

        information.setRememberDate(information.formatStringToDate(dataPickerStringConverter.getTempString()));

        informationRepository.save(information);

        return "informations/add_success";
    }

    /**
     * Create view with Informations added by currently logged-in user
     */

    @GetMapping("/informations/my_list")
    public String listMyInformations(Model model) {


        verifyIfUserInfoIsInSession();

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);

        User currUser = (User) session.getAttribute("currUser");
        List<Information> listInformations = informationRepository.returnAuthorsInformations(currUser.getId());
        model.addAttribute("listInformations", listInformations);

        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        return "informations/my_list";
    }

    /**
     * Delete an Information
     */

    @GetMapping("/informations/delete")
    public String informationDelete(@RequestParam Long deleteId) {
        informationRepository.deleteById(deleteId);
        return "redirect:/informations/my_list";
    }

    /**
     * Create a view with details of an Information
     */

    @GetMapping("/limited/details")
    public String informationDetails(@RequestParam Long detailsId,
                                     Model model) {

        Information information = informationRepository.getById(detailsId);
        model.addAttribute("information", information);

        return "limited/details";
    }

    /**
     * GET - Edit an Information
     */

    @GetMapping("/informations/edit")
    public String informationEdit(@RequestParam Long editId,
                                  Model model) {

        Information information = informationRepository.findByInformationId(editId);
        model.addAttribute("information", information);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);

        session.setAttribute("editId", editId);

        String stringDate = "";
        DataPickerStringConverter dataPickerStringConverter = new DataPickerStringConverter(stringDate);
        model.addAttribute("tempStringClass", dataPickerStringConverter);

        List<Category> listCategories = categoryRepository.findAll();
        model.addAttribute("listCategories", listCategories);

        return "informations/edit";
    }

    /**
     * POST - Edit an Information
     */
    @PostMapping("/informations/edit")
    public String processEditInformation(DataPickerStringConverter dataPickerStringConverter,
                                         Information information) throws ParseException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);

        Long editId = (Long) session.getAttribute("editId");

        Information oldInformation = informationRepository.findByInformationId(editId);

        information.setRememberDate(information.formatStringToDate(dataPickerStringConverter.getTempString()));

        information.setInformationId(oldInformation.getInformationId());
        information.setUser(oldInformation.getUser());


        informationRepository.save(information);

        return "informations/edit_success";
    }


    /**
     * Creates a view where user can share Information with another user
     */

    @GetMapping("/informations/share")
    public String informationShare(@RequestParam Long sharedInfoId,
                                   Model model) {

        ShareInformation shareInformation = new ShareInformation();
        shareInformation.setInformation(informationRepository.findByInformationId(sharedInfoId));
        model.addAttribute("shareInformation", shareInformation);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        session.setAttribute("sharedInfoId", sharedInfoId);



        String userName = currentUserNameSimple();
        User currUser = repo.findByLogin(userName);

        List<User> userList = repo.findAllUserOtherThanThis(currUser.getId());
        model.addAttribute("usersList", userList);

        return "informations/share";
    }

    @PostMapping("/informations/share")
    public String processInformationShare(ShareInformation shareInformation) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        Long sharedInfoId = (Long) session.getAttribute("sharedInfoId");

        shareInformation.setInformation(informationRepository.findByInformationId(sharedInfoId));

        shareInformationRepository.save(shareInformation);

        return "redirect:my_list";
    }

    @GetMapping("/limited/received")
    public String viewReceivedInformations(Model model) {

        //verifyIfUserInfoIsInSession();

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);

        User currUser = (User) session.getAttribute("currUser");

        List<ShareInformation> receivedInfo = shareInformationRepository.findInfosReceivedByUser(currUser.getId());

        model.addAttribute("receivedInfo", receivedInfo);

        return "limited/received";

    }

    /**
     * Methods checks if currently logged-in user information is stored in application session.
     * If user is not present, adds its data to session and returns true/false depending on if
     * data was present before invoking method.
     */

    public Boolean verifyIfUserInfoIsInSession() {
        if (!authenticationFacade.getAuthentication().isAuthenticated()) {
            return false;
        }

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);

        Enumeration<String> sessionAttributes = session.getAttributeNames();

        Boolean userInfoFound = false;

        for (; sessionAttributes.hasMoreElements();) {
            if (sessionAttributes.nextElement().equals("currUser")) {
                userInfoFound = true;
                break;
            }
        }

        User currUser;

        if (!userInfoFound) {
            currUser = repo.findByLogin(authenticationFacade.getAuthentication().getName());
            session.setAttribute("currUser", currUser);
        }

        return userInfoFound;

    }

}
