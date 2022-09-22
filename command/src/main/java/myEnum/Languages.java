package myEnum;
import java.util.*;

public enum Languages {
  CLANG("clang"),
  JAVA("java"),
  NODE("node");
  
  private String text;
  
  private Languages(String text) {
    this.text = text;
  }
  
  public String toString() {
    return this.text;
  }
}