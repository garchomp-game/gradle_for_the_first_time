package myEnum;

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
  
  public static Languages getLang(String lang) {
    for(Languages val : Languages.values())
      if(lang.equals(val.toString()))
        return val;
    return null;
  }
}
