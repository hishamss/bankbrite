package net.revature.bankbrite.model;

import java.sql.Timestamp;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, columnDefinition = "CHAR(8)")
	private String acc_no;
	@Column(nullable = false, columnDefinition = "CHAR(8)")
	private String recepient_acc_no;
	@Column(nullable = false, length=100)
	private String note;
	@Column(name = "date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
	private Timestamp date;
	@Column(columnDefinition="Decimal(10,2)", nullable = false)
	private float amount;
	@Column(columnDefinition="Decimal(10,2)", nullable = false)
	private float curr_balance;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name = "trans_type")
	private TransactionType transType;
	

}
