package com.campuskart.demo.controllers;


import com.campuskart.demo.models.History;
import com.campuskart.demo.repositories.HistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class HistoryController {
    @Autowired
    HistoryRepo historyRepo;

    @GetMapping("admin/history")
    public List<History> getHistory()
    {
        return historyRepo.findAll();
    }
}



