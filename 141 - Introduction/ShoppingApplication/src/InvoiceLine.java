/**
 * Created by Joseph on 07/10/2016.
 * Represents a single line from an invoice -- containing a single Item, and possibly one of many in an Invoice.
 * As basic as this is, I decided to omit the... JavaDoc, I believe it's called?
 */
public class InvoiceLine {
    /* Properties */
    private Item itemSold;
    private int quantity;


    /* Constructors */
    public InvoiceLine(int num, String description, double price, int quantity) {
        this.setItem(num, description, price);
        this.setQuantity(quantity);
    }


    /* Magic Methods */
    public String toString() {
        return String.format("%-10s", this.getItem().getNum()).replace("  ", " .") +
                String.format("%-30s", this.getItem().getDescription()).replace("  ", " .") +
                String.format("%-15s", (this.getItem().getPrice() + " * " + this.getQuantity())).replace("  ", " .") +
                String.format("%10s", this.getLineTotal()).replace("  ", " .") + "\n";
    }


    /* Getters and Setters, oh my */
    public void setItem(Item itemSold) {
        this.itemSold = itemSold;
    }

    public void setItem(int num, String description, double price) {
        this.itemSold = new Item(num, description, price);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return this.itemSold;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getLineTotal() {
        return this.getItem().getPrice() * this.getQuantity();
    }
}
