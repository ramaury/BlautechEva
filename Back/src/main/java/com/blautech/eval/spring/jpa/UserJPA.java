package com.blautech.eval.spring.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blautech.eval.spring.model.User;

public interface UserJPA extends JpaRepository<User, Long> {

}
