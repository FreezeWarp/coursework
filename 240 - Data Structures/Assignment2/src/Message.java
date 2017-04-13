import java.util.Objects;

/**
 * A message object, representing a message ID, a room ID, a user ID, a timestamp integer, and a text field. The message ID and room ID should generally be treated as a unique ID, but that's up to the collection.
 * Note that I've implemented this very similarly to an instant messenger I've been working on for a while (https://github.com/FreezeWarp/freeze-messenger/), which means I'm simulating more-or-less real data whose specifications have evolved to best fit a wide variety of scenarios.
 * Code generation has been used where appropriate. It gets tiring writing boilerplate, after all.
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-02-03
 */

public class Message {
    /**
     * The message ID. Combined with roomId, this forms a unique ID. (On its own, it may collide with messages of the same ID and different roomIDs, depending on implementation.)
     */
    private int id;

    /**
     * The room ID the message was posted in. Combined with messageID, this forms a unique ID.
     */
    private int roomId;

    /**
     * The user ID belonging to the user who posted the message.
     */
    private int userId;

    /**
     * The unix timestamp on which the message was posted.
     */
    private int time;


    /**
     * The message text.
     */
    private String text;


    /**
     * Constructor
     *
     * @param id See Message properties.
     * @param roomId See Message properties.
     * @param userId See Message properties.
     * @param time See Message properties.
     * @param text See Message properties.
     */
    public Message(int id, int roomId, int userId, int time, String text) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.time = time;
        this.text = text;
    }
    public Message() {
        this(0,0,0,0,"");
    }


    /*** METHODS ***/
    /**
     * Determines whether the Message text matches the given string.
     * At present, this is very simple, but in the future it could involve transliteration, word replacement, punctuation removal, and search word indexing. (Indeed, in the project I'm drawing inspiration from, it does involve all of those things.)
     *
     * @param string the text to search for in our Message's text
     * @return true if the text matches, false otherwise
     */
    public Boolean matches(String string) {
        return this.getText().contains(string);
    }


    /*** GETTERS ***/
    public int getId() {
        return this.id;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public int getUserId() {
        return this.userId;
    }

    public int getTime() {
        return this.time;
    }

    public String getText() {
        return this.text;
    }


    /*** SETTERS ***/
    public void setId(int id) {
        this.id = id;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setText(String text) {
        this.text = text;
    }


    /*** EQUALS ***/
    /* Based on Java 7 standard. */
    public boolean equals(Message m) {
        if (this == m) // Same reference
            return true;

        if (m == null) // Null
            return false;

        return this.getId() == m.getId() &&
                this.getRoomId() == m.getRoomId() &&
                this.getUserId() == m.getUserId() &&
                this.getTime() == m.getTime() &&
                Objects.equals(this.getText(), m.getText());
    }


    /*** toString ***/
    public String toString() {
        return (" (" + this.getId() + ", " + this.getRoomId() + "): " + this.getUserId() + ", " + this.getTime() + ", " + this.getText());
    }
}
