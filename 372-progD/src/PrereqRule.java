/**
 * Created by joseph on 31/07/17.
 */
public interface PrereqRule {
    public boolean checkForPrereq(Course courseBefore, Course courseAfter);
    public boolean checkForPrereqOrDuring(Course courseBefore, Course courseAfter);
    public boolean checkForSemester(Course course, int semester);
}
