import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * So, um, I know the assignment wanted this to be non-abstract. But I'm stubborn, and I tried writing it to be abstract anyway, figuring if I couldn't I would switch things over if needed.
 * And amazingly things worked out! ObjectNode and LinkedCollection are two abstract methods for nodes and linked lists, while MessageNode and MessageLinkedCollection are extensions that... do rather little, but add message-specific functionality if needed.
 * As a result we enjoy the strong typing of Message-specific classes while maintaining the flexibility of the generics. As someone who mostly works in weakly-typed languages, this is quite a cool thing indeed to me!

 * @author Joseph T Parsons <josephtparsons@gmail.com>
 * @version 2017-03-03
 */
public class Driver {
    public static void main(String[] args) {
        MessageLinkedCollection messages = new MessageLinkedCollection();

        Message m1 = new Message(1, 2, 3, Instant.now().getEpochSecond(), "Hello, Bob");
        Message m2 = new Message(2, 2, 7, Instant.now().plus(1, ChronoUnit.SECONDS).getEpochSecond(), "Hi, Nancy");
        Message m3 = new Message(1, 1, 3, Instant.now().minus(2, ChronoUnit.SECONDS).getEpochSecond(), "What's Up, Phil?");
        Message m4 = new Message(7, 20, 11, Instant.now().plus(1, ChronoUnit.HOURS).getEpochSecond(), "Are The Reports Done, Yet?");

        messages.add(m1);
        messages.add(m2);
        messages.add(0, m3);
        messages.addLast(m4);
        messages.addLast(m1);
        messages.add(3, m1);

        messages.display();

        System.out.println("Number of messages in list: " + messages.size());
        System.out.println("Average time of messages in list: " + messages.totalValue() / messages.size());
        System.out.println("Total Occurences of Message 1: " + messages.countOccurrences(m1));
        System.out.println("Total Occurences of Message 2: " + messages.countOccurrences(m2));
        System.out.println("First Position of Message 1: " + messages.indexOf(m1));
        System.out.println();

        messages.set(0, m4);
        messages.remove(m1);
        messages.set(2, m2);

        messages.display();
        System.out.println("Number of messages in list: " + messages.size());
        System.out.println("Average time of messages in list: " + messages.totalValue() / messages.size());
        System.out.println("Total Occurences of Message 1: " + messages.countOccurrences(m1));
        System.out.println("Total Occurences of Message 2: " + messages.countOccurrences(m2));
        System.out.println("First Position of Message 1: " + messages.indexOf(m1));
        System.out.println();

        MessageLinkedCollection messageSplits[] = messages.split('f');
        System.out.println("Split, before `f`:");
        System.out.println(messageSplits[0]);
        System.out.println("Split, after `f`:");
        System.out.println(messageSplits[1]);

        System.out.println("2nd item in the after `f` split:");
        System.out.println(messageSplits[1].get(1));
    }
}