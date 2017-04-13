import java.util.AbstractList;
import java.util.Iterator;

/**
 * A sorted array of items, supporting resizes, Iterable, and AbstractList. Performs searches binarily. Does not support duplicate keys, as determined by key.compareTo(key) == 0 (as in the given test case -- aaa and AAA conflict, because they compareTo = 0).
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-02-17
 */
public class GenericSortedArrayBag<E extends Comparable> extends AbstractList<E> implements Iterable<E>, Cloneable {
    /**
     * A basic array of message objects.
     */
	private Object[] itemList;

    /**
     * The number of messages currently in itemList. Returned by size().
     */
	private int numItems;

    /**
     * The current maximum number of messages the itemList can currently store.
     * This is NOT a hard limit; the class should automatically allocate more space when this number is exceeded.
     */
    private int maxItems;

    /**
     * When the list outgrows its original size, it will increase by this number of elements.
     */
	private static final int RESIZE_PERIOD = 5;


    /**
     * Create a new message list, allocating a certain amount of space for messages.
     *
     * @param maxItems The maximum number of messages the list should be capable of storing before requiring a resize. A bigger number may waste space, but require fewer resize operations, saving expensive CPU/memory operations.
     */
	public GenericSortedArrayBag(int maxItems) {
        this.itemList = new Object[maxItems];
		this.maxItems = maxItems;
	}


    /**
     * Create a new message list, allocating the default amount of space for messages, 10.
     */
    public GenericSortedArrayBag() {
        this(10);
    }


    /**
     * Add a new item to the sorted list. Will resize and shift as needed.
     *
     * @param item The item to add to the list.
     * @return the index the message was inserted on on success, or -1 on failure
     */
	public int insert(E item) {
	    if (this.size() == this.maxItems) { // ...The java way to do this might be to catch an OOB exception, but I'm used to the C approach.
            System.out.println("resize");
	        this.resize(this.size() + this.RESIZE_PERIOD);
        }

        // Find the index to do the insertion at
        int index = this.searchReturningIndex(item, true);


	    // If the index is not at the end of the list, check to see that the existing entry does not match the one we are inserting.
	    if (this.numItems != index && this.get(index).compareTo(item) == 0) {
            return -1;
        }
	    else {
	        // If the index is at the end of the list, don't shift. Otherwise, do.
	        if (this.numItems != index) {
                this.shiftRight(index, 1);
            }

            this.itemList[index] = item;
            this.numItems++;

            return index;
        }
	}


    /**
     * Shifts the data array to the right by `degree`.
     * For instance, calling shiftRight(3, 2) when the array is [1, 2, 3, 4, 5, 6] will result in [1, 2, 3, ?, ?, 4, 5, 6]
     *
     * @param index The index of the array to start the shift at. Everything to the right of (and on) this will be shifted, everything to left will remain unaltered.
     * @param degree How much to shift by.
     */
	protected void shiftRight(int index, int degree) {
	    if (index >= this.numItems || index < 0)
	        throw new IllegalArgumentException("index must not be out of bounds.");

	    this.ensureSize(this.numItems + degree);

	    for (int i = this.numItems; i >= (index == 0 ? 1 : index); i--) {
	        this.itemList[i] = this.itemList[i - degree];
        }

        assert(this.itemList[index] == this.itemList[index + degree]); // To be clear, this will not catch every error, but it should catch most situations where the shift was not performed correctly.
    }


    /**
     * The opposite of shiftRight, this shifts the data array to the left by `degree`, condensing it.
     * For instance, calling shiftRight(3, 2) when the array is [1, 2, 3, 4, 5, 6] will result in [1, 4, 5, 6]
     *
     * @param index The index to start the shift at.
     * @param degree How much to shift by.
     */
    public void shiftLeft(int index, int degree) {
        if (index >= this.numItems || index < 0)
            throw new IllegalArgumentException("index must not be out of bounds.");

        for (int i = index + 1; i < this.numItems; i++) {
            this.itemList[i - degree] = this.itemList[i];
        }

        //assert(this.itemList[index] == this.itemList[index - degree]); // To be clear, this will not catch every error, but it should catch most situations where the shift was not performed correctly.
    }


    /**
     * Finds an item matching `oldItem` in the data store and replaces it with `newItem`.
     *
     * @param oldItem the item to search for
     * @param newItem the item to replace with
     */
    public Boolean update(E oldItem, E newItem) {
        int index = this.searchReturningIndex(oldItem, false);

        if (index > -1) {
            this.itemList[index] = newItem;

            return true;
        }

        else
            return false;
    }


    /**
     * Deletes an entry that matches the given item.
     *
     * @param item The item to delete.
     * @return the index that was deleted, or -1 if not found.
     */
	public int delete(E item) {
	    int index = this.searchReturningIndex(item, false);

        if (index > -1) {
            this.shiftLeft(index, 1);
            this.numItems--;
        }

        return index;
    }


