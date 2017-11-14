package edu.metrostate.ics425.jtp307.prodmaint.db;

import edu.metrostate.ics425.jtp307.prodmaint.model.Product;
import edu.metrostate.ics425.jtp307.prodmaint.model.ProductBean;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.sql.Types;

/**
 * This class handles product catalogue permanence through a standard MySQL database.
 * 
 * Note that this class does not throw its own exceptions, and should catch all SQL exceptions. Instead, methods will return true or false as appropriate, with the Logger tracking the exceptions for debugging. I believe this is the best behaviour, as it doesn't require Exception boilerplate for testing for query success. The only ambiguous case that results is whether or not selectAllProducts()/selectProduct() encountered an error or is intentionally returning zero results. There is one case where this could, in-fact, cause an issue: the software behaves as if ProductCode is unique, and will enforce this by preventing insertions when an existing product already exists. The database does NOT perform such a check (even though it totally should), and thus if the SQL query somehow fails at determining if a product with a conflicting code exists, multiple products with the same code may come about. But, again, this is something that should be enforced at the database level anyway, so I'm not too upset that we might accidentally insert duplicates here. (It also maintains full compatibility with the previous ProductCatalog implementation, which is a plus.)
 * 
 * @author Joseph T. Parsons (based on example code from Ralph Foy)
 */
public class ProductCatalog {
    
    /**
     * Unqualified query string for retrieving products from DB.
     */
    private final String SELECT_FROM_PRODUCTS = "SELECT ProductID, ProductCode, ProductDescription, ProductPrice, ProductReleaseDate FROM Product";
    
