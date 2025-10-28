package Group29Project.Controller;

import Group29Project.Models.Logic.HashFunction;
import Group29Project.View.AlertHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class AccessController {


  private static final String databaseURL = "jdbc:sqlite:data/dataStore.db";

  public static void start(){
    createDatabase();
  }


  public static void createDatabase() {
    String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT NOT NULL UNIQUE, " + "password TEXT NOT NULL, " + "access TEXT NOT NULL)";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
      statement.execute();
    } catch (SQLException e) {
      new AlertHandler("Database Error: Login service unavailable.");
      e.printStackTrace();
    }
  }

  public static boolean insertUser(String username, String password, String access) {
    String SQL = "INSERT INTO users(username, password, access) VALUES (?, ?, ?)";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setString(1, username);
      statement.setString(2, HashFunction.hash(password));
      statement.setString(3, access);
      statement.executeUpdate();
      return true;
    } catch (SQLException e) {
      if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
        new AlertHandler("Database Error: Username '" + username + " ' is taken. Please choose another.");
      } else {
        new AlertHandler("Database Error: Database service unavailable.");
      }
      return false;
    }
  }

  private static void updateUsername(Integer userID, String username) {
    String SQL = "UPDATE users SET username = ? WHERE userID = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setString(1, username);
      statement.setString(2, String.valueOf(userID));
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated == 0) {
        new AlertHandler("Database Error: User no longer exists");
      }
    } catch (SQLException e) {
      if (e.getMessage().contains("UNIQUE constraint failed: users.username")) {
        new AlertHandler("Database Error: Username '" + username + " ' is taken. Please choose another.");
      } else {
        new AlertHandler("Database Error: Database service unavailable.");
      }
    }
  }

  private static void updatePassword(Integer userID, String password) {
    String SQL = "UPDATE users SET password = ? WHERE userID = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setString(1, HashFunction.hash(password));
      statement.setString(2, String.valueOf(userID));
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated == 0) {
        new AlertHandler("Database Error: User no longer exists");
      }
    } catch (SQLException e) {
      new AlertHandler("Database Error: Database service unavailable.");
    }
  }

  private static void updateAccess(Integer userID, String access) {
    String SQL = "UPDATE users SET access = ? WHERE userID = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setString(1, access);
      statement.setString(2, String.valueOf(userID));
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated == 0) {
        new AlertHandler("Database Error: User no longer exists");
      }
    } catch (SQLException e) {
      new AlertHandler("Database Error: Database service unavailable.");
    }
  }

  public static void deleteUser (Integer userID) {
    String SQL = "DELETE FROM users WHERE userID = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setInt(1, userID);
      int rowsDeleted = statement.executeUpdate();
      if (rowsDeleted == 0) {
        new AlertHandler("Database Error: User no longer exists");
      }
    } catch (SQLException e) {
      new AlertHandler("Database Error: Database service unavailable.");
      e.printStackTrace();
    }
  }

  public static int authenticateUser(String username, String password){
    String SQL = "SELECT password, userID FROM users WHERE username = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next() && resultSet.getString("password").equals(HashFunction.hash(password))) {
        return Integer.parseInt(resultSet.getString("userID"));
      } else {
        new AlertHandler("Authentication Failed: Invalid username or password");
      }
    } catch (SQLException e) {
      new AlertHandler("Database Error: Login service unavailable.");
    }
    return 0;
  }

  public static String getUserAccess(int userID) {
    String SQL = "SELECT access FROM users WHERE userID = ?";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL)) {
      statement.setInt(1, userID);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("access");
      } else {
        new AlertHandler("Database Error: User no longer exists.");
      }
    } catch (SQLException e) {
      new AlertHandler("Database Error: Database service unavailable.");
    }
    return null;
  }

  public static ResultSet queryUsers() {
    String SQL = "SELECT * FROM users";
    try (Connection connection = DriverManager.getConnection(databaseURL);
        PreparedStatement statement = connection.prepareStatement(SQL);
        ResultSet resultSet = statement.executeQuery()) {
        return resultSet;
    } catch (SQLException e) {
      new AlertHandler("Database Error: Database service unavailable.");
    }
    return null;
  }
}
