/**
 * A message object, containing text, an ID, a room ID, a user ID, and a timestamp.
 * Note that compareTo compares ONLY the key (messageText), for use in sorting, while equals compares the ENTIRE message.
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-02-17
 */

public class Message implements Comparable<Message>, Cloneable {
    /**
     * The message text (primary key)
     */
    private String text = "";

    /**
     * The message ID. Combined with roomId, this forms a unique ID. (On its own, it may collide with messages of the same ID and different roomIDs, depending on implementation.)
     */
    private int id = 0;

    /**
     * The room ID the message was posted in. Combined with messageID, this forms a unique ID.
     */
    private int roomId = 0;

    /**
     * The user ID belonging to the user who posted the message.
     */
    private int userId = 0;

    /**
     * The unix timestamp on which the message was posted.
     * This is long for two reasons: it allows easy summation of time values (adding two 32-bit recent timestamps immediately results in an overflow), and, hey, why not avoid the 2038 bug from the get-go?
     */
    private long time = 0;


    /**
     * Constructors and variants
     *
     * @param id See Message properties.
     * @param roomId See Message properties.
     * @param userId See Message properties.
     * @param time See Message properties.
     * @param text See Message properties.
     */
    public Message(int id, int roomId, int userId, long time, String text) {
        this.setId(id);
        this.setRoomId(roomId);
        this.setUserId(userId);
        this.setTime(time);
        this.setText(text);
    }

    public Message(Message m) {
        this(m.getId(), m.getRoomId(), m.getUserId(), m.getTime(), m.getText());
    }

    public Message(String messageText) {
        this(0, 0, 0, 0, messageText);
    }

    public Message() {
        this("");
    }


    /*** METHODS ***/
    /**
     * Determines whether the Message text matches the given string.
     * At present, this is very simple, but in the future it could involve transliteration, word replacement, punctuation removal, and search word indexing. (Indeed, in the project I'm drawing inspiration from, it does involve all of those things.)
     *
     * @param string the text to search for in our Message's text
     * @return true if the text matches, false otherwise
     */
    public boolean matches(String string) {
        return this.getText().toLowerCase().contains(string.toLowerCase());
    }


    /*** GETTERS ***/
    public String getText() {
        return this.text;
    }
    public int getId() {
        return this.id;
    }
    public int getRoomId() {
        return this.roomId;
    }
    public int getUserId() {
        return this.userId;
    }
    public long getTime() {
        return this.time;
    }


    /*** SETTERS ***/
    public void setText(String text) {
        this.text = text;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setTime(long time) {
        this.time = time;
    }


    /** Tests whether another object is FULLY equal to this one -- this means all fields are tested.
     * (The assignment indicated this should only test the primary key; for this purpose, use compareTo() == 0 instead.)
     *
     * @param o The object to test for equality against.
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || this.getClass() != o.getClass())

            return false;

        Message message = (Message) o;
        return getId() == message.getId() &&
                getRoomId() == message.getRoomId() &&
                getUserId() == message.getUserId() &&
                getTime() == message.getTime() &&
                message.getText().equals(getText());
    }

    /**
     * @param m The message to compare this object against (we compare the messageText fields).
     * @return 1 if the input should be sorted after this object, -1 if it should be sorted before, and 0 if the two objects have the same key.
     */
    public int compareTo(Message m) {
        return this.getText().compareTo(m.getText());
    }

    /**
     * @param c The character to compare this message against (we compare the first character of the message text).
     * @return 1 if the input should be sorted after this object, -1 if it should be sorted before, and 0 if the two objects have the same key.
     */
    public int compareTo(char c) {
        return this.getText().toLowerCase().compareTo(Character.toString(c));
    }


    /*** toString ***/
    public String toString() {
        return (" (" + this.getId() + ", " + this.getRoomId() + "): " + this.getUserId() + ", " + this.getTime() + ", " + this.getText());
    }


    /*** clone ***/
    @Override public Message clone() throws CloneNotSupportedException {
        return (Message) super.clone();
    }
}