    /**
     * @return A collection of Product instances corresponding to all products in the Product table, or an empty collection if an error occurs.
     */
    public Collection<Product> selectAllProducts() {
        try (
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(this.SELECT_FROM_PRODUCTS);
        ) {
            
            return this.getProductsFromResultSet(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    /**
     * @param id The ID of the product to search for.
     * @return A Product instance with the given ID, or null if none exists.
     */
    public Product selectProduct(int id) {
        String psql = this.SELECT_FROM_PRODUCTS + " WHERE ProductID = ?";
        
        try (
             Connection conn = db.getConnection();
             PreparedStatement pStmt = (new ProductStatementBinder(conn.prepareStatement(psql)))
                     .bindId(id)
                     .getStatement();
             ResultSet rs = pStmt.executeQuery();
        ) {

            return this.getProductFromResultSet(rs); 

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    
    /**
     * @param code The code of the product to search for.
     * @return A Product instance with the given code, or null if none exists.
     */
    public Product selectProduct(String code) {
        String psql = this.SELECT_FROM_PRODUCTS + " WHERE ProductCode = ?";
        
        try (
             Connection conn = db.getConnection();
             PreparedStatement pStmt = (new ProductStatementBinder(conn.prepareStatement(psql)))
                     .bindCode(code)
                     .getStatement();
             ResultSet rs = pStmt.executeQuery();
        ) {
            
            if (rs.first()) {
                return this.getProductFromResultSet(rs);
            }
            else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return null;
        }
    }
    
    
    /**
     * Generates a collection of Product instances from a ResultSet.
     * 
     * @param rs The ResultSet from the Product table.
     * @return A collection of Product instances with data from the ResultSet.
     * @throws SQLException 
     */
    private Collection<Product> getProductsFromResultSet(ResultSet rs) throws SQLException {
        Collection<Product> products = new LinkedList<>();
        
        while (rs.next()) {
            products.add(this.getProductFromResultSet(rs));
        }
        
        return products;
    }
    
    
    /**
     * Generates a Product instance from a ResultSet.
     * 
     * @param rs The ResultSet from the Product table.
     * @return A new Product instance with data from the ResultSet.
     * @throws SQLException 
     */
    private Product getProductFromResultSet(ResultSet rs) throws SQLException {        
        Product product = new ProductBean();
        
        product.setId(rs.getInt("ProductID"));
        product.setCode(rs.getString("ProductCode"));
        product.setDescription(rs.getString("ProductDescription"));
        product.setPrice(rs.getDouble("ProductPrice"));
        
        Timestamp productReleaseDate = rs.getTimestamp("ProductReleaseDate");
        product.setReleaseDate(productReleaseDate != null
                ? productReleaseDate.toLocalDateTime().toLocalDate()
                : null);

        return product;
    }
    
    
    /**
     * @param id The product ID.
     * @return True if a product with the given ID exists; false otherwise.
     */
    public boolean exists(int id) {
        return this.selectProduct(id) != null;
    }
    
    
    /**
     * @param code The product code.
     * @return True if a product with the given code exists; false otherwise.
     */
    public boolean exists(String code) {
        return this.selectProduct(code) != null;
    }
    
    
    /**
     * Insert a Product instance into the database.
     * 
     * @param product The Product instance to insert.
     * @return True on success, false on failure.
     */
    public boolean insertProduct(Product product) {    
        final String insertSql = "INSERT INTO Product (ProductCode, ProductDescription, ProductPrice, ProductReleaseDate) VALUES (?, ?, ?, ?)";

        try (
             Connection conn = db.getConnection();
             PreparedStatement pStmt = (new ProductStatementBinder(conn.prepareStatement(insertSql)))
                     .bindCode(product.getCode())
                     .bindDescription(product.getDescription())
                     .bindPrice(product.getPrice())
                     .bindReleaseDate(product.getReleaseDate())
                     .getStatement();
        ) {

            return (pStmt.executeUpdate() == 1);

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    
    /**
     * Update an existing Product instance stored in the database, based on its ProductId, {@link Product#getId()}.
     * 
     * @param product The Product instance whose information should be updated.
     * @return True on success, false on failure.
     */
    public boolean updateProduct(Product product) {        
        final String updateSql = "UPDATE Product SET ProductDescription = ?, ProductPrice = ?, ProductReleaseDate = ? WHERE ProductId = ?";
        
        try (
             Connection conn = db.getConnection();
             PreparedStatement pStmt = (new ProductStatementBinder(conn.prepareStatement(updateSql)))
                     .bindDescription(product.getDescription())
                     .bindPrice(product.getPrice())
                     .bindReleaseDate(product.getReleaseDate())
                     .bindId(product.getId())
                     .getStatement();
        ) {

            return (pStmt.executeUpdate() == 1);

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }
    
    
    /**
     * Delete an existing Product instance from the database, based on its ProductId, {@link Product#getId()}.
     * 
     * @param product The Product instance whose information should be deleted.
     * @return True on success, false on failure.
     */
    public boolean deleteProduct(Product product) {        
        final String updateSql = "DELETE FROM Product WHERE ProductId = ?";
        
        try (
             Connection conn = db.getConnection();
             PreparedStatement pStmt = (new ProductStatementBinder(conn.prepareStatement(updateSql)))
                     .bindId(product.getId())
                     .getStatement();
        ) {

            return (pStmt.executeUpdate() == 1);

        } catch (SQLException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
            
            return false;
        }
    }

    
    /**
     * A helper class used to bind Product variables to a prepared statement into the Product table.
     * All bind* methods in this class can be chained.
     * All methods should be self-explanatory, and thus are not documented.
     */
    private class ProductStatementBinder {
        /**
         * The PreparedStatement being bound.
         */
        PreparedStatement pStmt;
        
        /**
         * The last result index that was bound. Automatically incremented with every bind statement.
         */
        int lastIndex = 0;
        
        ProductStatementBinder(PreparedStatement pStmt) {
            this.pStmt = pStmt;
        }
    
        public ProductStatementBinder bindId(int id) throws SQLException {
            pStmt.setInt(++this.lastIndex, id);
            return this;
        }

        public ProductStatementBinder bindCode(String code) throws SQLException {
            pStmt.setString(++this.lastIndex, code);
            return this;
        }

        public ProductStatementBinder bindDescription(String description) throws SQLException {
            pStmt.setString(++this.lastIndex, description);
            return this;
        }

        public ProductStatementBinder bindPrice(Double price) throws SQLException {
            pStmt.setDouble(++this.lastIndex, price);
            return this;
        }

        public ProductStatementBinder bindReleaseDate(LocalDate releaseDate) throws SQLException {
            // This is the only fully-compliant JDBC way of setting a date to null.
            if (releaseDate == null) {
                pStmt.setNull(++this.lastIndex, Types.DATE);
            }
            else {
                pStmt.setDate(++this.lastIndex, Date.valueOf(releaseDate));
            }
            
            return this;
        }
        
        public PreparedStatement getStatement() {
            return pStmt;
        }
    }
    

    /*** Singleton Stuff ***/
    
    /**
     * A database DataSource, generated when this class is initialised.
     */
    private DataSource db;

    /**
     * @return A fully-initialised version of this class.
     */
    public static ProductCatalog getInstance() {
        return ProductCatalogHolder.INSTANCE;
    }

    private ProductCatalog() {
        
    /* The other way to connect:
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("ProdMaint");
        dataSource.setUser("prodmaint");
        dataSource.setPassword("sesame");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
                
        db = dataSource;
    */

        try {
            Context ic = new InitialContext();
            db = (DataSource) ic.lookup("java:comp/env/jdbc/ProdMaint");
        } catch (NamingException ex) {
            Logger.getLogger(ProductCatalog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Singleton-ensuring goodness for ProductCatalog.
     */
    private static class ProductCatalogHolder {
        private static final ProductCatalog INSTANCE = new ProductCatalog();
    }
}
