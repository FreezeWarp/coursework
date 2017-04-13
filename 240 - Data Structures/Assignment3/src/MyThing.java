public class MyThing implements Comparable<MyThing> {
    private String key;
    private int otherValue;

    public MyThing(String key) {
        this.key = key;
        this.otherValue = 0;
    }

    public MyThing(String key, int value) {
        this.key = key;
        this.otherValue = value;
    }

    @Override
    public String toString() {
        String output = this.key + "\t" + this.otherValue;
        return output;
    }

    @Override
    public boolean equals(Object obj) {
        boolean output = false;
        if (obj instanceof MyThing) {
            if (((MyThing) obj).key.equalsIgnoreCase(this.key))
                output = true;
        } else
            output = false;
        return output;
    }

    @Override
    public int compareTo(MyThing obj) {
        return this.key.compareToIgnoreCase(obj.key);
    }
}