package uns.ac.rs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.*;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.model.enums.StatusOfArticle;
import uns.ac.rs.repository.AuthorityRepository;
import uns.ac.rs.repository.UserAuthorityRepository;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.EventService;
import uns.ac.rs.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikola Dakic on 9/10/17.
 */

@Service
public class Data {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventService eventService;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;


    public void populateUserData() throws Exception{

        SpendingLimit spendingLimit1B = new SpendingLimit(10000.0, 50000.0, 3.0);
        SpendingLimit spendingLimit2B = new SpendingLimit(50000.0, 200000.0, 5.0);
        SpendingLimit spendingLimit3B = new SpendingLimit(200000.0, 1000000.0, 8.0);

        List<SpendingLimit> spendingLimitsBasic = new ArrayList<>();
        spendingLimitsBasic.add(spendingLimit1B);
        spendingLimitsBasic.add(spendingLimit2B);
        spendingLimitsBasic.add(spendingLimit3B);


        SpendingLimit spendingLimit9S = new SpendingLimit(1000.0, 5000.0, 3.0);
        SpendingLimit spendingLimit10S = new SpendingLimit(5000.0, 10000.0, 4.0);
        SpendingLimit spendingLimit11S = new SpendingLimit(10000.0, 50000.0, 6.0);
        SpendingLimit spendingLimit12S = new SpendingLimit(50000.0, 200000.0, 8.0);
        SpendingLimit spendingLimit13S = new SpendingLimit(200000.0, 1000000.0, 10.0);

        List<SpendingLimit> spendingLimitsSilver = new ArrayList<>();
        spendingLimitsSilver.add(spendingLimit9S);
        spendingLimitsSilver.add(spendingLimit10S);
        spendingLimitsSilver.add(spendingLimit11S);
        spendingLimitsSilver.add(spendingLimit12S);
        spendingLimitsSilver.add(spendingLimit13S);


        SpendingLimit spendingLimit4G = new SpendingLimit(1000.0, 5000.0, 4.0);
        SpendingLimit spendingLimit5G = new SpendingLimit(5000.0, 10000.0, 5.0);
        SpendingLimit spendingLimit6G = new SpendingLimit(10000.0, 50000.0, 7.0);
        SpendingLimit spendingLimit7G = new SpendingLimit(50000.0, 200000.0, 9.0);
        SpendingLimit spendingLimit8G = new SpendingLimit(200000.0, 1000000.0, 12.0);

        List<SpendingLimit> spendingLimitsGolden = new ArrayList<>();
        spendingLimitsGolden.add(spendingLimit4G);
        spendingLimitsGolden.add(spendingLimit5G);
        spendingLimitsGolden.add(spendingLimit6G);
        spendingLimitsGolden.add(spendingLimit7G);
        spendingLimitsGolden.add(spendingLimit8G);

        UserCategory userCategoryBasic = new UserCategory("basic", spendingLimitsBasic);
        UserCategory userCategorySilver = new UserCategory("silver", spendingLimitsSilver);
        UserCategory userCategoryGold = new UserCategory("golden", spendingLimitsGolden);

        UserCategory noCategory = new UserCategory("-", null);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);
        String password = encoder.encode("123");

        User user1 = new User("daka1", password, Role.customer);
        user1.setDate(new Date());
        user1.setUserProfile(new UserProfile("", 0.0, userCategoryBasic));

        User user2 = new User("daka2", password, Role.seller);
        user2.setDate(new Date());
        user2.setUserProfile(new UserProfile("", 0.0, userCategorySilver));

