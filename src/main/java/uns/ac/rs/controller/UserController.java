package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.config.Data;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserCategory;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Data data;

    @Autowired
    private ArticleService articleService;


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

        List<User> users = userService.findAll();

        if(users.isEmpty()){
            data.populateUserData();
            System.out.println("Populate user data!");
        }

        List<Article> articles = articleService.getArticles();

        if(articles.isEmpty()){
            data.populateArticleData();
            System.out.println("Populate article data!");
        }


        return userService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping(value = "/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity registration(@RequestBody User user) throws Exception{

        User checkUser = userService.registration(user);

        if(checkUser == null){ return new ResponseEntity<User>(new User(), HttpStatus.NO_CONTENT); }

        return new ResponseEntity<User>(checkUser, HttpStatus.OK);

    }

    @PostMapping(value = "/update", consumes = "application/json")
    public ResponseEntity updateUser(@RequestBody User user) throws Exception{

        User new_user = userService.updateUser(user);

        return new ResponseEntity<User>(new_user, HttpStatus.OK);

    }

    @GetMapping(value = "/categories", produces = "application/json")
    public List<UserCategory> allCategories(){
        return categoryService.getUserCategories();
    }



}
