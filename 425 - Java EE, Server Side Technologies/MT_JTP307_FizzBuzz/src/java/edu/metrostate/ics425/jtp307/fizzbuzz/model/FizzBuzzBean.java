package edu.metrostate.ics425.jtp307.fizzbuzz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a FizzBuzz object, which calculates FizzBuzz results such that, "Fizz" is printed when i is divisible by 3, and "Buzz" is printed when i is divisible by 5.
 * 
 * @author Joseph T. Parsons
 */
public class FizzBuzzBean implements Serializable {
    /**
     * The number to count to.
     */
    private int n;
    
    
    /**
     * The result strings.
     */
    private String[] results;
    
    
    /**
     * @return {@link FizzBuzzBean#n}
     */
    public int getN() {
        return n;
    }
    
    /**
     * Sets {@link FizzBuzzBean#n}
     */
    public void setN(int n) {
        this.n = n;
    }
    
    /**
     * @return a copy of {@link FizzBuzzBean#results}
     */
    public String[] getResults() {
        List<String> results = new ArrayList<String>();
        
        for (int i = 1; i <= n; i++) {
            String ithResult = "";
            
            if (i % 3 == 0) {
                ithResult += "Fizz";
            }
            if (i % 5 == 0) {
                ithResult += "Buzz";
            }
            if (ithResult.length() == 0) {
                ithResult = Integer.toString(i);
            }
            
            results.add(ithResult);
        }
        
        return results.toArray(new String[0]);
    }

    
    /**
     * @return A hashcode based solely on the {@link FizzBuzBean#n} value of the FizzBuzzBean.
     */
    public int hashCode() {
        return Objects.hashCode(this.n);
    }
    
    
    /**
     * Compares the equality of this with obj based on {@link FizzBuzzBean#n} values.
     * 
     * @param obj An object to test equality with this object.
     * @return true when obj equals this, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) { // Covers identity property.
            return true;
        }
        
        if (!(obj instanceof FizzBuzzBean)) { // Covers null, non-compatible object types.
            return false;
        }
        
        FizzBuzzBean castedObj = ((FizzBuzzBean)obj);
        return Objects.equals(
                this.getN(),
                castedObj.getN()
        );
    }
    
    
    /**
     * @return A string representation of the FizzBuzzBean, solely identifying its {@link FizzBuzzBean#n} property.
     */
    public String toString() {
        return "[FizzBuzzBean, n = " + getN() + "]"; 
    }
}
