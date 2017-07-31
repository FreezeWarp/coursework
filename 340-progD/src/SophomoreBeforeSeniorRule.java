/**
 * Created by joseph on 31/07/17.
 */
public class SophomoreBeforeSeniorRule implements PrereqRule {
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        return !(courseAfter.getCode() < 300 && (courseBefore.getCode() >= 400 && courseBefore.getCode() < 500));
    }

    public boolean checkForPrereqOrDuring(Course courseBefore, Course courseAfter) {
        return checkForPrereq(courseBefore, courseAfter);
    }

    public boolean checkForSemester(Course course, int semesterNum) {
        return true;
    }
}
