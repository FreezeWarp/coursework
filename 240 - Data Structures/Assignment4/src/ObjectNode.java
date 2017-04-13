/**
 * A generic linked-list node.
 * Note: all positions are zero-indexed
 * (Uses book code where applicable.)
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-03-03.
 */
public class ObjectNode<E extends Comparable> {
    private E data;

    private ObjectNode link;

    public ObjectNode() {
        this(null, null);
    }

    public ObjectNode(E data, ObjectNode link) {
        this.data = data;
        this.link = link;
    }



    /*** GETTERS and SETTERS ***/
    public Object getData() {
        return data;
    }

    public ObjectNode getLink() {
        return link;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setLink(ObjectNode link) {
        this.link = link;
    }



    /*** METHODS ***/

    /**
     * Adds `element` after this node.
     */
    public void addNodeAfter(E element) {
        if (this.getData() == null && this.getLink() == null) {
            this.setData(element);
        }

        else {
            this.setLink(new ObjectNode(element, this.getLink()));
        }
    }


    /**
     * Remove the node immediately following this node, maintaining all links thereafter.
     */
    public void removeNodeAfter() {
        if (this.getLink() == null) {
            throw new UnsupportedOperationException("Can't remove node after tail.");
        }
        else {
            this.setLink(this.getLink().getLink());
        }
    }


    /**
     * Finds the `position`th element in `head`.
     *
     * @param head The list to iterate through.
     * @param position The position to iterate to.
     *
     * @return the node at position `position` in linked list `head`.
     */
    public static ObjectNode listPosition(ObjectNode head, int position) {
        ObjectNode cursor = head;

        if (position < 0)
            throw new IllegalArgumentException("position is not positive.");

        for (int i = 0; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }


    /**
     * Finds how many nodes are linked from the `head` node.
     *
     * @param head The linked list.
     * @return The number of items in the linked list.
     */
    public static int listLength(ObjectNode head) {
        ObjectNode cursor;
        int length = 0;

        for (cursor = head; cursor != null; cursor = cursor.link)
            length++;

        return length;
    }


    /**
     * Searches for `target` in the list pointed to by `head`.
     * Uses compareTo() == 0 to test for equality.
     *
     * @param head The list to search in
     * @param target The item to search for
     * @return The node found in the search, or null if none found.
     */
    public static ObjectNode listSearch(ObjectNode head, Object target) {
        ObjectNode cursor;

        if (head == null) {
            return null;
        }

        for (cursor = head; cursor != null; cursor = cursor.link) {
            if (cursor.data == null)
                continue;

            if (cursor.data.compareTo(target) == 0)
                return cursor;
        }
        
        return null;
    }


    /**
     * Copies a source linked list to a new one.
     *
     * @param source The list to copy.
     * @return a new, copied list
     */
    public static ObjectNode listCopy(ObjectNode source) {
        ObjectNode copyHead;
        ObjectNode copyTail;

        if (source == null)
            return null;

        copyHead = new ObjectNode(source.data, null);
        copyTail = copyHead;

        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        return copyHead;
    }
}