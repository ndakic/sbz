package uns.ac.rs.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserAuthority;
import uns.ac.rs.model.UserProfile;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.repository.AuthorityRepository;
import uns.ac.rs.repository.UserAuthorityRepository;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.security.JWT;
import uns.ac.rs.security.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

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
        System.out.println("alo:" + authToken);
        if(authToken == null || authToken == "")
            role = "customer";
        else{
            Claims claims = tokenUtils.getClaimsFromToken(authToken);
            role = claims.get("role").toString();
        }

        List<Long> authorities = new ArrayList<>();

        if(!role.equalsIgnoreCase("manager")){
            user.setRole(Role.customer);
            user.setUserProfile(new UserProfile("",0.0, categoryService.getUserCatByTitle("basic")));

            User customer = userRepository.findOneByUsername("daka1");
            for(UserAuthority userAuthority: customer.getUserAuthorities())
                authorities.add(userAuthority.getAuthority_id().getId());
        }else{
            user.setRole(user.getRole());
            user.setUserProfile(new UserProfile("", user.getUserProfile().getPoints(), categoryService.getUserCatByTitle(user.getUserProfile().getUserCategory().getTitle())));

            if(user.getRole() == Role.seller){
                User customer = userRepository.findOneByUsername("daka2");

                for(UserAuthority userAuthority: customer.getUserAuthorities())
                    authorities.add(userAuthority.getAuthority_id().getId());
            }

            if(user.getRole() == Role.manager){
                User customer = userRepository.findOneByUsername("daka3");

                for(UserAuthority userAuthority: customer.getUserAuthorities())
                    authorities.add(userAuthority.getAuthority_id().getId());
            }

        }


        user.setDate(new Date());

        System.out.println("user: " + user.toString());
        User new_user = userRepository.save(user);
        new_user.setPassword("[PROTECTED]");

        for(Long id: authorities)
            userAuthorityRepository.save(new UserAuthority(new_user, authorityRepository.getOne(id)));

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
