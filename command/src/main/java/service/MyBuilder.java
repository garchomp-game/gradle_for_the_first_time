package service;
import java.nio.file.*;
import java.util.*;
import myEnum.Languages;
import myEnum.PathName;
import java.io.*;
import org.apache.commons.io.FilenameUtils;
import myException.NotMatchLangException;
import logic.FileLogic;

public class MyBuilder {
  private Path[] pathList = new Path[PathName.PATHLENGTH.get()];
  private String lang;
  private String extension;
  private String fileName;
  private String baseName;
  FileLogic fileLogic;
  public MyBuilder(Path[] pathList, String lang) {
    this.pathList = pathList;
    this.lang = lang;
    this.fileLogic = new FileLogic();
  }
  
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
  public void setExtension(String extension) {
    this.extension = extension;
  }
  public void setBaseName(String baseName) {
    this.baseName = baseName;
  }
  
  public Languages build(Languages language) {
    switch(language) {
      case CLANG:
        return this.clang();
      case JAVA:
        return this.java();
      case NODE:
        return this.node();
    }
    return null;
  }
  
  private Languages clang() {
    if(!this.extension.equals("c")) {
      try {
        throw new NotMatchLangException("C言語");
      } catch (NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    StringBuilder command = new StringBuilder();
    command.append("gcc ");
    command.append(this.fileName);
    if(this.fileLogic.isMoveDirectory(this.pathList)) {
      command.append(" -o ");
      command.append(this.baseName);
    }
    if(build(command.toString()))
      return Languages.CLANG;
    return null;
  }
  
  private Languages java() {
    System.out.println("java");
    if(!this.extension.equals("java")) {
      try {
        throw new NotMatchLangException("java");
      } catch(NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    StringBuilder command = new StringBuilder();
    command.append("javac ");
    if(this.fileLogic.isMoveDirectory(this.pathList)) {
      try {
        command.append("-d ");
        command.append(this.pathList[PathName.DIRECTORYNAME.get()].toString());
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    command.append(this.fileName);
    if(build(command.toString()))
      return Languages.JAVA;
    return null;
  }
  
  private Languages node() {
    if(!this.extension.equals("js")) {
      try {
        throw new NotMatchLangException("js");
      } catch(NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    String command = "node " + this.fileName;
    if(build(command))
      return Languages.NODE;
    return null;
  }
  
  private boolean build(String command) {
    try {
      Process process = Runtime.getRuntime().exec(command);
      BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()));
      
      StringBuilder output = new StringBuilder();
      String line;
      String br = System.getProperty("line.separator");
      while ((line = reader.readLine()) != null) {
        output.append(line);
        output.append(br);
      }
      System.out.println(output.toString());
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
