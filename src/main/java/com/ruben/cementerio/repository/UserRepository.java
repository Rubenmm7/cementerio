package com.ruben.cementerio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ruben.cementerio.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //userRepository.findAll();
    //userRepository.save(user);
    //userRepository.findById(id);
    //userRepository.deleteById(id);

}
