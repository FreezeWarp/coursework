import java.util.AbstractList;
import java.util.Iterator;

/**
 * A list of message objects, supporting resizes, Iterable, and AbstractList.
 *
 * I wasn't... exactly sure which interfaces to implement and which not to, but after a bit of trial and error I found it easy (and logical) enough to implement both AbstractList and Iterable.\
 * AbstractList is very helpful, because we can integrate with several different Java classes, while Iterable allows me to foreach things, and also is used by many Java classes.
 * I also, just because it seemed logical to me, implemented functionality to automatically grow this object's message list whenever space runs out.
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-02-03
 */
public class MessageList<T> extends AbstractList<T> implements Iterable<T> {
    /**
     * A basic array of message objects.
     */
	private Object[] messageList;

    /**
     * The number of messages currently in messageList. Returned by size().
     */
	private int numMessages;

    /**
     * The current maximum number of messages the messageList can currently store.
     * This is NOT a hard limit; the class should automatically allocate more space when this number is exceeded.
     */
    private int maxMessages;

    /**
     * When the list outgrows its original size, it will increase by this number of elements.
     */
	private final int resizePeriod = 5;


    /**
     * Create a new message list, allocating a certain amount of space for messages.
     *
     * @param maxMessages The maximum number of messages the list should be capable of storing before requiring a resize. A bigger number may waste space, but require fewer resize operations, saving expensive CPU/memory operations.
     */
	public MessageList(int maxMessages) {
        this.messageList = new Object[maxMessages];
		this.maxMessages = maxMessages;
	}


    /**
     * Create a new message list, allocating the default amount of space for messages, 10.
     */
    public MessageList() {
        this(10);
    }


    /**
     * Add a new message to the message list.
     *
     * @param m The message object to add to the list.
     * @return the index the message was inserted on on success, or -1 on failure
     */
	public int insert(Message m) {
	    if (this.primaryKeyLookup(m.getId(), m.getRoomId()) != -1) {
	        return -1;
        }

	    if (this.numMessages == this.maxMessages) { // ...The java way to do this might be to catch an OOB exception, but I'm used to the C approach.
            System.out.println("resize");
	        this.resize(this.numMessages + this.resizePeriod);
        }

		this.messageList[this.numMessages++] = m;

	    return this.numMessages - 1;
	}


    /**
     * Replaces a message, identified by m.id and m.roomId, with the new data supplied.
     *
     * @param m The message object to replace with.
     * @return True on success, false on failure (typically, non-existing primary key).
     */
    public Boolean update(Message m) {
        int index = this.primaryKeyLookup(m.getId(), m.getRoomId());

        if (index == -1)
            return false;

        else {
            Message mRef = (Message) this.get(index);

            mRef.setText(m.getText());
            mRef.setTime(m.getTime());
            mRef.setUserId(m.getUserId());

            return true;
        }
    }


    /**
     * Our Message structure should be uniquely identified by the composite of id and roomId. This function returns the index of the Message containing that composite.
     *
     * @param id The first part of the (id, roomId) pair.
     * @param roomId The second part of the (id, roomId) pair.
     * @return The array index pointed to by the (id, roomId) pair, or -1 if no such pair was found in the list.
     */
	protected int primaryKeyLookup(int id, int roomId) {
        int i = 0;
        Message m;

        while ((m = (Message) this.get(i)) != null) {
            if (m.getId() == id && m.getRoomId() == roomId) {
                return i;
            }

            i++;
        }

        return -1;
    }


    /**
     * Deletes an entry identified by its primary key (the composite of id and roomId).
     *
     * @param id
     * @param roomId
     * @return the index that was deleted, or -1 if not found.
     */
	public int delete(int id, int roomId) {
	    int index = this.primaryKeyLookup(id, roomId);

        if (index > -1)
            this.unset(index);

        return index;
    }


    /**
     * Removes the Message at index `index`, and compacts the array.
     *
     * @param index
     * @return the new array size (same as numMessages or size())
     */
    protected int unset(int index) {
	    for (int i = index + 1; i < this.numMessages; i++) {
            this.messageList[i - 1] = this.messageList[i];
        }

        return this.numMessages--;
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
        Object[] newMessageList = new Object[size];
	    System.arraycopy(this.messageList, 0, newMessageList, 0, this.numMessages); // I could do it manually, of-course, but why reinvent the wheel?
        this.messageList = newMessageList;

        // Update the max messages we can store
        this.maxMessages = size;

	    return true;
    }


    /**
     * Gets the item located at index `index`.
     *
     * @param index The index to find the item at.
     * @return The item at index `index`, or null if index is out-of-range.
     */
	public T get(int index) {
	    if (index >= this.size()) {
	        return null;
        }

	    return (T) this.messageList[index];
    }


    /**
     * @return The number of elements currently in the message list.
     */
	public int size() {
	    return this.numMessages;
    }



    /**
     * Initiates this class's iterator class.
     *
     * @return A MessageIterator() object that can be used to iterate this class.
     */
    public Iterator<T> iterator() {
        return new MessageIterator();
    }


    /**
     * Minimal iterator for this class.
     * Based on code from http://stackoverflow.com/a/25504773
     */
    class MessageIterator implements Iterator<T> {
        /**
         * The current position of the iterator.
         */
        private int index = 0;

        /**
         * @return if there are more items to iterate
         */
        @Override
        public boolean hasNext() {
            return this.index < MessageList.this.size();
        }

        /**
         * @return the next object in the iterator
         */
        @Override
        public T next() {
            return (T) MessageList.this.get(this.index++);
        }
    }


    /**
     * @return A string representation for this object of the format (m[0].toString())\n(m[1].toString())\n...
     */
    public String toString() {
	    String string = "";

	    for(T t : this) {
            string += ((Message) t + "\n");
        }

        return string;
    }
			
}
