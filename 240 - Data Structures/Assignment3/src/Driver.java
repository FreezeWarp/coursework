public class Driver {
    public static void main(String[] args) {
        GenericSortedArrayBag<MyThing> col1 = new GenericSortedArrayBag(10);
        col1.insert(new MyThing("abc",10));
        col1.insert(new MyThing("xyz",20));
        col1.insert(new MyThing("aaa",30));
        //col1.insert(new MyThing("AAA",40));


         GenericSortedArrayBag<MyThing> col2 = (GenericSortedArrayBag<MyThing>) col1.clone();
         col1.insert(new MyThing("meh", 40));
         col1.delete(new MyThing("abc"));

         MyThing t1 = new MyThing("xyz");
         MyThing t2 = new MyThing("xyz",200);
         col1.update(t1,t2);

        for (MyThing m: col1)
            System.out.println(m);

        System.out.println();

        for (MyThing m: col2)
            System.out.println(m);
    }}