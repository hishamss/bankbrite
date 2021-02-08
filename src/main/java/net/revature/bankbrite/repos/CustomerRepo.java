package net.revature.bankbrite.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.revature.bankbrite.model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{
	
	Optional<Customer> findByUsername(String username);

	
}
