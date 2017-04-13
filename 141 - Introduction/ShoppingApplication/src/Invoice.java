/**
 * Represents an invoice of multiple InvoiceLines.
 * Strictly speaking, you normally would call Line1/Line2 directly from within the function on occasion, instead of using the getter. At the same time, it doesn't make a big difference, and this style is occasionally beneficial.
 * If I were doing this for real, I would have used an array for invoiceLines, but I'm tired, and I get the impression Java makes it difficult to do arrays of objects.
 * Created by Joseph on 07/10/2016.
 */

public class Invoice {
    /* Properties */
    private String customerName;
    private int numItems;
    private InvoiceLine Line1 = null; // Should be array; I'm lazy.
    private InvoiceLine Line2 = null;


    public Invoice(String customerName) {
        this.setCustomerName(customerName);
    }


    /* Magic Methods */
    public String toString() {
        String output = "Customer: " + String.format("%55s", this.getCustomerName()) +  "\n";
        output += String.format("%-10s", "ProdID").replace(' ', '_') +
                String.format("%-30s", "Description").replace(' ', '_') +
                String.format("%-15s", "Price * Q").replace(' ', '_') +
                String.format("%10s", "Total").replace(' ', '_') + "\n";


        if (this.getLine(1) != null)
            output += this.getLine(1).toString();
        if (this.getLine(2) != null)
            output += this.getLine(2).toString();


        output += "Grant Total: " + String.format("%52s", this.getInvoiceTotal()) +  "\n";

        return output;
    }


    /* Getters/Setters
     * Setter for Line1/Line2 is addLine. (Not traditional, of-course, but that's the point of getters/setters: see rant on Item.) */
    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName (String customerName) {
        this.customerName = customerName;
    }

    /* I'm sure there's a better way to implement this, but if we're going to that trouble, might as well make it an array. */
    public void addLine(int num, String description, double price, int quantity) {
        if (this.getLine(1) == null) this.Line1 = new InvoiceLine(num, description, price, quantity);
        else if (this.getLine(2) == null) this.Line2 = new InvoiceLine(num, description, price, quantity);
        else System.err.println("Unable to add any more lines."); // I would've tried throw, but I haven't any clue how Java error handling works.
    }

    public InvoiceLine getLine(int $pos) {
        if ($pos == 1) return this.Line1;
        else if ($pos == 2) return this.Line2;
        else System.err.println("This should be an error of some sort, but I'm lazy.");

        return this.Line1;
    }


    /* Composite Methods */
    public double getInvoiceTotal() {
        int total = 0;

        if (this.getLine(1) != null) total += this.getLine(1).getLineTotal();
        if (this.getLine(2) != null) total += this.getLine(2).getLineTotal();

        return total;
    }
}
