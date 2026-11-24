package org.example;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static StudentDAO studentDAO = new StudentDAO();

    static GradeDAO gradeDAO = new GradeDAO();


    public static void main(String[] args) {

        while (true) {

            System.out.println("\n================================");
            System.out.println("      STUDENT GRADE TRACKER");
            System.out.println("================================");

            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Add Grade");
            System.out.println("4. View Student Grades");
            System.out.println("5. Calculate Average");
            System.out.println("6. Delete Student");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    addGrade();
                    break;

                case 4:
                    viewGrades();
                    break;

                case 5:
                    calculateAverage();
                    break;

                case 6:
                    deleteStudent();
                    break;

                case 7:

                    System.out.println(
                            "Thank you for using Student Grade Tracker!"
                    );

                    scanner.close();

                    return;

                default:

                    System.out.println(
                            "Invalid choice!"
                    );
            }
        }
    }


    // ADD STUDENT
    static void addStudent() {

        System.out.println("\n----- Add Student -----");

        System.out.print("Enter student name: ");

        String name =
                scanner.nextLine();

        System.out.print("Enter student email: ");

        String email =
                scanner.nextLine();

        Student student =
                new Student(name, email);

        studentDAO.addStudent(student);
    }


    // VIEW STUDENTS
    static void viewStudents() {

        System.out.println("\n----- Students -----");

        List<Student> students =
                studentDAO.getAllStudents();

        if (students.isEmpty()) {

            System.out.println(
                    "No students found."
            );

            return;
        }

        for (Student student : students) {

            System.out.println(student);
        }
    }


    // ADD GRADE
    static void addGrade() {

        System.out.println("\n----- Add Grade -----");

        System.out.print("Enter student ID: ");

        int studentId =
                scanner.nextInt();

        scanner.nextLine();


        Student student =
                studentDAO.findStudentById(studentId);

        if (student == null) {

            System.out.println(
                    "Student not found!"
            );

            return;
        }


        System.out.print("Enter subject: ");

        String subject =
                scanner.nextLine();


        System.out.print("Enter marks (0-100): ");

        double marks =
                scanner.nextDouble();


        if (marks < 0 || marks > 100) {

            System.out.println(
                    "Marks must be between 0 and 100."
            );

            return;
        }


        gradeDAO.addGrade(
                studentId,
                subject,
                marks
        );
    }


    // VIEW GRADES
    static void viewGrades() {

        System.out.println(
                "\n----- View Student Grades -----"
        );

        System.out.print("Enter student ID: ");

        int studentId =
                scanner.nextInt();


        Student student =
                studentDAO.findStudentById(studentId);


        if (student == null) {

            System.out.println(
                    "Student not found!"
            );

            return;
        }


        System.out.println(
                "\nStudent: " +
                        student.getName()
        );


        gradeDAO.getStudentGrades(studentId);
    }


    // CALCULATE AVERAGE
    static void calculateAverage() {

        System.out.println(
                "\n----- Calculate Average -----"
        );

        System.out.print("Enter student ID: ");

        int studentId =
                scanner.nextInt();


        Student student =
                studentDAO.findStudentById(studentId);


        if (student == null) {

            System.out.println(
                    "Student not found!"
            );

            return;
        }


        System.out.println(
                "Student: " +
                        student.getName()
        );


        gradeDAO.calculateAverage(studentId);
    }


    // DELETE STUDENT
    static void deleteStudent() {

        System.out.println(
                "\n----- Delete Student -----"
        );

        System.out.print("Enter student ID: ");

        int studentId =
                scanner.nextInt();


        studentDAO.deleteStudent(studentId);
    }
}