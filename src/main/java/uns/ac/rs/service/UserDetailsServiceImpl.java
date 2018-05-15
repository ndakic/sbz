package uns.ac.rs.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import uns.ac.rs.service.securityService.LoginAttemptService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

  @Override
  @Transactional
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      final String ip = getClientIP();
      System.out.println("IP: " + ip);
      System.out.println("status: " + loginAttemptService.isBlocked(ip));

      if (loginAttemptService.isBlocked(ip)) {
          throw new RuntimeException("blocked");
      }

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
    	
    	return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getRole(), grantedAuthorities);
    }
  }

    private final String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
