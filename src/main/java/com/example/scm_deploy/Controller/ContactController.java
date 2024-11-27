package com.example.scm_deploy.Controller;

import com.example.scm_deploy.entities.Contact;
import com.example.scm_deploy.entities.User;
import com.example.scm_deploy.forms.Contactform;
import com.example.scm_deploy.helpers.Helper;
import com.scm.helpers.Message;
import com.example.scm_deploy.helpers.MessageType;
import com.example.scm_deploy.services.ContactService;
import com.example.scm_deploy.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.apache.coyote.http11.Constants.a;

@Controller
  @RequestMapping("user/contacts")
public class ContactController {
    private Logger logger = LoggerFactory.getLogger(com.scm.Controller.UserController.class);
    //user dashboard page
    @Autowired
    private UserService userService;
    @Autowired
  private ContactService contactService;

    @RequestMapping(value = "/add")
    public String userContactView(Model model, Authentication authentication){
        String name = Helper.getEmailOfloggedinUser(authentication);
//        logger.info("User loggedin;{}",name);
        User user = userService.getUserByEmail(name);
        model.addAttribute("loggedinuser", user);
        Contactform contactform = new Contactform();
        contactform.setFavorite(true);
        model.addAttribute("contactform" ,contactform);
        return "user/add_contact";
    }
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute Contactform contactform,Model model, BindingResult result, Authentication authentication , HttpSession session){
        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }
        String name = Helper.getEmailOfloggedinUser(authentication);
        User user = userService.getUserByEmail(name);
        Contact contact = new Contact();
        contact.setName(contactform.getName());
        contact.setFavorite(contactform.isFavorite());
        contact.setEmail(contactform.getEmail());
        contact.setPhoneNumber(contactform.getPhoneNumber());
        contact.setAddress(contactform.getAddress());
        contact.setDescription(contactform.getDescription());
        contact.setUser(user);
        contact.setWebsiteLink(contactform.getWebsiteLink());
        contact.setLinkedInLink(contactform.getLinkedInLink());



        return "redirect:/user/contacts/add";
    }
}
