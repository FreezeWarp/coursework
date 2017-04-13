/**
 * Assignment 5: static stack methods
 * Created by joseph on 25/03/17
 * @author Joseph T. Parsons <josephtparsons@gmail.com>
 */

public class StackDriver {
    public static void main(String[] args) {
        IntLinkedStack stack = new IntLinkedStack();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(1);

        // stack now contains 1 - 3 - 2 - 1 (from top to bottom)

        System.out.print("Stack: " + stack);

        System.out.println("Stack as Int: " + stackToInt(stack));
        System.out.println("Sum of First Two Values in Stack: " + popSome(stackCopy(stack), 2));

        System.out.print("Flipped Stack: " + flip(stack));

        System.out.println("Flipped Stack as Int: " + stackToInt(flip(stack)));
        System.out.println("Sum of First Two Values in Flipped Stack: " + popSome(stackCopy(flip(stack)), 2));

        IntStackInterface stackWithout1s = extractFromStack(stackCopy(stack), 1);

        System.out.print("Stack Without 1s: " + stackWithout1s);
        System.out.println("Sum of First Two Values in Stack Without 1s: " + popSome(stackCopy(stackWithout1s), 2));

        System.out.println("Stack == Stack: " + equalStacks(stack, stack));
        System.out.println("Stack == Flipped Stack: " + equalStacks(stack, flip(stack)));

        System.out.println("Stack without 2s == Flipped Stack without 2s: " + equalStacks(extractFromStack(stackCopy(stack), 2), extractFromStack(flip(stackCopy(stack)), 2)));
        System.out.println("Stack without 2s == Flipped Stack without 3s: " + equalStacks(extractFromStack(stackCopy(stack), 2), extractFromStack(flip(stackCopy(stack)), 3)));
        System.out.println("Stack without 3s == Flipped Stack without 3s: " + equalStacks(extractFromStack(stackCopy(stack), 3), extractFromStack(flip(stackCopy(stack)), 3)));
        System.out.println("Stack without 1s == Flipped Stack without 1s: " + equalStacks(extractFromStack(stackCopy(stack), 1), extractFromStack(flip(stackCopy(stack)), 1)));

        IntStackInterface stack2 = stackCopy(stack);
        while (!stack2.isEmpty()) {
            System.out.println("Pop Bottom: " + popBottom(stack2));
        }
        System.out.print("Stack After Pop Bottoms: " + stack2);

        System.out.print("Original Stack: " + stack);
    }


    /**
     * Returns the stack as an integer with each stack value a digit in the integer, e.g. [1, 2, 3, 4] becomes 1234.
     *
     * @param stack The stack.
     * @param base The base to use (and, in-effect, the maximum value for each member of the stack for this function to work correctly).
     * @return The stack as a single integer.
     */
    public static int stackToInt(IntStackInterface stack, int base) {
        IntStackInterface stack2 = stackCopy(stack);

        // Exponent will store the digit position when adding numbers.
        int exponent = stack.size() - 1;

        // Result will be the final result.
        int result = 0;

        while (!stack2.isEmpty()) {
            // There are, of-course, other ways of calculating this, but this seems the most self-explanatory:
            // for each iteration, add value to result by multiplying it by 10^exponent, that is moving it to the correct digit in the result.
            result += (stack2.pop() * Math.pow(base, exponent--));
        }

        return result;
    }


    public static int stackToInt(IntStackInterface stack) {
        return stackToInt(stack, 10);
    }


    /**
     * Flips a copy of `stack`, returning it.
     *
     * @param stack The stack to to flip.
     * @return A copy of the stack, flipped.
     */
    public static IntStackInterface flip(IntStackInterface stack) {
        // Make copy of stack, to avoid altering the underlying data (...it'll do that anyway in the current implementation, but...)
        IntStackInterface stack2 = stackCopy(stack);

        // Create new stack to hold the return stack
        IntLinkedStack stack3 = new IntLinkedStack();

        // Flip the contents of stack2 (copy of stack) into stack3
        while (!stack2.isEmpty()) {
            stack3.push(stack2.pop());
        }

        // Return flipped stack
        return stack3;
    }

