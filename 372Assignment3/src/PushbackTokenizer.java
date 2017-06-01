import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * ...The last assignment was super-duper hard, took me like 8 hours, and this is doable in 20 minutes. Alright.
 */

public class PushbackTokenizer implements PushbackableTokenizer {
    // The alternative (and probably better) approach is a offset list. But whatever.
    /**
     * A stack holding the current tokenised values. nextToken() will pop from here, and store in {@link PushbackTokenizer#stackPushBackable}
     */
    private Stack<String> stack = new Stack<String>();

    /**
     * A stack holding *used* tokenised values; pushback() will pop from here into {@link PushbackTokenizer#stack}.
     */
    private Stack<String> stackPushBackable = new Stack<String>();


    /**
     * Creates a new pushbackable tokenizer by splitting `tokenizable` by `token`.
     *
     * @param tokenizable The string to be tokenised.
     * @param token The string to use for tokenisation, in any form accepted by {@link String#split(String)}
     */
    public PushbackTokenizer(String tokenizable, String token) {
        stack.addAll(Arrays.asList(tokenizable.split(token)));
        Collections.reverse(stack);
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
        return stackPushBackable.push(stack.pop());
    }

    /**
     * {@link PushbackableTokenizer#hasMoreTokens()}
     */
    public boolean hasMoreTokens() {
        return stack.size() > 0;
    }

    /**
     * {@link PushbackableTokenizer#pushback()}
     */
    public void pushback() {
        if (stackPushBackable.size() > 0) // Allow pushback() calls past the pushback stack, doing nothing if one occurs.
            stack.push(stackPushBackable.pop());
    }
}
