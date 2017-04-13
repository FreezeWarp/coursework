/**
 * Created by Joseph on 02/12/2016.
 */
public class Student {
    private int identifier;
    private String name;
    private int age;
    private double gpa;

    public Student(int identifier, String name, int age, double gpa) {
        this.setId(identifier);
        this.setName(name);
        this.setAge(age);
        this.setGpa(gpa);
    }

    /* I've gotten lazy, and I just auto-genned these. */
    public int getId() {
        return identifier;
    }

    public void setId(int identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
