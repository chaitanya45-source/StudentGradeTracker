package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ADD STUDENT
    public void addStudent(Student student) {

        String sql =
                "INSERT INTO students (name, email) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());

            statement.executeUpdate();

            System.out.println("Student added successfully!");

        } catch (SQLException e) {

            System.out.println(
                    "Error adding student: " + e.getMessage()
            );
        }
    }


    // VIEW ALL STUDENTS
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet =
                     statement.executeQuery(sql)) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                String name =
                        resultSet.getString("name");

                String email =
                        resultSet.getString("email");

                Student student =
                        new Student(id, name, email);

                students.add(student);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error getting students: " + e.getMessage()
            );
        }

        return students;
    }


    // FIND STUDENT BY ID
    public Student findStudentById(int id) {

        String sql =
                "SELECT * FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error finding student: " + e.getMessage()
            );
        }

        return null;
    }


    // DELETE STUDENT
    public void deleteStudent(int id) {

        String sql =
                "DELETE FROM students WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Student deleted successfully!"
                );

            } else {

                System.out.println(
                        "Student not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting student: " + e.getMessage()
            );
        }
    }
}