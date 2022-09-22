import java.util.*;
import java.util.stream.*;

public class CommandTest {
  public static void main(String[] args) {
      try {
      Process process = Runtime.getRuntime().exec(lang + fileName);
      StringBuilder output = new StringBuilder();
      
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line);
        output.append(System.getProperty("line.separator"));
      }
      System.out.println(output.toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }  
}
