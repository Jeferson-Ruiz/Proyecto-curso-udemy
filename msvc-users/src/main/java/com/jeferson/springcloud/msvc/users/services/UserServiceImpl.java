package com.jeferson.springcloud.msvc.users.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.jeferson.springcloud.msvc.users.entities.Role;
import com.jeferson.springcloud.msvc.users.entities.User;
import com.jeferson.springcloud.msvc.users.repositories.RoleRepository;
import com.jeferson.springcloud.msvc.users.repositories.UserRepository;

@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User save(User user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optRole = roleRepository.findByName("ROLE_USER");
        optRole.ifPresent(role -> roles.add(role));
        user.setRoles(roles);
        return repository.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}