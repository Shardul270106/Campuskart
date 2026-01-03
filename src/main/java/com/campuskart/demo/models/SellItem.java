package com.campuskart.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellItem {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int itemId;
    @Column(nullable = false)
    String itemname;
    @Column(nullable = false)
    String price;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    String imagepath;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    int userId;
    @Column(nullable = false)
    String status;
    @Column(nullable = false)
    LocalDateTime selldate;

    @PrePersist //jab object of transaction table banega teva prepersist annotation on cretae use karnar
    protected void onCreate()
    {
        this.selldate=LocalDateTime.now();
    }
}




