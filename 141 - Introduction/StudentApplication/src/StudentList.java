/**
 * Created by Joseph on 02/12/2016.
 */
public class StudentList {
    private Student[] students;
    private int pos;

    public StudentList(int size) {
        this.students = new Student[size];

/*        ObjectInputStream objectinputstream = null;
        try {
            FileInputStream streamIn = new FileInputStream("students");
            objectinputstream = new ObjectInputStream(streamIn);
            this.students = (Student[]) objectinputstream.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(objectinputstream != null){
                objectinputstream.close();
            }
        }*/
    }

    public void addStudent(int identifier, String name, int age, double gpa) {
        this.students[this.pos++] = new Student(identifier, name, age, gpa);

/*        ObjectOutputStream oos = null;
        FileOutputStream fout = null;

        try {
            fout = new FileOutputStream("students");
            oos = new ObjectOutputStream(fout);
            oos.writeObject(this.students);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }*/
    }

    public Student findStudent(int identifier) {
        for (int i = 0; i < pos; i++) {
            if (this.students[i].getId() == identifier) return this.students[i];
        }

        return null;
    }
}
