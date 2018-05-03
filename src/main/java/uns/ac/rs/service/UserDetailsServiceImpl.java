package uns.ac.rs.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.CustomUserDetails;
import uns.ac.rs.model.User;
import uns.ac.rs.model.UserAuthority;
import uns.ac.rs.repository.UserRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findOneByUsername(username);

    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
    } else {
    	List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    	for (UserAuthority ua: user.getUserAuthorities()) {
    		grantedAuthorities.add(new SimpleGrantedAuthority(ua.getAuthority().getName()));
    	}
    	
    	//Java 1.8 way   	
    	/*List<GrantedAuthority> grantedAuthorities = user.getUserAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
                .collect(Collectors.toList());*/
    	
    	return new CustomUserDetails(
    		  user.getUsername(),
    		  user.getPassword(),
              user.getRole(),
    		  grantedAuthorities);
    }
  }

}
