package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
    Currency SEK, DKK, NOK, EUR;

    @Before
    public void setUp() throws Exception {
        /* Setup currencies with exchange rates */
        SEK = new Currency("SEK", 0.15);
        DKK = new Currency("DKK", 0.20);
        EUR = new Currency("EUR", 1.5);
    }

    @Test
    public void testGetName() {
        assertEquals("SEK", SEK.getName());
        assertEquals("DKK", DKK.getName());
        assertEquals("EUR", EUR.getName());
        assertNotEquals("USD", SEK.getName());
    }

    @Test
    public void testGetRate() {
        assertEquals(0.15, SEK.getRate(), 0.01);
        assertEquals(0.20, DKK.getRate(), 0.01);
        assertEquals(1.5, EUR.getRate(), 0.01);
        assertNotEquals(1.0, SEK.getRate(), 0.01);
    }

    @Test
    public void testSetRate() {
        SEK.setRate(0.25);
        assertEquals(0.25, SEK.getRate(), 0.01);
        
        DKK.setRate(0.18);
        assertEquals(0.18, DKK.getRate(), 0.01);
        
        EUR.setRate(2.0);
        assertEquals(2.0, EUR.getRate(), 0.01);
    }

    @Test
    public void testGlobalValue() {
        assertEquals(150, SEK.universalValue(1000), 0.01);
        assertEquals(200, DKK.universalValue(1000), 0.01);
        assertEquals(1500, EUR.universalValue(1000), 0.01);
        assertNotEquals(100, SEK.universalValue(500), 0.01);
    }

    @Test
    public void testValueInThisCurrency() {
        assertEquals(1333, SEK.valueInThisCurrency(1000, DKK), 0.01);
        assertEquals(750, DKK.valueInThisCurrency(1000, SEK), 0.01);
        assertNotEquals(750, EUR.valueInThisCurrency(1000, DKK), 0.01);
    }
}
