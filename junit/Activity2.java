package JUnit_activity;


import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import JUnit_activity.BankAccount.NotEnoughFundsException;
import JUnit_activity.BankAccount;

public class Activity2 {
	
	@Test
	void notEnoughFunds() {
	    // Create an object for BankAccount class
	    BankAccount account = new BankAccount(9);

	    // Assertion for exception
	    assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10));
	}
	
	@Test
	void enoughFunds() {
		// create bank account object
		BankAccount account = new BankAccount(100);	
		
		// Assertion for no exceptions
		Assertions.assertDoesNotThrow(() -> account.withdraw(100));
	}		
}