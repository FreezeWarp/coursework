import java.util.*;

/**
 * Stores and then checks course prerequisites for a single program.
 * Also allows for custom rules to be associated.
 *
 * @author Joseph T. Parsons
 */
public class Prereqs {
    /**
     * What rule a given prereq entry follows, either "BEFORE" (indicating the first course must be taken before the second course) or "BEFORE_OR_DURING" (indicating they can also be taken simultaneously).
     */
    private enum Rule {
        BEFORE,
        BEFORE_OR_DURING
    };

    /**
     * A map of course pairs and associated {@link Prereqs#Rule}s to follow.
     */
    Map<Map.Entry<Course, Course>, Rule> prereqs = new HashMap<>();

    /**
     * A list of additional rules to enforce when checking prereqs. These must follow the interface {@link PrereqRule}, and can be added using {@link Prereqs#addRule(PrereqRule)}.
     */
    List<PrereqRule> prereqRuleList = new ArrayList<>();

    /**
     * Add a new prereq rule to {@link Prereqs#prereqRuleList}.
     *
     * @param rule The rule to add.
     */
    public void addRule(PrereqRule rule) {
        prereqRuleList.add(rule);
    }

    /**
     * Add a new prereq course pair.
     *
     * @param courseBefore A course that must be taken before.
     * @param courseAfter A course that must be taken after.
     */
    public void addPrereq(Course courseBefore, Course courseAfter) {
        prereqs.put(new AbstractMap.SimpleEntry<>(courseBefore, courseAfter), Rule.BEFORE);
    }

    /**
     * Add a new prereq course pair, allowing for simultaneous courses.
     *
     * @param courseBefore A course that must be taken before (or simultaneously).
     * @param courseAfterOrDuring A course that must be taken after (or simultaneously).
     */
    public void addPrereqDuring(Course courseBefore, Course courseAfterOrDuring) {
        prereqs.put(new AbstractMap.SimpleEntry<>(courseBefore, courseAfterOrDuring), Rule.BEFORE_OR_DURING);
    }

    /**
     * Test whether a given pair of courses complies with our existing list of prereq pairs and other rules.
     *
     * @param courseBefore A course that is being taken one semester (or more) before courseAfter.
     * @param courseAfter A course that is being taken one semester (or more) after courseAfter.
     *
     * @return True if courseBefore can correctly be taken before courseAfter, false otherwise.
     */
    public boolean checkForPrereq(Course courseBefore, Course courseAfter) {
        if (prereqs.get(new AbstractMap.SimpleEntry<>(courseAfter, courseBefore)) == Rule.BEFORE_OR_DURING
            || prereqs.get(new AbstractMap.SimpleEntry<>(courseAfter, courseBefore)) == Rule.BEFORE) {
            return false;
        }

        // Check Custom Rules
        for (PrereqRule rule : prereqRuleList) {
            if (!rule.checkForPrereq(courseBefore, courseAfter)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Test whether a given pair of courses complies with our existing list of prereq pairs and other rules.
     *
     * @param firstCourse A course that is being taken zero semesters (or more) before secondCourse.
     * @param secondCourse A course that is being taken zero semesters (or more) after secondCourse.
     *
     * @return True if firstCourse can correctly be taken before (or alongside) secondCourse, false otherwise.
     */
    public boolean checkForPrereqDuring(Course firstCourse, Course secondCourse) {
        if (prereqs.get(new AbstractMap.SimpleEntry<>(secondCourse, firstCourse)) == Rule.BEFORE) { // Check if the second course must come before the first
            return false;
        }
        else if (prereqs.get(new AbstractMap.SimpleEntry<>(firstCourse, secondCourse)) == Rule.BEFORE) { // Check if the first course must come before the second
            return false;
        }

        // Check Custom Rules
        for (PrereqRule rule : prereqRuleList) {
            if (!rule.checkForPrereqDuring(firstCourse, secondCourse)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Tests whether a given course can be taken during a given numbered semester, according to our list of associated {@link PrereqRule}s.
     *
     * @param course The course being taken.
     * @param semesterNum The semester it is being taken.
     *
     * @return True if the course can be taken during semesterNum, false otherwise.
     */
    public boolean checkForSemester(Course course, int semesterNum) {
        for (PrereqRule rule : prereqRuleList) {
            if (!rule.checkForSemester(course, semesterNum)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Find all conflicts given a list of courses that are spread evenly across semesters.
     *
     * @param courses A list of courses being taken, ordered sequentially by the semester they are being taken in.
     * @param coursesPerSemester The number of courses being taken per each semester.
     *
     * @return A list of pairs of courses that are incompatible in the current ordering.
     */
    public List<Map.Entry<Integer, Integer>> findConflicts(List<Course> courses, int coursesPerSemester) {
        List<Map.Entry<Integer, Integer>> conflicts = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {
            // Check if the course conflicts with this semester. If it does, we specify the conflict by choosing one other course at random and using it as our conflict pair. This would not be necessary if we could tell our invoker that a semester is causing the conflict, not another course, but for simplicity we want to always be able to specify a *pair* of conflict courses (as this is then much easier to work with).
            if (!checkForSemester(courses.get(i), i / coursesPerSemester)) {
                conflicts.add(new AbstractMap.SimpleEntry(i, (new Random()).nextInt(courses.size() - 1)));
            }

            // Check if the course conflicts with any other in this semester.
            for (int j = i + 1; j < i - i%coursesPerSemester + coursesPerSemester && j < courses.size(); j++) {
                if (!checkForPrereqDuring(courses.get(i), courses.get(j))) {
                    conflicts.add(new AbstractMap.SimpleEntry((new Random()).nextBoolean() ? i : j, (new Random()).nextInt(courses.size() - 1))); // Randomly return either i or j as the first item, and any other entry in the course list as the second item. This randomness ensures that same-semester conflicts will all eventually be resolved.
                }
            }

            // Check if the course conflicts with any other in later semesters.
            for (int j = i - i%coursesPerSemester + coursesPerSemester; j < courses.size(); j++) {
                if (!checkForPrereq(courses.get(i), courses.get(j))) {
                    conflicts.add(new AbstractMap.SimpleEntry(i, j));
                }
            }
        }

        return conflicts;
    }
}