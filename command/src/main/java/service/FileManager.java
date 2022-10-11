package service;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import service.MyBuilder;
import myEnum.PathName;
import myEnum.Languages;
import org.apache.commons.io.FilenameUtils;
import logic.FileLogic;
import org.apache.commons.lang3.*;

public class FileManager {
  private File[] files = new File[2];
  private String lang = "";
  private boolean compile = false;
  private boolean execution = false;
  private boolean full = false;
  private Path[] pathList = new Path[PathName.PATHLENGTH.get()];
  private String fileName;
  private String extension;
  private String baseName;
  private String directoryName;
  private Path directoryPath;
  public FileManager(File[] files, Path[] pathList) {
    this.files = files;
    this.pathList = pathList;
    this.fileName = this.pathList[0].toString();
    this.extension = FilenameUtils.getExtension(this.fileName);
    this.baseName = FilenameUtils.getBaseName(this.fileName);
    if(pathList.length == 1) {
      this.directoryName = this.pathList[PathName.DIRECTORYNAME.get()].toString();
      this.directoryPath = this.pathList[PathName.DIRECTORYNAME.get()];
     }
  }
  public void setLang(String lang) {
    this.lang = lang;
  }
  public void setCompile(boolean compile) {
    this.compile = compile;
  }
  public void setExecution(boolean execution) {
    this.execution = execution;
  }
  public void setFull(boolean full) {
    this.full = full;
  }
  
  public int run() {
    if(this.compile || this.full)
      this.compile();
    FileLogic fl = new FileLogic(); 
    if(fl.isMoveDirectory(this.pathList))
      this.fileMove();
    if(BooleanUtils.isFalse(this.compile))
      try {
        throw new Exception("言語を指定してください");
      } catch(Exception e) {
        e.printStackTrace(); 
      }
    // 実行メソッド
    this.runExtension();
    return 0;
  }
  
    private void compile() {
    Languages result = null;
    System.out.println(this.lang + " compile");
    MyBuilder mb = new MyBuilder(this.pathList, this.lang);
    mb.setFileName(this.fileName);
    mb.setExtension(this.extension);
    mb.setBaseName(this.baseName);
    for(Languages val : Languages.values())
      if(this.lang.equals(val.toString()))
        result = mb.build(val);
    if(Objects.isNull(result)) {
      try {
        throw new Exception("言語を指定してください");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  private void fileMove() {
    FileLogic fi = new FileLogic();
    String moveFileName = fi.getMoveFileName(this.lang, this.baseName);
    Path buildPath = fi.getBuildPath(this.pathList, this.lang, this.baseName);
    try {
      if(Files.isReadable(buildPath))
        Files.delete(this.directoryPath.resolve(moveFileName));
      Files.move(buildPath, this.directoryPath.resolve(moveFileName));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public void runExtension() {
    // このあと実行処理を実装するので消さないで！
    FileLogic fl = new FileLogic();
    Path buildPath = fl.getBuildPath(this.pathList, this.lang, this.baseName);
    Path changePath = buildPath.resolve(fl.getBuildExtension(Languages.getLang(this.lang)));
    System.out.println(changePath.toString());
  }
}
