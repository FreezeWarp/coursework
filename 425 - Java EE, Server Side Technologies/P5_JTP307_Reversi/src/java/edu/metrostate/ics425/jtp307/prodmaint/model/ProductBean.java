package edu.metrostate.ics425.jtp307.prodmaint.model;

import java.io.Serializable;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;
import java.util.Objects;

/**
 * Represents a Product based on the Product interface; that is, having a code, description, price, and release date, and being able to calculate the years released. It also adds a unqiue ID for primary key purposes.
 * This class implements equals and hashCode() based solely on its code property. Thus, it is equal to other Product instances with the same code, case-insensitive. 
 * 
 * @author Joseph T. Parsons
 */
public class ProductBean implements Product, Serializable {
    /**
     * A unique product id.
     * (I kinda get why you might want to have this as the primary key, since its easier to
     * operate on an integer than a string. Still, I can't think of a reason to do so
     * other than being a bad developer. ...Educate me?)
     */
    private int id;
    
    
    /**
     * A unique product code.
     */
    private String code; 
    
    
    /**
     * The product's description.
     */
    private String description;
    
    
    /**
     * The product's price.
     */
    private double price;
    
    
    /**
     * The product's release date.
     */
    private LocalDate releaseDate;
    
    
    /**
     * @return {@link ProductBean#productId}
     */
    public int getId() {
        return id;
    }
    
    /**
     * Sets {@link ProductBean#productId}
     */
    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * @return {@link ProductBean#code}
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Sets {@link ProductBean#code}
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    
    /**
     * @return {@link ProductBean#description}
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Sets {@link ProductBean#description}
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * @return {@link ProductBean#price}
     */
    public Double getPrice() {
        return price;
    }
    
    /**
     * Sets {@link ProductBean#price}
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    
    
    /**
     * @return {@link ProductBean#description}
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    
    /**
     * Sets {@link ProductBean#description}
     */
    public void setReleaseDate(LocalDate newreleaseDate) {
        this.releaseDate = newreleaseDate;
    }

    
    /**
     * @return A hashcode based solely on the values of the product used to test equality, i.e. code. Code is case insensitive; "a" and "A" produce identical hashcodes.
     */
    public int hashCode() {
        return Objects.hashCode(this.code == null ? this.code : this.code.toLowerCase());
    }
    
    
    /**
     * Compares the equality of this with obj based on code values, with the following rules (in order):
     * - If obj is the same reference as this, it is equal to this.
     * - If obj is not a Product instance, it is not equal to this.
     * - If obj has the same code (case insensitive) as this, it is equal to this. (null codes allowed.)
     * 
     * Note that this does compare against other implementors of Project, which should be desirable behaviour. (That is, if a differnt Product implementor has the same code value, it will be deemed equal. The Product interface does not reject this behaviour, and it is the most naturally logical.)
     * 
     * @param obj An object to test equality with this object.
     * @return true when obj equals this, false otherwise.
     */
    public boolean equals(Object obj) {
        if (this == obj) { // Covers identity property.
            return true;
        }
        
        if (!(obj instanceof Product)) { // Covers null, non-compatible object types.
            return false;
        }
        
        Product castedObj = ((Product)obj);
        return Objects.equals(
                this.getCode() == null
                        ? this.getCode()
                        : this.getCode().toLowerCase(),
                castedObj.getCode() == null
                        ? castedObj.getCode()
                        : castedObj.getCode().toLowerCase()
        );
    }
    
    
    /**
     * @return A string representation of the product, with its name, description, price, release date, and years since release included on one line.
     */
    public String toString() {
        return "Product '" + getCode() + "': '" + getDescription() + "', Price " + getPrice() + ", Released " + getReleaseDate() + " (" + getYearsReleased() + " years since release)\n"; 
    }
    
    
    /**
     * @return The number of years that have passed since {@link ProductBean#releaseDate}. If a release date is not known, returns -2. If the release date is any time in the future, returns -1.
     */
    public int getYearsReleased() {
        LocalDate now = LocalDate.now();
        
        if (releaseDate == null) {
            return -2;
        }
        else if (releaseDate.isAfter(now)) {
            return -1;
        }
        else {
            // There are two logical ways of doing this: finding the number of days and dividing by 365.25 (which will still... eventually sucuumb to rounding errors/drift), or just get the two years of the date object, and hope that YEARS.between() works correctly. The latter seems more reasonable.
            return (int) YEARS.between(releaseDate, now);
        }
    }
}
