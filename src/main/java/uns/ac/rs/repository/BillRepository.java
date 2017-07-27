package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.Bill;

import java.util.List;

/**
 * Created by Nikola Dakic on 7/26/17.
 */
public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findOneByBuyerUsername(String username);
}
