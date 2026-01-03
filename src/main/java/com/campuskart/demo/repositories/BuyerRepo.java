package com.campuskart.demo.repositories;


import com.campuskart.demo.models.Buyitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyerRepo extends JpaRepository<Buyitem,Integer> {

    List<Buyitem> findByUserid(int id);
}
