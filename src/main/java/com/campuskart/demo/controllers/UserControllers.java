package com.campuskart.demo.controllers;

import com.campuskart.demo.dto.DisplayDto;
import com.campuskart.demo.dto.LoginDto;
import com.campuskart.demo.dto.ProfileDto;
import com.campuskart.demo.dto.UpdateDto;
import com.campuskart.demo.models.History;
import com.campuskart.demo.models.User;
import com.campuskart.demo.repositories.HistoryRepo;
import com.campuskart.demo.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class UserControllers {
    @Autowired
    UserRepo userRepo;
    @Autowired
    HistoryRepo historyRepo;

    @PostMapping("/register")
    public String register(@RequestBody User user)
    {
        if (userRepo.findByUsername(user.getUsername()) !=null) {
            return "ERROR: Username already exists";
        }
        if (userRepo.findByCollegeId(user.getCollegeId()) !=null) {
            return "ERROR: CollegeId already exists";
        }
        userRepo.save(user);
        return "Signup Successful";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto u)
    {
        User user=userRepo.findByUsername(u.getUsername());
        if(user==null)
        {
            return "user not found";
        }
        if(!u.getPassword().equals(user.getPassword()))
        {
            return "Password Incorrect";
        }
        return "Login success";
    }
    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return userRepo.findByUsername(username);
    }

    @GetMapping("/get details/{id}")
    public DisplayDto display(@PathVariable int id)
    {
        User user=userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        DisplayDto displayDto=new DisplayDto();
        displayDto.setUsername(user.getUsername());
        return displayDto;
    }
    @GetMapping("/profile/by-id/{id}")
    public ProfileDto profileByUsername(@PathVariable int id) {
        User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        if (user == null) throw new RuntimeException("User not found");

        ProfileDto dto = new ProfileDto();
        dto.setName(user.getName());
        dto.setMobile(user.getMobile());
        return dto;
    }

    @PostMapping("/profile/update")
    public String update(@RequestBody UpdateDto u)
    {
        User user=userRepo.findByUsername(u.getUsername());
        if(u.getKey().equalsIgnoreCase("name"))
        {
            if(u.getValue().equals("user.getName()"))
            {
                return "ERROR: Name already exists";
            }
            History h=new History();
            h.setDescription("User "+user.getUsername()+" Changes his name to: "+u.getValue());
            historyRepo.save(h);
            user.setName(u.getValue());
        }
        if(u.getKey().equalsIgnoreCase("password"))
        {
            if(u.getValue().equals("user.getpassword()"))
            {
                return "ERROR: Password already exists";
            }
            user.setPassword(u.getValue());
            History h=new History();
            h.setDescription("User "+user.getUsername()+" Changes his password");
            historyRepo.save(h);
        }
        if(u.getKey().equalsIgnoreCase("mobile"))
        {
            if(u.getValue().equals("user.getmobile()"))
            {
                return "ERROR: Mobile NO. already exists";
            }
            user.setMobile(u.getValue());
            History h=new History();
            h.setDescription("User "+user.getUsername()+"  Changes his MobileNo. to: "+u.getValue());
            historyRepo.save(h);
        }
        userRepo.save(user);
        return "Update successfully";
    }

    @GetMapping("/admin/users")
    public List<User> getUsers()
    {
        return userRepo.findAll();
    }

    @GetMapping("/admin/users/search")
    public List<User> searchUsers(@RequestParam String keyword) {

        // check if keyword contains only digits
        boolean isMobileSearch = keyword.matches("\\d+");

        if (isMobileSearch) {
            return userRepo.findByMobileContaining(keyword);
        } else {
            return userRepo.findByUsernameContainingIgnoreCase(keyword);
        }
    }

}
