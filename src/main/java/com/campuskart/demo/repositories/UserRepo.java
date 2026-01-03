package com.campuskart.demo.repositories;

import com.campuskart.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {


    List<User> findByUsernameContainingIgnoreCase(String keyword);

    List<User> findByMobileContaining(String keyword);

    User findByUsername(String username);

    User findByCollegeId(String collegeId);
}




