package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.User;
import uns.ac.rs.service.UserService;

import java.util.List;

/**
 * Created by Nikola Dakic on 7/5/17.
 */

@RestController
@RequestMapping("/api/user")
public class UserController {



    @Autowired
    private UserService userService;


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

    @PostMapping(value = "/registration", consumes = "application/json", produces = "text/plain")
    public String registration(@RequestBody User user) throws Exception{
        return userService.registration(user.getUsername(), user.getPassword());
    }





}
