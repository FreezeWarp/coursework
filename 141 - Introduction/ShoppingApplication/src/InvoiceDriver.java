/**
 * Driver to test Invoice stuff
 *
 * Created by Joseph on 07/10/2016.
 */
public class InvoiceDriver {
    public static void main(String[] args) {
        Invoice Invoice1 = new Invoice("Billy Bob Thorton");
        Invoice1.addLine(52810, "2.5oz Premium-Grade Hair Gel", 7.99, 8);
        Invoice1.addLine(62843, "7oz Suntan Lotion", 10.99, 3);

        Invoice Invoice2 = new Invoice("Uma Thurman");
        Invoice2.addLine(112045, "Katana", 1999.99, 2);
        Invoice2.addLine(415545, "Yellow Jump Suit", 79.99, 1);
        Invoice2.addLine(101010, "Something, Iunno", 22.22, 22);
        Invoice2.addLine(101010, "Something, Iunno", 22.22, 22);

        System.out.println(Invoice1);
        System.out.println();
        System.out.println(Invoice2);
    }
}
