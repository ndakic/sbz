package uns.ac.rs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()
				.antMatchers("/", "/index.html", "/index.js").permitAll()
				.antMatchers("/bower_components/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/errors/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/layouts/**").permitAll()
				.antMatchers("/utils/**").permitAll()
				.antMatchers("/users/**").permitAll()

				.antMatchers("/api/user/login/**").permitAll()
				.antMatchers("/api/user/registration/**").permitAll()

				.antMatchers("/api/article/bill").hasAuthority("create_bill")
				.antMatchers("/api/article/submit_bill").hasAuthority("submit_bill")

				.antMatchers("/api/bill/all").hasAuthority("all_bills")
				.antMatchers("/api/bill/check_bill").hasAuthority("check_bill")
				.antMatchers("/api/bill/reject_bill").hasAuthority("reject_bill")
				.antMatchers("/api/article/orders").hasAuthority("orders_article")
				.antMatchers("/api/article/order_more").hasAuthority("order_more_article")
				.antMatchers("/api/article/add").hasAuthority("add_article")

				.antMatchers("/api/category/users","/api/category/articles", "/api/category/article/add",
						                 "/api/category/article/delete/**", "/api/category/user/update" ).hasAuthority("category")
				.antMatchers("/api/event/add", "/api/event/delete/**").hasAuthority("event")
				.antMatchers("/api/user/delete/**").hasAuthority("user")

				.anyRequest().authenticated();



		// Custom JWT based authentication
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(),
				UsernamePasswordAuthenticationFilter.class);
	}
	

}
