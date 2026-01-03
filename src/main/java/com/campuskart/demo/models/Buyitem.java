package com.campuskart.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Buyitem
{
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    int itemid;
    @Column(nullable = false)
    String itemname;
    @Column(nullable = false)
    int sellerUserid;
    @Column(nullable = false)
    String sellername;
    @Column(nullable = false)
    String price;
    @Column(nullable = false)
    int userid;
    @Column(nullable = false)
    String buyername;
    @Column(nullable = false)
    String username;
    @Column(nullable = false)
    String email;
    @Column(columnDefinition = "LONGTEXT",nullable = false)
    String imagepath;
    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime date;
}




