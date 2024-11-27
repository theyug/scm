package com.example.scm_deploy.Controller;

import com.example.scm_deploy.entities.User;
import com.example.scm_deploy.forms.Userform;
import com.example.scm_deploy.services.UserService;
import com.example.scm_deploy.helpers.Message;
import com.example.scm_deploy.helpers.MessageType;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }
    @RequestMapping("/home")
    public String home(Model model) {
        return "home"; // This should map to home.html in templates
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/services")
    public String services(){
        return "services";
    }
//contact
@GetMapping("/contact")
public String contact(){
    return "contact";
}
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        Userform userForm = new Userform();  // Create a new Userform instance
        model.addAttribute("userForm", userForm);  // Add the object to the model
        return "register";  // Render the registration page
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processregister(@ModelAttribute Userform userForm ,HttpSession session){
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        User savedUser = userService.saveUser(user);
    System.out.println(userForm);
        // Assuming MessageType.green is a valid enum value
        Message message = Message.builder()
                .content("Registration successful")
                .type(MessageType.green) // or any appropriate type
                .build();

        session.setAttribute("message", message);

        return "redirect:/register";
    }

}
