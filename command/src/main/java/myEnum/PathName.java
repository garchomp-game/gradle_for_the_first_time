package myEnum;

public enum PathName {
  FILENAME(0),
  DIRECTORYNAME(1),
  PATHLENGTH(2);
  
  private int id;
  
  private PathName(int id) {
    this.id = id;
  }
  
  public int get() {
    return this.id;
  }
}