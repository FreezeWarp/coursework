import java.util.Iterator;

/**
 * A linked-list of items, supporting Iterable. Uses both generic item types (E) and generic node types (N).
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-03-03
 */
public class LinkedCollection<E extends Comparable, N extends ObjectNode> implements Iterable<E> {
    /**
     * The head node of the linked list.
     */
	protected N head;

    /**
     * The number of items in the linked list (allows size() and similar operations to work in O(1) instead of O(n)).
     * (Obviously, relying on this for operations requires that all modifications to the linked list go through LinkedCollection. Otherwise, numItems may becoming inaccurate, causing problems.)
     */
	protected int numItems;


    /**
     * Create a new linked list.
     */
	public LinkedCollection() {
        this.head = (N) new ObjectNode(null, null);
	}


    /**
     * Add a new item to the linked list.
     *
     * @param element The item to add to the list.
     */
	public void add(E element) {
	    this.head.addNodeAfter(element);

	    this.numItems++;
	}

    /**
     * Adds an element to the linked list at position `index`
     *
     * @param index - The position to add the element at, between 0 (at the beginning of list) and size() (at the end of the list).
     * @param element - The element to add.
     */
	public void add(int index, E element) {
	    if (index == 0) {
	        this.head = (N) new ObjectNode(element, this.head);
        }
        else {
            N.listPosition(this.head, index - 1).addNodeAfter(element);
        }

        this.numItems++;
    }

    /**
     * Adds an element at the end of the list.
     *
     * @param element The position to add the element a.
     */
    public void addLast(E element) {
        //N.listPosition(this.head, ObjectNode.listLength(this.head) - 1).addNodeAfter(element); this.numItems++;
        this.add(this.size(), element);
    }


    /**
     * Counts the number of times E appears in the linked list. Relies on listSearch to determine matches, which at present relies on equals(). (If target doesn't have equals(), this will crash.)

     * @param target - The element to search for.
     * @return the number of times element was found in the list.
     */
    public int countOccurrences(E target) {
	    int count = 0;
	    N match = this.head;

	    while ((match = (N) N.listSearch(match, target)) != null) {
	        count++;

	        match = (N) match.getLink();
        }

        return count;
    }


    /**
     * Returns the linked list item at position `index` (0 for the beginning of the list, size()-1 for the end of it..
     */
    public E get(int index) {
        if (index >= this.size() || index < 0) {
            throw new IllegalArgumentException("index out of range");
        }

	    return (E) ObjectNode.listPosition(this.head, index).getData();
    }


    /**
     * @return the position corresponding with the first occurrence of `target` in the list. Returns -1 if none found (per listLength spec)
     */
    public int indexOf(E target) {
	    // ...Admittedly, an odd (and somewhat inefficient) approach, but I'm trying to use ObjectNode's methods as much as possible.
	    return ObjectNode.listLength(this.head) - ObjectNode.listLength(ObjectNode.listSearch(this.head, target));
    }


    /**
     * Removes the first element matching E from the linked list.
     *
     * @param target The element to remove.
     */
    public void remove(E target) {
	    ObjectNode.listPosition(this.head, this.indexOf(target) - 1).removeNodeAfter();

        this.numItems--;
    }


    /**
     * Sets the item at `index` to `element`. Requires that existing data exist at `index`.
     */
    public void set(int index, E element) {
	    ObjectNode.listPosition(this.head, index).setData(element);
    }


    /**
     * Displays the existing collection. To obtain the string equivalent without automatically displaying, use toString().
     */
    public void display() {
        System.out.println(this.toString());
    }


    /**
     * @return The number of elements currently in the message list.
     */
	public int size() {
	    return this.numItems;
    }



    /**
     * Initiates this class's iterator class.
     *
     * @return A MessageIterator() object that can be used to iterate this class.
     */
    public Iterator<E> iterator() {
        return new LinkedCollectionIterator();
    }


    /**
     * Minimal iterator for this class.
     * Based on code from http://stackoverflow.com/a/25504773
     */
    class LinkedCollectionIterator implements Iterator<E> {
        /**
         * The current position of the iterator.
         */
        private int index = 0;

        /**
         * @return if there are more items to iterate
         */
        @Override
        public boolean hasNext() {
            return this.index < LinkedCollection.this.size();
        }

        /**
         * @return the next object in the iterator
         */
        @Override
        public E next() {
            return (E) LinkedCollection.this.get(this.index++);
        }
    }


    /**
     * @return A string representation for this object of the format (m[0].toString())\n(m[1].toString())\n...
     */
    public String toString() {
	    String string = "";
	    int index = 0;

	    for(E item : this) {
            string += (index++ + ": " + item + "\n");
        }

        return string;
    }
}