    /**
     * Resizes the message store to a new size.
     * This can shrink or grow, but it will not allow you to resize below the currently occupied space.
     *
     * @param size The new number of items that can be stored.
     * @return true on success, false on failure
     */
	protected Boolean resize(int size) {
	    // Can't resize below our current size
	    if (size < this.size())
	        return false;

        // Create and copy the new object
        Object[] newItemList = new Object[size];
	    System.arraycopy(this.itemList, 0, newItemList, 0, this.numItems); // I could do it manually, of-course, but why reinvent the wheel?
        this.itemList = newItemList;

        // Update the max messages we can store
        this.maxItems = size;

	    return true;
    }


    /**
     * Ensures the list is capable of storing `size` elements without requiring additional resize operations.
     *
     * @param size
     */
    public void ensureSize(int size) {
	    if (size > this.maxItems)
	        resize(size);
    }


    /**
     * Gets the item located at index `index`.
     *
     * @param index The index to find the item at.
     * @return The item at index `index`, or null if index is out-of-range.
     */
	public E get(int index) {
	    if (index >= this.size() || index < 0) {
	        throw new IllegalArgumentException("index out of range");
        }

	    return (E) this.itemList[index];
    }


    /**
     * Searches for an item using searchReturningIndex (itself using compareTo) and returns the item found.
     *
     * @param item the item to search for
     * @return a found item, or null if none.
     */
    public E search(E item) {
        int index = this.searchReturningIndex(item, false);

        if (index == -1) {
            return null;
        }
        else {
            return this.get(index);
        }
    }


    /**
     * Searches for a given item using its compareTo method and a binary search.
     * (The assignment mentioned using equals, but, I mean, c'mon. This is a textbook binary search case, and that requires compareTo instead)
     * (...Serious question. Do students at MetroState not learn Binary Search in introductory programming? Normandale's freshmen CSCI1011 class, Intro to C Programming, covered it at the end.)
     *
     * @param item The item to search for.
     * @param approximate Whether to return -1 if not found, or the appropriate insertion point instead.
     */
    protected int searchReturningIndex(E item, Boolean approximate) {
        /* This code was created from memory of the binary search algorithm (and a bit of trial and error), and not anything I found on the internet, which probably means it sucks. */
        int min = 0;
        int max = (this.numItems == 0 ? 0 : this.numItems);

        while (max != min) {
            double midpoint = (double) (max + min) / 2;

            // In truth, I'm honestly not sure if this can happen. But, yeah, if the midpoint equals the end of list (where no data resides), just return it.
            if ((int) midpoint == this.numItems)
                return (int) midpoint;

            switch ((int) Math.signum(this.get((int) midpoint).compareTo(item))) {
                // The half point is *less* than the searched for item -- increase the floor.
                case -1:
                    min = (int) Math.ceil(midpoint); break;

                // The half point is *greater* than the searched for item -- lower the ceil.
                case 1:
                    max = (int) Math.floor(midpoint); break;

                // The items are equal.
                case 0:
                    return (int) midpoint;

                // Unexpected compareTo result
                default:
                    throw new UnsupportedOperationException("Unexpected compareTo result.");
            }
        }

        // If this didn't happen, something's wrong.
        assert(max == min);

        if (approximate || (min < this.size() && this.get(min).equals(item))) { // Return the midpoint we settled on only if the item exists in the list, or we toggled on approximate (indicating we don't care if it's in the list)
            return min;
        }
        else { // Return negative one if item was not found, and approximate was not turned on.
            return -1;
        }
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
        return new ItemIterator();
    }


    /**
     * Minimal iterator for this class.
     * Based on code from http://stackoverflow.com/a/25504773
     */
    class ItemIterator implements Iterator<E> {
        /**
         * The current position of the iterator.
         */
        private int index = 0;

        /**
         * @return if there are more items to iterate
         */
        @Override
        public boolean hasNext() {
            return this.index < GenericSortedArrayBag.this.size();
        }

        /**
         * @return the next object in the iterator
         */
        @Override
        public E next() {
            return (E) GenericSortedArrayBag.this.get(this.index++);
        }
    }


    /**
     * @return A string representation for this object of the format (m[0].toString())\n(m[1].toString())\n...
     */
    public String toString() {
	    String string = "";

	    for(E item : this) {
            string += (item + "\n");
        }

        return string;
    }

    /**
     * Clone, based on deep-clone example from book (pg. 313).
     */
    @Override public GenericSortedArrayBag<E> clone() {
        try {
            GenericSortedArrayBag<E> clone = (GenericSortedArrayBag<E>) super.clone();
            clone.itemList = clone.itemList.clone();

            // This is all from the book (pg. 313)
            java.lang.Class my_class;
            java.lang.reflect.Method my_clone_method;

            for (int i = 0; i < clone.size(); i++) {
                try
                {
                    my_class = clone.itemList[i].getClass( );
                    my_clone_method = my_class.getMethod("clone", new Class[0]);
                    clone.itemList[i] = my_clone_method.invoke(clone.itemList[i], new Object[0]);
                }
                catch (Exception e) {
                    // no clone available
                }
            }

            return clone;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
