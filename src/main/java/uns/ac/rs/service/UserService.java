package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.security.JWT;

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

    public String registration(String username, String password){

        User user = userRepository.findOneByUsername(username);

        if((user == null)){
            User newUser = new User(username, password, Role.customer);
            userRepository.save(newUser);

            return "success";
        }

        return "fail";
    }


}
