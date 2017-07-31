import java.util.*;

/**
 * Created by joseph on 31/07/17.
 */
public class Prereqs {
    private enum Rule {
        BEFORE,
        BEFORE_OR_DURING
    };

    Map<Map.Entry<Course, Course>, Rule> prereqs = new HashMap<>();
    List<PrereqRule> prereqRuleList = new ArrayList<>();

    public void addRule(PrereqRule rule) {
        prereqRuleList.add(rule);
    }

    public void addPrereq(Course courseBefore, Course courseAfter) {
        prereqs.put(new AbstractMap.SimpleEntry<>(courseBefore, courseAfter), Rule.BEFORE);
    }

    public void addPrereqOrDuring(Course courseBefore, Course courseAfterOrDuring) {
        prereqs.put(new AbstractMap.SimpleEntry<>(courseBefore, courseAfterOrDuring), Rule.BEFORE_OR_DURING);
    }

    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        if (prereqs.get(new AbstractMap.SimpleEntry<>(courseAfter, courseBefore)) == Rule.BEFORE_OR_DURING
                || prereqs.get(new AbstractMap.SimpleEntry<>(courseAfter, courseBefore)) == Rule.BEFORE) {
            return false;
        }


        // Check Custom Rules
        boolean result = true;

        for (PrereqRule rule : prereqRuleList) {
            result = result && rule.checkForPrereq(courseBefore, courseAfter);
        }

        return result;
    }

    public boolean checkForPrereqOrDuring(Course courseBefore, Course courseAfter) {
        if (prereqs.get(new AbstractMap.SimpleEntry<>(courseAfter, courseBefore)) == Rule.BEFORE) {
            return false;
        }


        // Check Custom Rules
        boolean result = true;

        for (PrereqRule rule : prereqRuleList) {
            result = result && rule.checkForPrereqOrDuring(courseBefore, courseAfter);
        }

        return result;
    }

    public boolean checkForSemester(Course course, int semesterNum) {
        // Check Custom Rules
        boolean result = true;

        for (PrereqRule rule : prereqRuleList) {
            result = result && rule.checkForSemester(course, semesterNum);
        }

        return result;
    }

    public List<Map.Entry<Integer, Integer>> findConflicts(List<Course> courses) {
        List<Map.Entry<Integer, Integer>> conflicts = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {
            if (!checkForSemester(courses.get(i), i / 3)) {
                conflicts.add(new AbstractMap.SimpleEntry(i, (int) (Math.random() * courses.size())));
            }

            for (int j = i; j < i + i%3; j++) {
                if (!checkForPrereqOrDuring(courses.get(i), courses.get(j))) {
                    conflicts.add(new AbstractMap.SimpleEntry(i, j));
                }
            }

            for (int j = i - i%3 + 3; j < courses.size(); j++) {
                if (!checkForPrereq(courses.get(i), courses.get(j))) {
                    conflicts.add(new AbstractMap.SimpleEntry(i, j));
                }
            }
        }

        return conflicts;
    }
}