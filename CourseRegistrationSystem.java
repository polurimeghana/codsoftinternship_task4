import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    String code;
    String title;
    String description;
    int capacity;
    String schedule;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }
}

class Student {
    int studentID;
    String name;
    List<String> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }
}

public class CourseRegistrationSystem {
    Map<String, Course> courses = new HashMap<>();
    Map<Integer, Student> students = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    public void addCourse(Course course) {
        courses.put(course.code, course);
    }

    public void registerStudent(Student student) {
        students.put(student.studentID, student);
    }

    public void displayCourseList() {
        System.out.println("Available Courses:");
        for (Course course : courses.values()) {
            int availableSlots = course.capacity - studentsRegisteredForCourse(course.code);
            System.out.println(course.code + " - " + course.title + " | Available Slots: " + availableSlots);
        }
    }

    public void displayRegisteredCourses(int studentID) {
        Student student = students.get(studentID);
        if (student != null) {
            System.out.println("Registered Courses for " + student.name + ":");
            for (String courseCode : student.registeredCourses) {
                Course course = courses.get(courseCode);
                if (course != null) {
                    System.out.println(course.code + " - " + course.title);
                }
            }
        }
    }

    public int studentsRegisteredForCourse(String courseCode) {
        int count = 0;
        for (Student student : students.values()) {
            if (student.registeredCourses.contains(courseCode)) {
                count++;
            }
        }
        return count;
    }

    public void registerForCourse(int studentID, String courseCode) {
        Student student = students.get(studentID);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (!student.registeredCourses.contains(courseCode) && studentsRegisteredForCourse(courseCode) < course.capacity) {
                student.registeredCourses.add(courseCode);
                System.out.println(student.name + " has successfully registered for " + course.title);
            } else {
                System.out.println("Registration failed. Either already registered or course is full.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void dropCourse(int studentID, String courseCode) {
        Student student = students.get(studentID);
        if (student != null && student.registeredCourses.contains(courseCode)) {
            student.registeredCourses.remove(courseCode);
            System.out.println(student.name + " has successfully dropped the course.");
        } else {
            System.out.println("Drop failed. Course not found or student is not registered for this course.");
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem registrationSystem = new CourseRegistrationSystem();

        Course c1 = new Course("CSE101", "Introduction to Computer Science", "Introduction to programming.", 50, "MWF 10:00 AM");
        Course c2 = new Course("MAT201", "Linear Algebra", "Algebraic equations and vectors.", 30, "TTh 2:00 PM");

        registrationSystem.addCourse(c1);
        registrationSystem.addCourse(c2);

        Student s1 = new Student(1, "Alice");
        Student s2 = new Student(2, "Bob");

        registrationSystem.registerStudent(s1);
        registrationSystem.registerStudent(s2);

        registrationSystem.registerForCourse(1, "CSE101");
        registrationSystem.registerForCourse(1, "MAT201");
        registrationSystem.registerForCourse(2, "CSE101");

        registrationSystem.displayCourseList();
        registrationSystem.displayRegisteredCourses(1);
        registrationSystem.displayRegisteredCourses(2);

        registrationSystem.dropCourse(1, "MAT201");

        registrationSystem.displayRegisteredCourses(1);
    }
}
