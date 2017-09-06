package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
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

    public String registration(String username, String password, Role role){

        User user = userRepository.findOneByUsername(username);

        if((user == null)){
            User newUser = new User(username, password, role);
           // userRepository.save(newUser);

            return "success";
        }

        return "fail";
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


}
