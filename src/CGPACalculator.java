import java.util.ArrayList;
import java.util.Scanner;

    // Class to represent a single course
    class Course {
        private String courseName;
        private String grade;
        private int creditHours;
        private double gradePoints;

        public Course(String courseName, String grade, int creditHours) {
            this.courseName = courseName;
            this.grade = grade.toUpperCase();
            this.creditHours = creditHours;
            this.gradePoints = convertGradeToPoint(this.grade);
        }

        private double convertGradeToPoint(String grade) {
            switch (grade) {
                case "A": return 4.0;
                case "B": return 3.0;
                case "C": return 2.0;
                case "D": return 1.0;
                case "F": return 0.0;
                default: return -1.0; // invalid
            }
        }

        public String getCourseName() {
            return courseName;
        }

        public String getGrade() {
            return grade;
        }

        public int getCreditHours() {
            return creditHours;
        }

        public double getGradePoints() {
            return gradePoints;
        }
    }

    // Class to represent a semester with multiple courses
    class Semester {
        private ArrayList<Course> courses;

        public Semester() {
            this.courses = new ArrayList<>();
        }

        public void addCourse(Course course) {
            courses.add(course);
        }

        public ArrayList<Course> getCourses() {
            return courses;
        }

        public double calculateGPA() {
            double totalPoints = 0;
            int totalCredits = 0;

            for (Course course : courses) {
                totalPoints += course.getGradePoints() * course.getCreditHours();
                totalCredits += course.getCreditHours();
            }

            return totalCredits > 0 ? totalPoints / totalCredits : 0;
        }
    }

    // Main CGPA Calculator program
    public class CGPACalculator {
        private ArrayList<Semester> semesters;
        private Scanner scanner;

        public CGPACalculator() {
            semesters = new ArrayList<>();
            scanner = new Scanner(System.in);
        }

        public void start() {
            System.out.println("Welcome to the CGPA Calculator!");

            while (true) {
                System.out.println("\n1. Add a course");
                System.out.println("2. View all semesters");
                System.out.println("3. Calculate CGPA");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = getValidIntInput();

                switch (choice) {
                    case 1:
                        addCourseToSemester();
                        break;
                    case 2:
                        viewSemesters();
                        break;
                    case 3:
                        calculateCGPA();
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please choose between 1-4.");
                }
            }
        }

        private void addCourseToSemester() {
            System.out.print("Enter semester number: ");
            int semesterNumber = getValidIntInput();

            while (semesterNumber <= 0) {
                System.out.println("Semester number must be a positive number.");
                semesterNumber = getValidIntInput();
            }

            while (semesters.size() < semesterNumber) {
                semesters.add(new Semester());
            }

            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();

            String grade;
            while (true) {
                System.out.print("Enter grade (A, B, C, D, F): ");
                grade = scanner.nextLine().toUpperCase();
                if (grade.matches("[ABCDF]")) break;
                System.out.println("Invalid grade. Please enter A, B, C, D, or F.");
            }

            System.out.print("Enter credit hours: ");
            int creditHours = getValidIntInput();

            while (creditHours <= 0) {
                System.out.println("Credit hours must be a positive integer.");
                creditHours = getValidIntInput();
            }

            Course course = new Course(courseName, grade, creditHours);
            semesters.get(semesterNumber - 1).addCourse(course);
            System.out.println("Course added successfully!");
        }

        private void viewSemesters() {
            if (semesters.isEmpty()) {
                System.out.println("No semesters available yet.");
                return;
            }

            for (int i = 0; i < semesters.size(); i++) {
                Semester semester = semesters.get(i);
                System.out.println("\nSemester " + (i + 1) + ":");
                ArrayList<Course> courses = semester.getCourses();

                if (courses.isEmpty()) {
                    System.out.println("No courses added yet.");
                    continue;
                }

                for (Course course : courses) {
                    System.out.printf("%s: %s (%.1f), %d credits%n",
                            course.getCourseName(), course.getGrade(), course.getGradePoints(), course.getCreditHours());
                }

                System.out.printf("Semester GPA: %.2f%n", semester.calculateGPA());
            }
        }

        private void calculateCGPA() {
            double totalPoints = 0;
            int totalCredits = 0;

            for (Semester semester : semesters) {
                for (Course course : semester.getCourses()) {
                    totalPoints += course.getGradePoints() * course.getCreditHours();
                    totalCredits += course.getCreditHours();
                }
            }

            if (totalCredits == 0) {
                System.out.println("No courses available to calculate CGPA.");
            } else {
                double cgpa = totalPoints / totalCredits;
                System.out.printf("Overall CGPA: %.2f%n", cgpa);
            }
        }

        private int getValidIntInput() {
            while (true) {
                try {
                    int number = Integer.parseInt(scanner.nextLine());
                    return number;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a number: ");
                }
            }
        }

        public static void main(String[] args) {
            CGPACalculator calculator = new CGPACalculator();
            calculator.start();
        }
    }
