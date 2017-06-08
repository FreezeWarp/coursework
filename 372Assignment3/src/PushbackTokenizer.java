import java.util.Arrays;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * An implementation of PushbackableTokenizer that uses a second stack for pushback operations.
 * When a pop occurs from the main stack, a push occurs on the other. When a pushback occurs, we pop from the pushback stack onto the main one.
 */
public class PushbackTokenizer implements PushbackableTokenizer {
    // There are two ways of doing this while allowing for O(1) pushback and pop operations.
    // The first approach is an offset list/array implemented using the methods Stack inherits from Vector. This is an okay option, though sort-of seems against the spirit of things.
    // The second approach is to use two stacks, which does potentially require twice the space -- though I believe Java's Stack and Vector implementations resize freely, so in actuality there is only going to be a little bit of extra space usage for large stacks. If space were enough of a concern, trimToSize() could be invoked after all pops.
    // My impression, from the assignment, was to use the two stack approach, since the handout indicates to use a stack for _both_ types of parameters: "You must use a Stack object with the actual parameter String to store both types of tokens."
    // However, I was told by a groupmate who asked if two stacks are okay that that's actually _not_ what we're supposed to do, so I'm leaving the Vector approach as default. The two-stack approach should be pretty clear from the comments, and was developed first. They both work perfectly fine, and, importantly, maintain O(1) operations. I can envision a way of storing pushback values at the back of stack and still using a position to indicate the "bottom" of the accessible stack to implement this using a more "pure stack" approach of only push and pop operations but this is dumb both because we have the Vector get method available, and because doing it that way would be O(n), not O(1), time.

    /**
     * The "top" of our Stack, when being treated instead as a Vector.
     * (We only use this for the first method, not the second.)
     */
    int pos = 0;

    /**
     * A stack holding the current tokenised values.
     * If we're using the two-stack method, nextToken() will pop from here, and store in {@link PushbackTokenizer#stackPushBackable}
     */
    private Stack<String> stack = new Stack<String>();

    // /**
    // * A stack holding *used* tokenised values; pushback() will pop from here into {@link PushbackTokenizer#stack}.
    // * (We only use this for the second method, not the first.)
    // */
    // private Stack<String> stackPushBackable = new Stack<String>();


    /**
     * Creates a new pushbackable tokenizer by splitting `tokenizable` by `token`.
     * (The assignment indicated to only have one constructor, but that's silly. If you're tokenising, you're going to have a specific token to tokenise, commonly a space, comma, or slash. Assuming a space -- and not allowing a custom token specification -- is really bad API design.)
     *
     * @param tokenizable The string to be tokenised.
     * @param token The string to use for tokenisation, in any form accepted by {@link String#split(String)}
     */
    public PushbackTokenizer(String tokenizable, String token) {
        stack.addAll(Arrays.asList(tokenizable.split(token)));

        // With two stack method only:
        // Collections.reverse(stack); // Perhaps unsurprisingly, stack.addAll adds them in the wrong order from what we want. So reverse them here.
    }

    /**
     * Creates a new pushbackable tokenizer by splitting `tokenizable` by a space character.
     *
     * @param tokenizable The string to be tokenised by a space.
     */
    public PushbackTokenizer(String tokenizable) {
        this(tokenizable, " ");
    }


    /**
     * {@link PushbackableTokenizer#nextToken()}
     * @throws java.util.EmptyStackException if there are no more tokens.
     */
    public String nextToken() {
        // With two stacks:
        // return stackPushBackable.push(stack.pop());

        // With Vector methods:
        if (pos >= stack.size()) {
            throw new EmptyStackException();
        }
        return stack.get(pos++);
    }

    /**
     * {@link PushbackableTokenizer#hasMoreTokens()}
     */
    public boolean hasMoreTokens() {
        // With two stacks:
        // return !stack.empty();

        // With Vector methods:
        return pos < stack.size();
    }

    /**
     * See {@link PushbackableTokenizer#pushback()}
     * This does allow for pushbacks even when there is no pushback history; it will simply do nothing in such an event.
     * (I believe this is reasonable behaviour, because no value is expected from this call. Doing the same for a pop() call from an empty stack doesn't have a logically defined behaviour -- at best, it could look at the last value in the pushback stack.)
     */
    public void pushback() {
        // With two stacks:
        //if (stackPushBackable.size() > 0) // Allow pushback() calls past the pushback stack, doing nothing if one occurs.
        //    stack.push(stackPushBackable.pop());

        // With Vector methods:
        pos--;
        if (pos < 0) pos = 0; // Allow pushback() calls past the pushback stack, doing nothing if one occurs.
    }
}
