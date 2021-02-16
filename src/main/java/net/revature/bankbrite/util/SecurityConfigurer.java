package net.revature.bankbrite.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.revature.bankbrite.filters.JwtEveryRequestFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtEveryRequestFilter jwtEveryRequestFilter;

	// To configure the Authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsService);
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		// TODO Auto-generated method stub
//		web.ignoring()
//		.antMatchers("/customer/add")
//		.antMatchers("/signin");
//		
	// }

	// To configure the Autherization
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable().authorizeRequests().antMatchers("/signin").permitAll().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtEveryRequestFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeRequests().antMatchers("/branch").hasRole("ADMIN").antMatchers("/customer").hasAnyRole("ADMIN",
				"USER");

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
