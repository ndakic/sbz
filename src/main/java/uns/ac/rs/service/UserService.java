package uns.ac.rs.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserProfile;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.security.JWT;
import uns.ac.rs.security.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/6/17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    private HttpServletRequest request;


    public List<User> findAll(){

        List<User> users;
        users = userRepository.findAll();

        return users;

    }

    public User findUser(String username){

        User user = userRepository.findOneByUsername(username);

        if(!(user == null)){
            user.setPassword("sensitive-data");
        }

        return user;
    }

    public User registration(User user) throws Exception{

        User alreadyExist = userRepository.findOneByUsername(user.getUsername());

        if(alreadyExist != null){ return null;}

        String authToken = request.getHeader("authorization");

        String role = "";

        if(authToken != null){
            Claims claims = tokenUtils.getClaimsFromToken(authToken);
            role = claims.get("role").toString();
        }else{
            role = "customer";
        }

        if(!role.equalsIgnoreCase("manager")){
            user.setRole(Role.customer);
            user.setUserProfile(new UserProfile("",0.0, categoryService.getUserCatByTitle("basic")));
        }else{
            user.setRole(user.getRole());
            user.setUserProfile(new UserProfile("",user.getUserProfile().getPoints(), categoryService.getUserCatByTitle(user.getUserProfile().getUserCategory().getTitle())));
        }


        user.setDate(new Date());

        User new_user = userRepository.save(user);
        new_user.setPassword("[PROTECTED]");

        return user;

    }

    public User updateUser(User user) throws Exception{

        User new_user = userRepository.findOneByUsername(user.getUsername());
        new_user.setFirstName(user.getFirstName());
        new_user.setLastName(user.getLastName());
        new_user.getUserProfile().setAddress(user.getUserProfile().getAddress());

        if(new_user.getDate() == null){ new_user.setDate(new Date());}

        userRepository.save(new_user);

        new_user.setPassword("sensitive-data");

        return new_user;

    }

    public User findUserByUsername(String username)throws Exception{
       return userRepository.findOneByUsername(username);
    }

    public User saveUser(User user) throws Exception{
        return userRepository.save(user);
    }

    public void deleteUser(String username) throws Exception{

        User user =  userRepository.findOneByUsername(username);
        userRepository.delete(user);

    }

}
