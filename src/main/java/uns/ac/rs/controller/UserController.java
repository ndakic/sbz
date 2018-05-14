package uns.ac.rs.controller;

import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.config.Data;
import uns.ac.rs.model.*;
import uns.ac.rs.model.dto.LoginDTO;
import uns.ac.rs.repository.AuthorityRepository;
import uns.ac.rs.security.TokenUtils;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.CategoryService;
import uns.ac.rs.service.UserDetailsServiceImpl;
import uns.ac.rs.service.UserService;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private HttpServletRequest request;



    private static final Logger logger = LogManager.getLogger(UserController.class);


    @GetMapping(value = "/all", produces = "application/json")
    public List<User> all(){

        List<User> users = userService.findAll();

        for(User user: users)
            user.setPassword("[PROTECTED]");

        return users;
    }

    @GetMapping(value = "/{username}")
    public User findUser(@PathVariable String username){
        return userService.findUser(username);
    }


    @PostMapping(value = "/registration", consumes = "application/json", produces = "application/json")
    public ResponseEntity registration(@RequestBody User user) throws Exception{
        System.out.println(user.toString());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String password = encoder.encode(user.getPassword());


        user.setPassword(password);

        User checkUser = userService.registration(user);

        if(checkUser == null){ return new ResponseEntity<User>(new User(), HttpStatus.NO_CONTENT); }

        String authToken = request.getHeader("authorization");
        Claims claims = tokenUtils.getClaimsFromToken(authToken);
        String username = tokenUtils.getUsernameFromToken(authToken);
        String role = claims.get("role").toString();

        if(role.equalsIgnoreCase("manager"))
            logger.info("Manager: " + username + " added new user: " + user.toString());


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


    @PostMapping(value = "/login", consumes = "application/json", produces = "text/plain")
    public String login(@RequestBody LoginDTO loginDTO) throws Exception{

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

        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            CustomUserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

            logger.info("LOGIN DETAILS: " + details.toString());

            return tokenUtils.generateToken(details).toString();
        } catch (Exception ex) {
            return "Error!";
        }
    }

    @GetMapping(value = "/authorities", produces = "application/json")
    public List<Authority> userAuth() throws Exception{
        return authorityRepository.findAll();
    }

    @PostMapping(value = "/delete/{username}")
    public ResponseEntity delete(@PathVariable String username) throws Exception{

        userService.deleteUser(username);


        String authToken = request.getHeader("authorization");
        String manager = tokenUtils.getUsernameFromToken(authToken);


        logger.info("Manager: " + manager + " has deleted user: " + username);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
