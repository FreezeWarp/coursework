/**
 * Created by joseph on 31/07/17.
 */
public class SemesterRestrictionsRule implements PrereqRule {
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        return true;
    }

    public boolean checkForPrereqOrDuring(Course courseBefore, Course courseAfter) {
        return true;
    }

    public boolean checkForSemester(Course course, int semesterNum) {
        return !(course.getDepartment().getName().equals("ICS") && course.getCode() == 490 && semesterNum % 3 == 0)
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 492 && semesterNum % 3 != 0)
                && !(course.getDepartment().getName().equals("ICS") && course.getCode() == 499 && semesterNum != 6);
    }
}
