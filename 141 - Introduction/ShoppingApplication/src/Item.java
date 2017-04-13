/**
 * Represents an item composed of an ID, description, and price.
 *
 * Created by Joseph on 07/10/2016.
 */
public class Item {
    /* Properties */
    private int itemNum;
    private String itemDescription;
    private double itemPrice;


    /* Constructors */
    public Item(int num, String description, double price) {
        this.setNum(num);
        this.setDescription(description);
        this.setPrice(price);
    }


    /* Magic Methods */
    public String toString() {
        return String.format("%-10s", this.getNum())     +
                String.format("%-30s", this.getDescription()) +
                String.format("%10s", this.getPrice()) + "\n";
    }


    /* Getters and Setters... that's it.
     * Brief rant: I don't dislike getters/setters on principle, but I do find they've been overused.
     * Getters and setters shine when the object stores the data differently than it is passed to it.
     * In PHP -- a much maligned, but frankly better than the average language -- you get __set() and __get(),
     * which are basically what setters should be used for.
     *
     * In more than one application, I've defined universal get($property) and set($property, $value) methods.
     * If the property needs special handling (e.g. converting JSON to array), it does that, otherwise it just passes the property normally.
     * This is especially useful if the object can resolve all its parameters from a database, but doesn't fetch them all initially.
     * Obviously, we can just manually ask the database ahead of time -- it might even be good design to do so -- but this automatic approach,
     * where the programmer doesn't necissarily know if certain properties have already been fetched from the database,
     * does result in a really, really solid maintainability at only marginal performance cost.
     */
    public int getNum() {
        return this.itemNum;
    }

    public String getDescription() {
        return this.itemDescription;
    }

    public double getPrice() {
        return this.itemPrice;
    }

    public void setNum(int num) {
        this.itemNum = num;
    }

    public void setDescription(String description) {
        this.itemDescription = description;
    }

    public void setPrice(double price) {
        this.itemPrice = price;
    }
}
