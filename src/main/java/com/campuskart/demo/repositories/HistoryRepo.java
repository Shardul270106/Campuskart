package com.campuskart.demo.repositories;

import com.campuskart.demo.models.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<History,Integer> {

}
