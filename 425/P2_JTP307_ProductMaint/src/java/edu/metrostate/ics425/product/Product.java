
package edu.metrostate.ics425.product;

import java.time.LocalDate;

/**
 *
 * @author rfoy
 */
public interface Product {

    /**
     * Returns the code of this product
     * @return product code
     */
    String getCode();

    /**
     * Returns the description of this product
     * @return product description
     */
    String getDescription();

    /**
     * Returns the price of this product
     * @return product price
     */
    double getPrice();
    
    /**
     *  Returns the release date of this product
     * @return product release date
     */
    LocalDate getReleaseDate();
    
    /**
     * Returns the number of years since the release date of this product
     * @return number of years
     */
    int getYearsReleased();

    /**
     *
     * @param code
     */
    void setCode(String code);

    /**
     *
     * @param description
     */
    void setDescription(String description);

    /**
     *
     * @param price
     */
    void setPrice(double price);
    
    /**
     *
     * @param date
     */
    void setReleaseDate(LocalDate date);  
}    

