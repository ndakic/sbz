package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.model.User;
import uns.ac.rs.model.enums.Role;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikola Dakic on 9/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TestUsers {

    @Autowired
    UserService userService;

    @Test
    public void testLogin(){

        String test1 = userService.login("daka1", "1234");
        String test2 = userService.login("daka", "123");

        assertThat(test1).isNull();
        assertThat(test2).isNotNull();

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testRegistration() throws Exception{

        User alreadyExist = userService.registration(new User("daka","bla", Role.customer));
        User newUser = userService.registration(new User("test243","blabla", Role.customer));

        assertThat(alreadyExist).isNull();
        assertThat(newUser).isNotNull();
    }

}
