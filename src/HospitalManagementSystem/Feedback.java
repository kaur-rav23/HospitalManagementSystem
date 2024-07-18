package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Feedback {
    private Connection connection;
    private Scanner scanner;

    public Feedback(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void giveFeedback(){
        System.out.println("Enter patient id: ");
        int patientId = scanner.nextInt();
        System.out.println("Enter doctor id: ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter doctor name: ");
        String doctorName = scanner.next();
        System.out.print("Enter Feedback and Ratings (you can give stars also) ");
        String feedback= scanner.next();
        try{
            String query="INSERT INTO feedback(patient_id,doctor_id,doctor_name,rating) VALUES(?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1,patientId);
            ps.setInt(2,doctorId);
            ps.setString(3,doctorName);
            ps.setString(4,feedback);
            int affectedRows=ps.executeUpdate();
            if(affectedRows>0){
                System.out.println("Feedback added successfully");
            }
            else{
                System.out.println("Feedback not added");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewFeedback(){
        String query = "select * from feedback";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Feedbacks: ");
            System.out.println("+----+------------+-------------+-------------+-----------+");
            System.out.println("| Id | Patient_id |  Doctor_id  | Doctor_name |  Ratings  |");
            System.out.println("+----+------------+-------------+-------------+-----------+");
            while(resultSet.next()){
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");
                String doctor_name = resultSet.getString("doctor_name");
                String feedback = resultSet.getString("rating");
                System.out.printf("| %-4s | %-12s | %-13s | %-14s |%-11s|\n",patientId, doctorId, doctor_name, feedback);
                System.out.println("+----+------------+-------------+-------------+-----------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
