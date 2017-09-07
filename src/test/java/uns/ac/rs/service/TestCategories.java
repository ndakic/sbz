package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.model.UserCategory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikola Dakic on 9/7/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TestCategories {

    @Autowired
    CategoryService categoryService;

    @Test
    @Transactional
    @Rollback(true)
    public void testUpdateUserCategory(){

        List<UserCategory> userCategories = categoryService.getUserCategories();
        UserCategory userCategory = userCategories.get(0);
        userCategory.setTitle("UpdateTitle");

        UserCategory userCate = categoryService.updateUserCategory(userCategory);

        assertThat(userCate).isNotNull();
    }

}
