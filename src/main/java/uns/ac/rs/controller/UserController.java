package uns.ac.rs.controller;

import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.config.Data;
import uns.ac.rs.model.*;
import uns.ac.rs.model.dto.LoginDTO;
import uns.ac.rs.model.dto.RegistDTO;
import uns.ac.rs.repository.AuthorityRepository;
import uns.ac.rs.security.TokenUtils;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.CategoryService;
import uns.ac.rs.service.UserDetailsServiceImpl;
import uns.ac.rs.service.UserService;
import uns.ac.rs.security.LoginAttemptService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Autowired
    private LoginAttemptService loginAttemptService;


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
    public ResponseEntity registration(@RequestBody @Valid RegistDTO user, BindingResult result) throws Exception{

        if(result.getErrorCount() > 0)
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String password = encoder.encode(user.getPassword());

        user.setPassword(password);

        User new_user = new User();
        new_user.setUsername(user.getUsername());
        new_user.setPassword(user.getPassword());

        // check if already exist
        User checkUser = userService.registration(new_user);
        if(checkUser == null){ return new ResponseEntity<>(null, HttpStatus.NO_CONTENT); }

        String authToken = request.getHeader("authorization");

        // for logging purpose - Manager
        if(authToken != ""){
            Claims claims = tokenUtils.getClaimsFromToken(authToken);
            String username = tokenUtils.getUsernameFromToken(authToken);
            String role = claims.get("role").toString();

            if(role.equalsIgnoreCase("manager"))
                logger.info("Manager: " + username + " added new user: " + user.toString());
        }

        return new ResponseEntity<User>(checkUser, HttpStatus.OK);

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
        System.out.println(loginDTO.toString());
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsername(), loginDTO.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);
            CustomUserDetails details = userDetailsService.loadUserByUsername(loginDTO.getUsername());

            // LOGIN SUCCEED
            String xfHeader = request.getHeader("X-Forwarded-For");
            if (xfHeader == null) {
                loginAttemptService.loginSucceeded(request.getRemoteAddr());
            } else {
                loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
            }

            logger.info("LOGIN DETAILS: " + details.toString());

            return tokenUtils.generateToken(details).toString();
        } catch (Exception ex) {

            // LOGIN FAILED
            String xfHeader = request.getHeader("X-Forwarded-For");
            if (xfHeader == null) {
                loginAttemptService.loginFailed(request.getRemoteAddr());
            } else {
                loginAttemptService.loginFailed(xfHeader.split(",")[0]);
            }
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
