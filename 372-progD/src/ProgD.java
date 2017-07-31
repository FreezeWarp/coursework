import java.util.*;

/**
 * Simulation of DFS graph algorithm on graph.
 * Takes an input file as source graph and outputs to the same filename with _out appended before the file ext.
 * Additionally, uses GraphDrawer to output a visual representation of the input graph, for easier debugging.
 *
 * @author Joseph T. Parsons
 */


public class ProgD {
    private enum Rule {
        BEFORE,
        BEFORE_OR_DOING
    };

    /**
     * Passes to ProgBGraphical, which launches file opener.
     */
    public static void main(String[] args) {
        // Create a separate department object for every department.
        Department mathDepartment = new Department("Math");
        Department icsDepartment = new Department("ICS");
        Department lsDepartment = new Department("Liberal Studies");

        // Create list of courses. (A hashmap is used to more easily specify each course by its code.)
        Map<Integer, Course> courses = new HashMap<>();
        courses.put(120, new Course(mathDepartment, 120));
        courses.put(210, new Course(mathDepartment, 210));
        courses.put(215, new Course(mathDepartment, 215));
        courses.put(140, new Course(icsDepartment, 140));
        courses.put(141, new Course(icsDepartment, 141));
        courses.put(232, new Course(icsDepartment, 232));
        courses.put(240, new Course(icsDepartment, 240));
        courses.put(311, new Course(icsDepartment, 311));
        courses.put(340, new Course(icsDepartment, 340));
        courses.put(365, new Course(icsDepartment, 365));
        courses.put(372, new Course(icsDepartment, 372));
        courses.put(440, new Course(icsDepartment, 440));
        courses.put(460, new Course(icsDepartment, 460));
        courses.put(462, new Course(icsDepartment, 462));
        courses.put(490, new Course(icsDepartment, 490));
        courses.put(492, new Course(icsDepartment, 492));
        courses.put(499, new Course(icsDepartment, 499));
        courses.put(998, new Course(lsDepartment, 998));
        courses.put(999, new Course(lsDepartment, 999));
        List<Course> courseListSorted = new ArrayList<>(courses.values());

        // Create prereqs object.
        Prereqs prereqs = new Prereqs();
        prereqs.addPrereq(courses.get(120), courses.get(210));
        prereqs.addPrereq(courses.get(120), courses.get(215));
        prereqs.addPrereqOrDuring(courses.get(120), courses.get(140));
        prereqs.addPrereqOrDuring(courses.get(215), courses.get(141));
        prereqs.addPrereq(courses.get(140), courses.get(141));
        prereqs.addPrereq(courses.get(141), courses.get(232));
        prereqs.addPrereq(courses.get(141), courses.get(240));
        prereqs.addPrereq(courses.get(141), courses.get(311));
        prereqs.addPrereq(courses.get(240), courses.get(311));
        prereqs.addPrereq(courses.get(240), courses.get(340));
        prereqs.addPrereq(courses.get(240), courses.get(365));
        prereqs.addPrereq(courses.get(240), courses.get(372));
        prereqs.addPrereq(courses.get(340), courses.get(440));
        prereqs.addPrereq(courses.get(372), courses.get(499));

        prereqs.addRule(new SophomoreBeforeSeniorRule());
        prereqs.addRule(new SemesterRestrictionsRule());

        // Assign every 3 courses randomly to a semester.
        List<Course> courseList = new ArrayList<>(courses.values());
        Collections.shuffle(courseList); // Eh, same diff: this randomises our list of courses. I could have implemented this manually with Math.random, but why?

        List<Map.Entry<Integer, Integer>> conflicts;
        while ((conflicts = prereqs.findConflicts(courseList)).size() != 0) {
            coursePrint(courseListSorted, courseList);

            int randomIndex = (int) Math.random() % conflicts.size();
            Collections.swap(courseList, conflicts.get(randomIndex).getKey(), conflicts.get(randomIndex).getValue());
        }

        System.out.println("Assignment found.");
        coursePrint(courseListSorted, courseList);

    }


    public static void coursePrint(List<Course> coursesListSorted, List<Course> courseList) {
        String print = "";

        for (int i = 0; i < courseList.size(); i++) {
            print += courseList.get(i) + "\t\t";
        }

        System.out.println(print);
    }
}