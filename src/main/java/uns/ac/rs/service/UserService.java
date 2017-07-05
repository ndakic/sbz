package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.User;
import uns.ac.rs.repository.UserRepository;

/**
 * Created by Nikola Dakic on 7/6/17.
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String test(){

        User user = userRepository.findOneByUsername("daka");
        System.out.println(user.toString());

        return user.getUsername();
    }

}
