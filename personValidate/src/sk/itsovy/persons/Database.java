package sk.itsovy.persons;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private final String username="javauser";
    private final String password="java";
    private final String host = "localhost";
    private final String port = "3306";
    private final String url = "jdbc:mysql://localhost:3306/db1";

    private Connection getConnection(){
        Connection connection;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("DriverLoaded");
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void insertNewPerson(Person person){
    try {
        Connection conn = getConnection();

        assert conn != null;
        PreparedStatement stmt=conn.prepareStatement("INSERT INTO persons(FirstName,LastName,dnar,bnum) values(?,?,?,?)");
        stmt.setString(1,person.getName());
        stmt.setString(2,person.getSurname());
        stmt.setDate(3,new Date(person.getDob().getTime()));
        stmt.setString(4,person.getBnum());
        int result = stmt.executeUpdate();

        closeConnection(conn);

    } catch (SQLException e){
        e.printStackTrace();
    }


    }

    private void closeConnection(Connection conn){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Person selectBySurname(String surname){
        Person jozo = null;
    try {
        Connection conn = getConnection();

        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = conn.prepareStatement("select * from persons where LastName=? ");
        pst.setString(1, surname);
        rs = pst.executeQuery();
        while (rs.next()) {
            jozo = new Person(rs.getString("FirstName"),rs.getString("LastName"),
                    rs.getDate("dnar"),rs.getString("bnum"));
        }


    }catch (SQLException e){
        e.printStackTrace();
    }
        return jozo;
    }

    public Person selectByBNum(String bnum){
        Person jozo = null;
        try {
            Connection conn = getConnection();

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("select * from persons where bnum=? ");
            pst.setString(1, bnum);
            rs = pst.executeQuery();
            while (rs.next()) {
                jozo = new Person(rs.getString("FirstName"),rs.getString("LastName"),
                        rs.getDate("dnar"),rs.getString("bnum"));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
        return jozo;
    }

    public void selectWomenNumber(){
        //Person jozo = null;
        try {
            Connection conn = getConnection();

            PreparedStatement pst = null;
            ResultSet rs = null;
            pst = conn.prepareStatement("SELECT COUNT(*) AS pocet FROM persons WHERE bnum LIKE '__5%' OR bnum LIKE '__6%'");
            rs = pst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt("pocet"));
            }
            /*while (rs.next()) {
                jozo = new Person(rs.getString("FirstName"),rs.getString("LastName"),
                        rs.getDate("dnar"),rs.getString("bnum"));
            }*/

        }catch (SQLException e){
            e.printStackTrace();
        }
        //return jozo;
    }


    public List <Person> selectAllMen(){
        Connection conn = getConnection();
        String query = "SELECT * FROM persons WHERE bnum LIKE '__1%' OR bnum LIKE '__0%' ";
        List <Person> persons = new ArrayList<>();

    try {
        PreparedStatement pst = null;
        ResultSet rs = null;
        pst = conn.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()) {

            Person p = new Person(rs.getString("FirstName"),rs.getString("LastName"),
                    rs.getDate("dnar"),rs.getString("bnum"));
            persons.add(p);
        }

    }catch(SQLException e){
        e.printStackTrace();
    }

        return persons;
    }
}
