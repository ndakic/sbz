package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserProfile;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.security.JWT;

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

    public String login(String username, String password){

        String response = null;
        User user = userRepository.findOneByUsernameAndPassword(username, password);

        if(!(user == null)){
            String token = JWT.createJWT(user.getUsername(), user.getRole().toString());
            response = token;
        }

        return response;
    }

    public User registration(User user) throws Exception{

        User alreadyExist = userRepository.findOneByUsername(user.getUsername());

        if(alreadyExist != null){ return null;}

        if(user.getRole() == Role.customer){
            if(user.getUserProfile() == null){
                user.setUserProfile(new UserProfile("",0.0, categoryService.getUserCatByTitle("basic")));
            }else{
                user.getUserProfile().setPoints(0.0);
            }
        }

        user.setDate(new Date());

        User new_user = userRepository.save(user);
        new_user.setPassword("sensitive-data");

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

}
