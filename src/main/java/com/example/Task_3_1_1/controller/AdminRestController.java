package com.example.Task_3_1_1.controller;


import com.example.Task_3_1_1.exceptionHandling.NoSuchUserException;
import com.example.Task_3_1_1.model.User;
import com.example.Task_3_1_1.service.InitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Task_3_1_1.service.RoleService;
import com.example.Task_3_1_1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final UserService userService;

    private final RoleService roleService;

    private final InitServiceImpl initServiceImpl;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService,
                               InitServiceImpl initServiceImpl) {
        this.userService = userService;
        this.roleService = roleService;
        this.initServiceImpl = initServiceImpl;
    }

    @PostConstruct
    public void firstInitialization() {
        initServiceImpl.initServiceImpl();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> allUsers(Model model) {
        return new ResponseEntity<>(userService.allUsers(),HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        User user = userService.get(id);
        if(user == null){
            throw new NoSuchUserException("There is no user with id "+ id);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> add(@RequestBody User user){
        roleService.setupRoles(user);
        userService.add(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<User> update(@RequestBody User user){
        roleService.setupRoles(user);
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable("id") int id) {

        User user = userService.get(id);

        if(user == null){
            throw new NoSuchUserException("There is no user with id "+ id);
        }
        userService.delete(id);
    }
}