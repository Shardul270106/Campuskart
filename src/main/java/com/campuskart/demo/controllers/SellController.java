package com.campuskart.demo.controllers;

import com.campuskart.demo.models.SellItem;
import com.campuskart.demo.repositories.SellRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class SellController {
    @Autowired
    SellRepo sellrepo;

    @PostMapping("/sellitems")
    public String sellitems(@RequestBody SellItem sell) {
        sell.setStatus("Available");
        sellrepo.save(sell);
        return "Item is on sell";
    }

    @GetMapping("/sellitems")
    public List<SellItem> getAllItems() {
        return sellrepo.findByStatus("AVAILABLE");
    }


    @GetMapping("/api/user/{id}/sellitem")
    public List<SellItem> getUserSells(@PathVariable int id) {
        return sellrepo.findByUserId(id);
    }


}