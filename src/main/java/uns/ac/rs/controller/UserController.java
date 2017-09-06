package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserCategory;
import uns.ac.rs.repository.UserCategoriesRepository;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.service.UserService;

import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/5/17.
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCategoriesRepository userCategoriesRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/all", produces = "application/json")
    public List<User> all(){
        return userService.findAll();
    }

    @GetMapping(value = "/{username}")
    public User findUser(@PathVariable String username){
        return userService.findUser(username);
    }

    @PostMapping(value = "/login", consumes = "application/json", produces = "text/plain")
    public String login(@RequestBody User user) throws Exception{
        return userService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping(value = "/registration", consumes = "application/json")
    public ResponseEntity registration(@RequestBody User user) throws Exception{

        User u = userRepository.findOneByUsername(user.getUsername());

        if(u != null){ return new ResponseEntity<User>(u, HttpStatus.NO_CONTENT); }

        user.setDate(new Date());
        user.getUserProfile().setPoints(0.0);
        User new_user = userRepository.save(user);
        new_user.setPassword("null");

        return new ResponseEntity<User>(new_user, HttpStatus.OK);

    }

    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) throws Exception{

        User new_user = userService.updateUser(user);

        return new ResponseEntity<User>(new_user, HttpStatus.OK);

    }

    @GetMapping(value = "/categories", produces = "application/json")
    public List<UserCategory> allCategories(){
        return userCategoriesRepository.findAll();
    }



}
