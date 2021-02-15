package net.revature.bankbrite.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.revature.bankbrite.dto.MyUserDetails;
import net.revature.bankbrite.model.Customer;
import net.revature.bankbrite.repos.CustomerRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	CustomerRepo customerRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Customer> customer = customerRepo.findByUsername(username);
		
		customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		
		// Using method referencing instead of lambda expression
		//return customer.map((c) -> new MyUserDetails(c)).get();
		return customer.map(MyUserDetails::new).get();
	}

}
