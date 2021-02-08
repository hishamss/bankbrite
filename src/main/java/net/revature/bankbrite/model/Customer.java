package net.revature.bankbrite.model;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
	
	public enum Role {
		ROLE_ADMIN, ROLE_USER;
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 16, unique=true)
	private String username;
	@Column(nullable = false, length = 62)
	private String password;
	@Column(nullable = false, length = 40, unique= true)
	private String email;
	
	@Column(nullable= false, length = 5)
	@Enumerated(EnumType.ORDINAL)
	private Role role;
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name = "branch_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Branch branch;
	
	@ManyToMany(mappedBy = "customers")
	private Set<Account> accounts;

}
