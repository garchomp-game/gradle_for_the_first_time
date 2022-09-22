package myException;

public class NotMatchLangException extends Exception {
  private static final long serialVersionUID = 1L;
  /**
   * ファイルの拡張子、及び言語に関する例外
   * @param String 言語の情報のみ記載
   */
  public NotMatchLangException(String msg) {
    System.out.println(msg + "のファイルでないか、存在しないファイルです");
  }
}