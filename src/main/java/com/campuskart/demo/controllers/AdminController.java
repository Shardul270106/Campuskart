package com.campuskart.demo.controllers;

import com.campuskart.demo.dto.AdminloginDto;
import com.campuskart.demo.models.Admin;
import com.campuskart.demo.models.History;
import com.campuskart.demo.models.SellItem;
import com.campuskart.demo.models.User;
import com.campuskart.demo.repositories.AdminRepo;
import com.campuskart.demo.repositories.HistoryRepo;
import com.campuskart.demo.repositories.SellRepo;
import com.campuskart.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    HistoryRepo historyRepo;
    @Autowired
    SellRepo sellRepo;
    @Autowired
    UserRepo userRepo;

    @PostMapping("/admin/register")
    public String registerAdmin(@RequestBody Admin admin)
    {
        if(adminRepo.findByUsername(admin.getUsername()) !=null)
        {
            return "ERROR: Username already exists";
        }
        if(adminRepo.findByEmail(admin.getEmail()) !=null)
        {
            return "ERROR: Email already exists";
        }
        adminRepo.save(admin);
        return "Registration successful";
    }

    @PostMapping("/admin/login")
    public String adminLogin(@RequestBody AdminloginDto a)
    {
        Admin admin = adminRepo.findByUsername(a.getUsername());
        if(admin==null)
        {
            return "ERROR: User not found";
        }
        if(! admin.getUsername().equals(a.getUsername()))
        {
            return "ERROR: Username do not match";
        }
        if(! admin.getPassword().equals(a.getPassword()))
        {
            return "ERROR: Password do not match";
        }
        return "Login successfully";
    }


    @GetMapping("/admin/items")
    public List<SellItem> getAllSells() {
        return sellRepo.findAll();
    }

    @DeleteMapping("/admin/items/{id}")
    public String deleteItem(@PathVariable int id) {
        SellItem sellitem=sellRepo.findById(id).orElseThrow(()->new RuntimeException("Item not found"));
        History history = new History();
        history.setDescription("Admin deleted the sellitem "+sellitem.getItemname()+" posted by: "+sellitem.getUsername());
        historyRepo.save(history);
        sellRepo.deleteById(id);
        return "Item deleted by admin";
    }

    @DeleteMapping("/admin/deleteuser/{userId}/{adminId}")
    public String delete(@PathVariable int userId, @PathVariable int adminId)
    {
        User user=userRepo.findById(userId).orElseThrow(()->new RuntimeException("user not found"));

        History h=new History();
        h.setDescription("Admin "+adminId+" deleted user " +user.getUsername());
        historyRepo.save(h);
        userRepo.delete(user);
        return "Deleted successfully";
    }

}
