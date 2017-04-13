/**
 * A message-specific extension to ObjectNode.
 * (I have no idea what this type of inheritance is called. I just guessed things until the program compiled.)]
 *
 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-03-03
 */
public class MessageNode extends ObjectNode<Message> {
    public MessageNode(Message data, MessageNode link) {
        super(data, link);
    }
}