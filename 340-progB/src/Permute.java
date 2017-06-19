import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Singleton for permutation/combination stuff. I would try documenting this, but I'm a little unsure of the Java 8 stream conventions. ...I've learned Java entirely from Metro coursework, and, you know, Metro coursework until this class has been almost trivial. I'll probably try using streams myself for the next program.
 *
 * @source http://stackoverflow.com/a/28515742/3500150
 */

class Permute<E> {
    public static <E> Stream<List<E>> combinations(List<E> l, int size) {
        if (size == 0) {
            return Stream.of(Collections.emptyList());
        }

        else {
            return IntStream.range(0, l.size()).boxed().
                    <List<E>> flatMap(i -> combinations(l.subList(i+1, l.size()), size - 1).map(t -> pipe(l.get(i), t)));
        }
    }

    private static <E> List<E> pipe(E head, List<E> tail) {
        List<E> newList = new ArrayList<>(tail);
        newList.add(0, head);
        return newList;
    }
}