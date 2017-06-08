import java.util.EmptyStackException;

/**
 * Created by joseph on 28/05/17.
 */
public class Driver {
    public static void main(String args[]) {
        PushbackableTokenizer pushbackTokenizer = new PushbackTokenizer("Hello this is a test");

        System.out.println(pushbackTokenizer.hasMoreTokens() ? "There are still more tokens." : "There are no more tokens.");

        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());

        pushbackTokenizer.pushback(); // pushes back “is”
        pushbackTokenizer.pushback(); // pushes back “this”

        System.out.println(pushbackTokenizer.hasMoreTokens() ? "There are still more tokens." : "There are no more tokens.");

        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());

        pushbackTokenizer.pushback(); // pushes back “test”
        pushbackTokenizer.pushback(); // pushes back “a”

        System.out.println(pushbackTokenizer.nextToken());
        System.out.println(pushbackTokenizer.nextToken());

        System.out.println(pushbackTokenizer.hasMoreTokens() ? "There are still more tokens." : "There are no more tokens.");

        try {
            pushbackTokenizer.nextToken();
            System.out.println("No empty stack exception?");
        } catch (EmptyStackException ex) {
            System.out.println("Empty stack exception.");
        }

        pushbackTokenizer.pushback(); // pushes back “test”
        pushbackTokenizer.pushback(); // pushes back “a”
        pushbackTokenizer.pushback(); // pushes back “is”
        pushbackTokenizer.pushback(); // pushes back “this”
        pushbackTokenizer.pushback(); // pushes back “Hello”
        pushbackTokenizer.pushback(); // pushes back nothing
        pushbackTokenizer.pushback(); // pushes back more nothing

        System.out.println(pushbackTokenizer.nextToken()); // "Hello" -- last two pushbacks ignored silently.
    }
}

/* Output:
There are still more tokens.
Hello
this
is
There are still more tokens.
this
is
a
test
a
test
There are no more tokens.
Empty stack exception.
Hello
 */