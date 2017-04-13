/**
 * Created by Joseph on 29/03/2017.
 */
public class Driver {
    public static void main(String[] args) {

    }

    public static void replace(QueueInterface q, int oldVal, int newVal) {
        QueueInterface q2 = new ArrayQueue(q.size());

        while (!q.isEmpty()) {
            int val = (int) q.remove();

            if (val == oldVal) {
                val = newVal;
            }

            q2.add(val);
        }

        while (!q2.isEmpty()) {
            q.add(q2.remove());
        }
    }

    public static QueueInterface[] split(QueueInterface inputQ) {
        QueueInterface[] q = new ArrayQueue[2];

        while (!inputQ.isEmpty()) {
            int val = (int) inputQ.remove();

            q[val % 2].add(val);
        }

        return q;
    }

    public static void selectionSort(IntNode head) {

    }
}
