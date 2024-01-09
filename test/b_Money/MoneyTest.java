package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
    Currency SEK, DKK, NOK, EUR;
    Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

    @Before
    public void setUp() throws Exception {
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
        SEK100 = new Money(10000, SEK);
        EUR10 = new Money(1000, EUR);
        SEK200 = new Money(20000, SEK);
        EUR20 = new Money(2000, EUR);
        SEK0 = new Money(0, SEK);
        EUR0 = new Money(0, EUR);
        SEKn100 = new Money(-10000, SEK);
    }

    @Test
    public void testGetAmount() {
        assertEquals(Integer.valueOf(10000), SEK100.getAmount());
        assertEquals(Integer.valueOf(0), SEK0.getAmount());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SEK100.getCurrency());
        assertEquals(EUR, EUR10.getCurrency());
    }

    @Test
    public void testToString() {
        assertEquals("100,0 SEK", SEK100.toString());
        assertEquals("0,0 EUR", EUR0.toString());
    }

    @Test
    public void testGlobalValue() {
        assertEquals(Integer.valueOf(1500), SEK100.universalValue());
        assertEquals(Integer.valueOf(3000), SEK200.universalValue());
       
    }

    @Test
    public void testEqualsMoney() {
        assertTrue(SEK100.equals(new Money(10000, SEK)));
        assertFalse(SEK100.equals(EUR10));
    }

    @Test
    public void testAdd() {
        Money result = SEK100.add(EUR10);
        assertEquals(Integer.valueOf(20000), result.getAmount());
        assertEquals(SEK, result.getCurrency());
    }

    @Test
    public void testSub() {
        Money result = SEK100.sub(EUR10);
        assertNotEquals(Integer.valueOf(9900), result.getAmount());
        
    }

    @Test
    public void testIsZero() {
        assertTrue(EUR0.isZero());
        assertFalse(SEK100.isZero());
    }

    @Test
    public void testNegate() {
        Money negated = SEK100.negate();
        assertEquals(Integer.valueOf(-10000), negated.getAmount());
        assertEquals(SEK, negated.getCurrency());
    }

    @Test
    public void testCompareTo() {
        assertEquals(0, SEK100.compareTo(SEK100));
        assertTrue(SEK100.compareTo(SEK200) < 0);
        assertTrue(EUR10.compareTo(SEK100) > 0);
    }
}
