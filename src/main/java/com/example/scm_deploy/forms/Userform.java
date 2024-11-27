package com.example.scm_deploy.forms;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Userform {

    public @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Characters is required") String getName() {
        return name;
    }


    public @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public @NotBlank(message = "Password is required") @Size(min = 6, message = "Min 6 Characters is required") String getPassword() {
        return password;
    }

    public @NotBlank(message = "About is required") String getAbout() {
        return about;
    }

    public @Size(min = 8, max = 12, message = "Invalid Phone Number")
    @Pattern(regexp = "^[0-9]{10}$" , message = "Invalid Phone number")
    String getPhoneNumber() {
        return phoneNumber;
    }

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String name;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 Characters is required")
    private String password;

    @NotBlank(message = "About is required")
    private String about;
    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phoneNumber;

}