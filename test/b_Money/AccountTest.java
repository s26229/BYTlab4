package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
    Currency SEK;
    Bank SweBank;
    Account testAccount;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        SweBank.openAccount("Hans", SEK);
        testAccount = new Account("Hans", SEK);
        testAccount.deposit(new Money(10000000, SEK));
    }

    @Test 
    public void testAddRemoveTimedPayment() {
        // Test dodawania i usuwania planowanych płatności
        testAccount.addTimedPayment("payment1", 3, 0, new Money(500, SEK), SweBank, "Alice");
        assertTrue(testAccount.timedPaymentExists("payment1"));

        testAccount.removeTimedPayment("payment1");
        assertFalse(testAccount.timedPaymentExists("payment1"));
    }
    //nie wiem jak to zrobić
    @Test//nie powiodło sie za pierwszym razem
    public void testTimedPayment() throws AccountDoesNotExistException {
        // Test obsługi planowanych płatności
        try {
			SweBank.openAccount("Bob", SEK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        testAccount.addTimedPayment("payment2", 2, 0, new Money(200, SEK), SweBank, "Bob");

        // Symulacja upływu czasu, płatność powinna być wykonana
        SweBank.tick();
        assertEquals(new Money(9999800, SEK), testAccount.getBalance());

        // Dodatkowe upływy czasu, płatność powinna być wykonana wielokrotnie
        SweBank.tick();
        SweBank.tick();
        assertEquals(new Money(9999400, SEK), testAccount.getBalance());
    }

    @Test
    public void testAddWithdraw() {
        // Test dodawania i wypłacania pieniędzy z konta
        
        Money withdrawAmount = new Money(200, SEK);

        testAccount.withdraw(withdrawAmount);
        assertEquals(Integer.valueOf(9999800), testAccount.getBalance().getAmount());
    }

    @Test
    public void testGetBalance() {
        // Test sprawdzania salda konta
    	Account account = new Account("Martin", SEK);
        assertEquals(Integer.valueOf(0), account.getBalance().getAmount());
        assertEquals(SEK, account.getBalance().getCurrency());
    }
}