    /**
     * Pops a value from the bottom of `stack`, returning it.
     * `stack` is modified further during execution, but will return to its original state (minus matched values) once complete.
     *
     * @param stack The stack to modify.
     * @return The value popped.
     */
    public static synchronized int popBottom(IntStackInterface stack) {
        IntLinkedStack stack2 = new IntLinkedStack();

        // If the interface doesn't correctly handle pop() on an empty stack, then problems will arise. We protect against this here.
        if (stack.isEmpty()) {
            throw new IllegalArgumentException("Stack must not be empty.");
        }

        // Flip the contents of stack into stack2.
        while (!stack.isEmpty()) {
            stack2.push(stack.pop());
        }

        // Pop off the first value from the flipped stack.
        int returnValue = stack2.pop();

        // Flip the remainder of stack2 back into stack
        while (!stack2.isEmpty()) {
            stack.push(stack2.pop());
        }

        // Return popped value from flipped stack
        return returnValue;
    }

    /**
     * Pops `count` values from `s`, returning the sum of the popped values.
     * @return sum of popped values, or -1 if not enough values to pop.
     */
    public static int popSome(IntStackInterface s, int count) {
        // Return -1 if not enough values.
        if (s.size() < count)
            return -1;

        int result = 0;

        while (count-- > 0) {
            result += s.pop();
        }

        return result;
    }


    /**
     * Removes integers equalling target from the stack. Returns the stack reference for easier chaining.
     * @param stack The stack to remove values from.
     * @param target The target to match when removing.
     * @return The stack reference.
     */
    public static synchronized IntStackInterface extractFromStack(IntStackInterface stack, int target) {
        IntLinkedStack stack2 = new IntLinkedStack();

        // Flip the contents of stack into stack2, omitting values matching target.
        while (!stack.isEmpty()) {
            int value = stack.pop();

            if (value != target) {
                stack2.push(value);
            }
        }

        // Flip the contents of stack2 (which are a flipped copy of stack, minus target) into stack and stack3, meaning that stack and stack3 now contain an original copy of stack.
        while (!stack2.isEmpty()) {
            stack.push(stack2.pop());
        }

        // Stack is returned for convenience, not out of requirement.
        return stack;
    }

    /**
     * @return true when `stack1` and `stack2` have the same values in the same order. False otherwise.
     */
    public static boolean equalStacks(IntStackInterface stack1, IntStackInterface stack2) {
        // Obviously, if they aren't equally sized, they aren't matches. Don't waste the CPU checking.
        if (stack1.size() != stack2.size())
            return false;

        IntStackInterface stack1Copy = stackCopy(stack1);
        IntStackInterface stack2Copy = stackCopy(stack2);

        // And then pop two at a time, comparing, returning false as soon as a pair are inequal. (The second isEmpty is redundant, but defensive.)
        while (!stack1Copy.isEmpty() && !stack2Copy.isEmpty()) {
            if (stack1Copy.pop() != stack2Copy.pop())
                return false;
        }

        return true;
    }


    /**
     * Makes a copy of the provided stack and returns its reference.
     * Note that, as implemented, this has fairly high space requirements: 3 times the initial stack during execution (and, of-course, 2 times once finished)
     * It will also alter the stack while executing, but return it to its original form once done.
     * (Ideally, this would just use stack's clone method, but... well, it doesn't have one.)
     *
     * @param stack The stack to copy.
     * @return A reference to the copied stack.
     */
    public static synchronized IntStackInterface stackCopy(IntStackInterface stack) {
        IntLinkedStack stack2 = new IntLinkedStack();
        IntLinkedStack stack3 = new IntLinkedStack();

        // Flip the contents of stack into stack2.
        while (!stack.isEmpty()) {
            stack2.push(stack.pop());
        }

        // Flip the contents of stack2 (which are a flipped copy of stack) into stack and stack3, meaning that stack and stack3 now contain an original copy of stack.
        while (!stack2.isEmpty()) {
            int value = stack2.pop();

            stack3.push(value);
            stack.push(value);
        }

        return stack3;
    }
}
