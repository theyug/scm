package com.example.scm_deploy.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contactform  {

     @NotBlank(message = "Username is required") @Size(min = 3, message = "Min 3 Characters is required")
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    @Pattern(regexp = "^[0-9]{10}$" , message = "Invalid Phone number")
    private String phoneNumber;
    @NotBlank(message = "address is required")
    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile picture;
}