        User user3 = new User("daka3", password, Role.manager);
        user3.setDate(new Date());
        user3.setUserProfile(new UserProfile("", 0.0, userCategoryGold));


        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);


        // customer

        Authority create_bill = new Authority("create_bill");
        Authority submit_bill = new Authority("submit_bill");

        // seller
        Authority all_bills = new Authority("all_bills");
        Authority check_bill = new Authority("check_bill");
        Authority reject_bill = new Authority("reject_bill");
        Authority orders_article = new Authority("orders_article");
        Authority order_more_article = new Authority("order_more_article");
        Authority add_article = new Authority("add_article");

        // manager
        Authority category = new Authority("category");
        Authority event = new Authority("event");
        Authority user = new Authority("user");
        // + seller + customer


        authorityRepository.save(create_bill);
        authorityRepository.save(submit_bill);
        authorityRepository.save(all_bills);
        authorityRepository.save(check_bill);
        authorityRepository.save(reject_bill);
        authorityRepository.save(orders_article);
        authorityRepository.save(order_more_article);
        authorityRepository.save(add_article);
        authorityRepository.save(category);
        authorityRepository.save(event);
        authorityRepository.save(user);

        UserAuthority customerAuthority1 = new UserAuthority(user1, create_bill);
        UserAuthority customerAuthority2 = new UserAuthority(user1, submit_bill);

        UserAuthority sellerAuthority1 = new UserAuthority(user2, all_bills);
        UserAuthority sellerAuthority2 = new UserAuthority(user2, check_bill);
        UserAuthority sellerAuthority3 = new UserAuthority(user2, reject_bill);
        UserAuthority sellerAuthority4 = new UserAuthority(user2, orders_article);
        UserAuthority sellerAuthority5 = new UserAuthority(user2, order_more_article);
        UserAuthority sellerAuthority6 = new UserAuthority(user2, add_article);


        UserAuthority managerAuthority1 = new UserAuthority(user3, category);
        UserAuthority managerAuthority2 = new UserAuthority(user3, event);
        UserAuthority managerAuthority3 = new UserAuthority(user3, user);

        UserAuthority managerAuthority4 = new UserAuthority(user3, create_bill);
        UserAuthority managerAuthority5 = new UserAuthority(user3, submit_bill);
        UserAuthority managerAuthority6 = new UserAuthority(user3, all_bills);
        UserAuthority managerAuthority7 = new UserAuthority(user3, check_bill);
        UserAuthority managerAuthority8 = new UserAuthority(user3, reject_bill);
        UserAuthority managerAuthority9 = new UserAuthority(user3, orders_article);
        UserAuthority managerAuthority10 = new UserAuthority(user3, order_more_article);
        UserAuthority managerAuthority11 = new UserAuthority(user3, add_article);


        userAuthorityRepository.save(customerAuthority1);
        userAuthorityRepository.save(customerAuthority2);
        userAuthorityRepository.save(sellerAuthority1);
        userAuthorityRepository.save(sellerAuthority2);
        userAuthorityRepository.save(sellerAuthority3);
        userAuthorityRepository.save(sellerAuthority4);
        userAuthorityRepository.save(sellerAuthority5);
        userAuthorityRepository.save(sellerAuthority6);
        userAuthorityRepository.save(managerAuthority1);
        userAuthorityRepository.save(managerAuthority2);
        userAuthorityRepository.save(managerAuthority3);
        userAuthorityRepository.save(managerAuthority4);
        userAuthorityRepository.save(managerAuthority5);
        userAuthorityRepository.save(managerAuthority6);
        userAuthorityRepository.save(managerAuthority7);
        userAuthorityRepository.save(managerAuthority8);
        userAuthorityRepository.save(managerAuthority9);
        userAuthorityRepository.save(managerAuthority10);
        userAuthorityRepository.save(managerAuthority11);


    }


    public void populateArticleData() throws Exception{

        ArticleCategory superCategory = new ArticleCategory("broad_consumption", 15.0, null);

        ArticleCategory computers = new ArticleCategory("computers", 10.0, null);
        ArticleCategory tvs = new ArticleCategory("tvs", 10.0, null);
        ArticleCategory laptops = new ArticleCategory("laptops", 10.0, null);
        ArticleCategory cosmetics = new ArticleCategory("cosmetics", 10.0, superCategory);

        Article article1 = new Article("MacBook Pro", computers, 2000.0, 10, new Date(), StatusOfArticle.ACTIVE, 5, false, null, null);
        Article article2 = new Article("Sony TV", tvs, 1000.0, 10, new Date(), StatusOfArticle.ACTIVE, 5, false, null, null);
        Article article3 = new Article("Dell LapTop", laptops, 1500.0, 10, new Date(), StatusOfArticle.ACTIVE, 5, false, null, null);
        Article article4 = new Article("Gel", cosmetics, 200.0, 10, new Date(), StatusOfArticle.ACTIVE, 5, false, null, null);

        articleService.addArticle(article1);
        articleService.addArticle(article2);
        articleService.addArticle(article3);
        articleService.addArticle(article4);


        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        String string1 = "09/01/2017";
        String string2 = "09/24/2017";

        Date date1 = formatter.parse(string1);
        Date date2 = formatter.parse(string2);
        List<ArticleCategory> articleCategoryList1 = new ArrayList<>();
        articleCategoryList1.add(computers);

        String string3 = "11/01/2017";
        String string4 = "01/10/2018";
        Date date3 = formatter.parse(string3);
        Date date4 = formatter.parse(string4);
        List<ArticleCategory> articleCategoryList2 = new ArrayList<>();
        articleCategoryList2.add(computers);
        articleCategoryList2.add(tvs);
        articleCategoryList2.add(cosmetics);


        Event event1 = new Event("PC discount", date1, date2, 5.0 ,articleCategoryList1);
        Event event2 = new Event("New Year", date3, date4, 10.0, articleCategoryList2 );

        eventService.addEvent(event1);
        eventService.addEvent(event2);

    }

}
