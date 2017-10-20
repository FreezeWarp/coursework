/*
 *  Copyright © 2017 Ralph A. Foy. All Rights Reserved.
 *  No part of this document may be reproduced without 
 *     written consent from the author.
 */
package edu.metrostate.ics425.jtp307.p1.model;

/**
 * Represents a square with height and width properties, as well as an area property that is calculated from the two.
 * (The area is not directly implemented as a property with its own storage alocation, since the additional code complexity from updating it alongside height and width would be undesirable.)
 * 
 * @author rfoy; modified by Joseph T. Parsons
 */
public class AreaCalculatorBean {
    /**
     * The input height used to calculate an area.
     */
    private int height; 
    
    
    /**
     * The input width used to calculate an area.
     */
    private int width;
    
    
    /**
     * @return {@link AreaCalculatorBean#height}
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Sets {@link AreaCalculatorBean#height}
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    
    /**
     * @return {@link AreaCalculatorBean#width}
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Sets {@link AreaCalculatorBean#width}
     */
    public void setWidth(int width) {
        this.width = width;
    }
    
    
    /**
     * Returns the *rectangular* area computed given {@link AreaCalculatorBean#height} and {@link AreaCalculatorBean#width}.
     */
    public int getArea() {
        return width * height;
    }
}
