package net.revature.bankbrite.model;



import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="Decimal(10,2) default 0.0", nullable = false)
	private float balance;
	
	@ManyToOne
	@JoinColumn(name = "acc_type_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private AccountType accountType;
	
	@ManyToMany
	@JoinTable(name = "customer_account")
	@JsonIgnore
	private Set<Customer> customers;

}
