package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
    }
 
    @Test
    public void testGetName() {
        // Test sprawdzania nazwy banku
        assertEquals("SweBank", SweBank.getName());
    }

    @Test
    public void testGetCurrency() {
        // Test sprawdzania waluty banku
        assertEquals(SEK, SweBank.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        // Test otwierania konta w banku
        SweBank.openAccount("Alice", DKK);
        assertTrue(SweBank.accountExists("Alice"));
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        // Test wpłacania środków na konto w banku
        try {
			SweBank.openAccount("Mery", SEK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Money depositAmount = new Money(1000, SEK);
        SweBank.deposit("Mery", depositAmount);

        assertEquals(Integer.valueOf(1000), SweBank.getBalance("Mery"));
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        // Test wypłacania środków z konta w banku
        try {
			SweBank.openAccount("Alice", SEK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Money depositAmount = new Money(1000, SEK);
        SweBank.deposit("Alice", depositAmount);

        Money withdrawAmount = new Money(500, SEK);
        SweBank.withdraw("Alice", withdrawAmount);

        assertEquals(Integer.valueOf(500), SweBank.getBalance("Alice"));
    }

    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        // Test sprawdzania salda konta w banku
        try {
			SweBank.openAccount("Alice", DKK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertNotNull(SweBank.getBalance("Alice"));
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        // Test transferu środków między kontami w różnych bankach
        try {
			SweBank.openAccount("Holy", DKK);
			Nordea.openAccount("John", SEK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Money depositAmount = new Money(1000, SEK);
        SweBank.deposit("Holy", depositAmount);
        Money transferAmount = new Money(200, SEK);
        SweBank.transfer("Holy", Nordea, "John", transferAmount);
        
        assertEquals(Integer.valueOf(200), Nordea.getBalance("John"));
        
        
    }

    @Test//nie działa
    public void testTimedPayment() throws AccountDoesNotExistException {
        // Test obsługi planowanych płatności w banku
        try {
			SweBank.openAccount("Jenna", DKK);
			SweBank.openAccount("Bob", SEK);
		} catch (AccountExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        SweBank.addTimedPayment("Alice", "payment1", 2, 0, new Money(100, SEK), SweBank, "Bob");

        // Symulacja upływu czasu, płatność powinna być wykonana
        SweBank.tick();
        assertEquals(new Money(9900, SEK), SweBank.getBalance("Alice"));
        assertEquals(new Money(100, SEK), SweBank.getBalance("Bob"));
    }
}
