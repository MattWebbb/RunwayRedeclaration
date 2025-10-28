package Group29Project.Controller;

import Group29Project.Models.Logic.HashFunction;
import Group29Project.View.AlertHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class RemoteAccessController {

  private static final String CREATETABLE = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=CREATE%20TABLE%20IF%20NOT%20EXISTS%20users%20(%20id%20INTEGER%20PRIMARY%20KEY%20AUTOINCREMENT,%20username%20TEXT%20NOT%20NULL%20UNIQUE,%20password%20TEXT%20NOT%20NULL,%20access%20TEXT%20NOT%20NULL)&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String INSERT = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=INSERT%20INTO%20users(username,%20password,%20access)%20VALUES%20('%3F',%20'%4F',%20'%5F')&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String UPDATE = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=UPDATE%20users%20SET%20%3F%20=%20'%4F'%20WHERE%20id%20=%20'%5F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String DELETE = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=DELETE%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String GET = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20*%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String AUTHENTICATE = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20password%20FROM%20users%20WHERE%20username%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String GETALL = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20*%20FROM%20users&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  private static final String GETID = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20id%20FROM%20users%20where%20username%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
  public static void start(){
    executeSQL(CREATETABLE);
  }

  public static boolean insertUser(String username, String password, String access) {
    String SQL = INSERT;
    SQL = SQL.replace("%3F", username).replace("%4F", HashFunction.hash(password)).replace("%5F", access);
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20username%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", username);
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count != 0) {
        new AlertHandler("Username '" + username + " ' is taken. Please choose another.");
      } else {
        if (!executeSQL(SQL).equals("fail")) {
          new AlertHandler("Account created successfully.");
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public static boolean updateUsername (Integer userID, String username){
    String SQL = UPDATE;
    SQL = SQL.replace("%3F", "username").replace("%4F", username).replace("%5F", userID.toString());
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", userID.toString());
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        String uniqueCheck2 = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20username%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
        uniqueCheck2 = uniqueCheck2.replace("%3F", username);
        String response2;
        if (!(response2 = executeSQL(uniqueCheck2)).equals("fail")) {
          JSONObject jsonObject2 = new JSONObject(response2);
          JSONArray arr2 = jsonObject2.getJSONArray("data");
          int count2 = arr2.getJSONObject(0).getInt("COUNT(*)");
          if (count2 != 0) {
            new AlertHandler("Username '" + username + " ' is taken. Please choose another.");
          } else {
            if (!executeSQL(SQL).equals("fail")) {
              new AlertHandler("Username updated successfully");
              return true;
            }
            return false;
          }
        }

      }
    }
    return false;
   }

  public static boolean updatePassword (Integer userID, String password){
    String SQL = UPDATE;
    SQL = SQL.replace("%3F", "password").replace("%4F", password).replace("%5F", userID.toString());
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", userID.toString());
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        if (!executeSQL(SQL).equals("fail")) {
          new AlertHandler("Password updated successfully");
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public static boolean updateAccess (Integer userID, String access){
    String SQL = UPDATE;
    SQL = SQL.replace("%3F", "access").replace("%4F", access).replace("%5F", userID.toString());
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", userID.toString());
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        if (!executeSQL(SQL).equals("fail")) {
          new AlertHandler("Access updated successfully");
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public static boolean deleteUser (Integer userID){
    String SQL = DELETE;
    SQL = SQL.replace("%3F", userID.toString());
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", userID.toString());
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        if (!executeSQL(SQL).equals("fail")) {
          new AlertHandler("User deleted successfully.");
          return true;
        }
        return false;
      }
    }
    return false;
  }

  public static String getUser (Integer userID){
    String SQL = GET;
    SQL = SQL.replace("%3F", userID.toString());
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20id%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", userID.toString());
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        String details;
        if (!(details = executeSQL(SQL)).equals("fail")) {
          return details;
        }
        return "fail";
      }
    }
    return "fail";
  }

  public static int getUserID(String username){
    String SQL = GETID;
    SQL = SQL.replace("%3F", username);
    String uniqueCheck = "https://ctk40qz0nz.g5.sqlite.cloud/v2/weblite/sql?sql=SELECT%20COUNT(*)%20FROM%20users%20WHERE%20username%20=%20'%3F'&database=chinook.sqlite&apikey=X5gCtsqS4nEWCPg4LSaM8PrS08ezwLzl5bj84u2153o";
    uniqueCheck = uniqueCheck.replace("%3F", username);
    String response;
    if (!(response = executeSQL(uniqueCheck)).equals("fail")) {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray arr = jsonObject.getJSONArray("data");
      int count = arr.getJSONObject(0).getInt("COUNT(*)");
      if (count == 0) {
        new AlertHandler("Database Error: User no longer exists.");
      } else {
        String details;
        if (!(details = executeSQL(SQL)).equals("fail")) {
          JSONObject jsonObject1 = new JSONObject(details);
          JSONArray jsonArray = jsonObject1.getJSONArray("data");
          int id = jsonArray.getJSONObject(0).getInt("id");
          return id;
        }
        return 0;
      }
    }
    return 0;
  }

  public static String getUserList(){
    String SQL = GETALL;
    return executeSQL(SQL);
  }

  public static boolean authenticateUser (String username, String password){
    String SQL = AUTHENTICATE;
    SQL = SQL.replace("%3F", username);
    String hashed = HashFunction.hash(password);
    String response;
    if(!(response = executeSQL(SQL)).equals("fail")){
      JSONArray arr;
      try {
        JSONObject jsonObject = new JSONObject(response);
        arr = jsonObject.getJSONArray("data");
      } catch (Exception e){
        new AlertHandler("Database Error: Database service unavailable.");
        return false;
      }
      if (!arr.isEmpty()){
        String pass = arr.getJSONObject(0).getString("password");
        if(pass.equals(hashed)){
          new AlertHandler("Authentication successful.");
          return true;
        }
        new AlertHandler("Authentication Failed: Invalid username or password");
        return false;
      }
      new AlertHandler("Authentication Failed: Invalid username or password");
      return false;
    }
    return false;
  }




  public static String executeSQL(String sql) {
    try {
      URL url = new URL(sql);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Accept", "application/json");

      StringBuilder response = new StringBuilder();
      int responseCode = connection.getResponseCode();
      //System.out.println(responseCode);
      if (responseCode != 200) {
        try {
          new AlertHandler("Database Error: Database service unavailable.");
        } catch (Exception ignored) {}
        return "fail";
      }
      BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = input.readLine()) != null) {
        //System.out.println(line);
        response.append(line);
      }
      input.close();
      connection.disconnect();
      return response.toString();
    } catch (Exception ignored) {
      }
    return "fail";
  }
}
