package edu.metrostate.ics425.jtp307.prodmaint.model;

import java.time.LocalDate;

/**
 * This is a minimal interface for Products that can be operated by ProductCatalog and ChangeProductServlet.
 * It is undocumented -- see {@link ProductBean} for full documentation.
 * 
 * @author Joseph T. Parsons
 */
public interface Product {
    public int getId();
    public void setId(int id);
    public String getCode();
    public void setCode(String code);
    public String getDescription();
    public void setDescription(String description);
    public Double getPrice();
    public void setPrice(Double price);
    public LocalDate getReleaseDate();
    public void setReleaseDate(LocalDate newreleaseDate);
    public int getYearsReleased();
}
