package org.example;

import java.sql.*;

public class GradeDAO {


    // ADD GRADE
    public void addGrade(
            int studentId,
            String subject,
            double marks) {

        String sql =
                "INSERT INTO grades " +
                        "(student_id, subject, marks) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setString(2, subject);
            statement.setDouble(3, marks);

            statement.executeUpdate();

            System.out.println(
                    "Grade added successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error adding grade: " + e.getMessage()
            );
        }
    }


    // VIEW STUDENT GRADES
    public void getStudentGrades(int studentId) {

        String sql =
                "SELECT subject, marks " +
                        "FROM grades " +
                        "WHERE student_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            ResultSet resultSet =
                    statement.executeQuery();

            boolean found = false;

            System.out.println("\n----- Grades -----");

            while (resultSet.next()) {

                found = true;

                String subject =
                        resultSet.getString("subject");

                double marks =
                        resultSet.getDouble("marks");

                String grade =
                        calculateGrade(marks);

                System.out.println(
                        "Subject: " + subject +
                                " | Marks: " + marks +
                                " | Grade: " + grade
                );
            }

            if (!found) {
                System.out.println(
                        "No grades found."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error getting grades: " + e.getMessage()
            );
        }
    }


    // CALCULATE AVERAGE
    public void calculateAverage(int studentId) {

        String sql =
                "SELECT AVG(marks) AS average " +
                        "FROM grades " +
                        "WHERE student_id = ?";

        try (Connection connection =
                     DBConnection.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                double average =
                        resultSet.getDouble("average");

                if (resultSet.wasNull()) {

                    System.out.println(
                            "No grades available."
                    );

                } else {

                    System.out.printf(
                            "Average Marks: %.2f%n",
                            average
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error calculating average: "
                            + e.getMessage()
            );
        }
    }


    // CONVERT MARKS TO GRADE
    private String calculateGrade(double marks) {

        if (marks >= 90) {
            return "A+";
        } else if (marks >= 80) {
            return "A";
        } else if (marks >= 70) {
            return "B";
        } else if (marks >= 60) {
            return "C";
        } else if (marks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}