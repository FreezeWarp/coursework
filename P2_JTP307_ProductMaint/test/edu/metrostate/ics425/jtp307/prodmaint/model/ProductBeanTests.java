package edu.metrostate.ics425.jtp307.prodmaint.model;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Basic unit tests to test the equals, hashCode, and getYearsReleased methods of {@link ProductBean}.
 * 
 * @author Joseph T. Parsons
 */
public class ProductBeanTests {
    final ProductBean product1 = new ProductBean();
    final ProductBean product2 = new ProductBean();
    final ProductBean product3 = new ProductBean();
    final ProductBean product4 = new ProductBean();
    
    /**
     * Populate our ProuctBeans with information.
     * (...They're all Arrested Development references.)
     */
    @Before
    public void setUp() {
        product1.setCode("a");
        product1.setDescription("The Man Inside Me: The Tobias Fünke Story");
        product1.setPrice(4.49);
        product1.setReleaseDate(LocalDate.parse("2005-09-08"));
        
        product2.setCode("A");
        product2.setDescription("Peter & The Wolf");
        product2.setPrice(3.99);
        product2.setReleaseDate(LocalDate.parse("1946-08-15"));
        
        product3.setCode("0");
        product3.setDescription("Mother Boy: 100 Years Collection");
        product3.setPrice(49.99);
        product3.setReleaseDate(LocalDate.parse("2018-01-31"));
        
        product4.setCode("11-a");
        product4.setDescription("Mrs. Doubtfire HD Remaster");
        product4.setPrice(11.99);
    }

    /**
     * Tests that {@link ProductBean#equals(java.lang.Object)} correctly implements the identity property.
     */
    @Test
    public void IdentityTest() {
        assertTrue((new ProductBean()).equals(new ProductBean()));
        assertTrue(product1.equals(product1));
        assertTrue(product2.equals(product2));
        assertTrue(product3.equals(product3));
        assertTrue(product4.equals(product4));
    }
    
    /**
     * Tests that {@link ProductBean#equals(java.lang.Object)} is correctly implemented across different objects.
     */
    @Test
    public void EqualityTest() {
        assertTrue(product1.equals(product2));
        assertTrue(product2.equals(product1));
        assertFalse(product4.equals(null));
        assertFalse(product4.equals(22.2));
        assertFalse(product4.equals(new Integer(1)));
        
        assertFalse(product1.equals(product3));
        assertFalse(product1.equals(product4));
        assertFalse(product1.equals(new ProductBean()));
        assertFalse(product1.equals(null));
        assertFalse(product1.equals(22.2));
        assertFalse(product1.equals(new Integer(1)));
        
        assertFalse(product2.equals(product3));
        assertFalse(product2.equals(product4));
        assertFalse(product2.equals(new ProductBean()));
        assertFalse(product2.equals(null));
        assertFalse(product2.equals(22.2));
        assertFalse(product2.equals(new Integer(1)));
        
        assertFalse(product3.equals(product1));
        assertFalse(product3.equals(product2));
        assertFalse(product3.equals(product4));
        assertFalse(product3.equals(new ProductBean()));
        assertFalse(product3.equals(null));
        assertFalse(product3.equals(22.2));
        assertFalse(product3.equals(new Integer(1)));
        
        assertFalse(product4.equals(product1));
        assertFalse(product4.equals(product2));
        assertFalse(product4.equals(product3));
        assertFalse(product4.equals(new ProductBean()));
        assertFalse(product4.equals(null));
        assertFalse(product4.equals(22.2));
        assertFalse(product4.equals(new Integer(1)));
    }
    
    /**
     * Tests that {@link ProductBean#hashCode()} is correctly implemented, that is maintains the same relationship as {@link ProductBean#equals(java.lang.Object)}.
     */
    @Test
    public void HashCodeTest() {
        assertEquals((new ProductBean()).hashCode(), (new ProductBean()).hashCode());
        assertEquals(product1.hashCode(), product2.hashCode());
        
        assertNotEquals(product1.hashCode(), product3.hashCode());
        assertNotEquals(product1.hashCode(), product4.hashCode());
        assertNotEquals(product1.hashCode(), (new ProductBean()).hashCode()); assertNotEquals(product1.hashCode(), product4.hashCode());
        
        assertNotEquals(product3.hashCode(), product4.hashCode());
        assertNotEquals(product3.hashCode(), (new ProductBean()).hashCode());
        
        assertNotEquals(product4.hashCode(), (new ProductBean()).hashCode());
    }
    
    /**
     * Tests that {@link ProductBean#getYearsReleased()} is correctly implemented. This will not work past a certain date (January 31st, 2018, at present).
     */
    @Test
    public void YearsReleasedTest() {
        assertEquals(product1.getYearsReleased(), 12); // Works until Sept 8, 2018
        assertEquals(product2.getYearsReleased(), 71); // Works until August 15, 2018
        assertEquals(product3.getYearsReleased(), -1); // Works until Jan 31, 2018
        assertEquals(product4.getYearsReleased(), -2);
    }
}
