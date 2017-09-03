package uns.ac.rs.controller;


import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.Bill;
import uns.ac.rs.model.Item;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nikola Dakic on 9/1/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ControllersTests {

    private static final String url_prefix = "/api/bill";

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.
                webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAllBills() throws Exception{
        mockMvc.perform(get(url_prefix + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

    }

    // check enough articles in stock
    @Test
    public void testCheckBill() throws Exception{

        Article article1 = new Article();
        article1.setTitle("Mac");
        article1.setAmount(10);

        Item item = new Item();
        item.setArticle(article1);
        item.setQuantity(20);

        List<Item> items = new ArrayList<>();
        items.add(item);

        Bill bill = new Bill();
        bill.setItems(items);

        Gson gson = new Gson();
        String data = gson.toJson(bill);

        mockMvc.perform(post(url_prefix + "/check_bill")
                .content(data)
                .contentType(contentType))
                .andExpect(status().isNoContent());
    }


}
