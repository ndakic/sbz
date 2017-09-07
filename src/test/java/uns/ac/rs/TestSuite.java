package uns.ac.rs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import uns.ac.rs.service.*;

/**
 * Created by Nikola Dakic on 9/7/17.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestArticles.class,
        TestBills.class,
        TestEvents.class,
        TestCategories.class,
        TestUsers.class
})
public class TestSuite {
}
