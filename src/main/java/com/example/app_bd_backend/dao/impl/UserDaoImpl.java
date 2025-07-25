package com.example.app_bd_backend.dao.impl;

import com.example.app_bd_backend.dao.UserDao;
import com.example.app_bd_backend.data.entity.UserEtt;
import com.example.app_bd_backend.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEtt save(UserEtt user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEtt> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEtt> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserEtt> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserEtt> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}