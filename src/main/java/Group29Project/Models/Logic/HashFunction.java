package Group29Project.Models.Logic;

import Group29Project.View.AlertHandler;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashFunction {

  public void HashFunction() {

  }

  public static String hash (String str){
    try {
      String saltstr = "NaCl" + str; //"salting" taken literally :)
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(saltstr.getBytes(StandardCharsets.UTF_8));
      return bytesToHex(hash);
    } catch (Exception e) {
      new AlertHandler("Hashing Error: Could not hash password correctly");
      return ":(";
    }
  }

  private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
      if (Integer.toHexString(0xff & b).length() == 1) {
        hexString.append('0');
      }
      hexString.append(Integer.toHexString(0xff & b));
    }
    return hexString.toString();
  }

}
