/**
 * A message-specific extension to LinkedCollection. Adds totalValue and split methods.
 * (I have no idea what this type of inheritance is called. I just guessed things until the program compiled.)
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-03-03
 */
public class MessageLinkedCollection extends LinkedCollection<Message, MessageNode> {
    public MessageLinkedCollection() {
        this.head = new MessageNode(null, null);
    }

    /**
     * @return the sum of all message time fields in the linked collection.
     */
    public long totalValue() {
        long total = 0;

        for (Message item : this) {
            total += item.getTime();
        }

        /*BigInteger total = new BigInteger("0");

        for (Message item : this) {
            total = total.add(new BigInteger(Long.toString(item.getTime())));
        }*/

        return total;
    }

    /**
     * Splits this collection into two new collections based on the member's compareTo(ch) results.
     * (This could probably be implemented in LinkedCollection, but I found the inheritance a tad tricky to get right.)
     *
     * @param ch The character to use as a split point.
     * @return An array of two LinkedCollections.
     */
    public MessageLinkedCollection[] split(char ch) {
        // ... I have no idea if these three lines are correct.
        MessageLinkedCollection[] lcs = new MessageLinkedCollection[2];
        lcs[0] = new MessageLinkedCollection();
        lcs[1] = new MessageLinkedCollection();

        for (Message item: this) {
            if (item.compareTo(ch) < 0)
                lcs[0].add(item);
            else
                lcs[1].add(item);
        }

        return lcs;
    }
}
