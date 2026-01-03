package com.campuskart.demo.controllers;

import com.campuskart.demo.dto.BuyDto;
import com.campuskart.demo.models.Buyitem;
import com.campuskart.demo.models.SellItem;
import com.campuskart.demo.models.User;
import com.campuskart.demo.repositories.BuyerRepo;
import com.campuskart.demo.repositories.SellRepo;
import com.campuskart.demo.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class BuyController
{
    @Autowired
    UserRepo userRepo;
    @Autowired
    BuyerRepo buyerRepo;

    @Autowired
    SellRepo sellRepo;

    @PostMapping("/buyitems")
    public String buyitem(@RequestBody BuyDto buyDto)
    {
        User user=userRepo.findByUsername(buyDto.getUsername());
        if(user==null)
        {
            return "Enter proper Username";
        }
        Buyitem b=new Buyitem();
        b.setUserid(user.getId());
        b.setBuyername(user.getName());

        SellItem s=sellRepo.findById(buyDto.getItemid()).orElseThrow(()->new RuntimeException("sell item not found"));
        if(buyDto.getUsername().equals(s.getUsername()))
        {
            return "username cannot be same";
        }
        b.setUsername(buyDto.getUsername());
        b.setEmail(buyDto.getEmail());
        b.setItemid(s.getItemId());
        b.setItemname(s.getItemname());
        b.setPrice(s.getPrice());
        b.setSellername(s.getUsername());
        b.setSellerUserid(s.getUserId());
        b.setImagepath(s.getImagepath());

        buyerRepo.save(b);
        s.setStatus("sold");
        sellRepo.save(s);
        return "Your Order is Buy Successfully!";
    }

    @GetMapping("/api/user/{id}/buyitem")
    public List<Buyitem> getUserbuys(@PathVariable int id) {
        return buyerRepo.findByUserid(id);
    }

    @GetMapping("/{userid}/invoices")
    public List<Buyitem> getInvoicesByUser(@PathVariable int userid) {
        return buyerRepo.findByUserid(userid);
    }

    @GetMapping("/admin/orders")
    public List<Buyitem> getbuyer ()
    {
        return buyerRepo.findAll();
    }
}
