package com.scm.Controller;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    //user dashboard page
    @Autowired
    private UserService userService;



    @RequestMapping(value = "/dashboard")
        public String userDashboard(Model model,Authentication authentication){
            String name = Helper.getEmailOfloggedinUser(authentication);
            logger.info("User loggedin;{}",name);
            User user = userService.getUserByEmail(name);
            model.addAttribute("loggedinuser", user);
            return "user/dashboard";
        }



    @RequestMapping(value = "/profile")
    public String userprofile(Model model,Authentication authentication){
        String name = Helper.getEmailOfloggedinUser(authentication);
        logger.info("User loggedin;{}",name);
       User user = userService.getUserByEmail(name);
        model.addAttribute("loggedinuser", user);
        return "user/profile";
    }
    @RequestMapping(value = "/home")
    public String userHome(Model model,Authentication authentication){
        String name = Helper.getEmailOfloggedinUser(authentication);
        logger.info("User loggedin;{}",name);
        User user = userService.getUserByEmail(name);
        model.addAttribute("loggedinuser", user);
        return "/home";
    }
    //user add contact page


    // user add view contact
    //user edit contact
    //user search page
}
