package com.campuskart.demo.repositories;

import com.campuskart.demo.models.SellItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellRepo extends JpaRepository<SellItem,Integer>
{


    List<SellItem> findByStatus(String available);



    List<SellItem> findByUserId(int id);
}
