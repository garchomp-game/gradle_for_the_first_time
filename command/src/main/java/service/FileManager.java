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
    this.directoryName = pathList[PathName.DIRECTORYNAME.get()].toString();
    this.directoryPath = this.pathList[PathName.DIRECTORYNAME.get()];
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
    
    if(FileLogic.isMoveDirectory(this.pathList))
      this.fileMove();
    // 実行メソッド
    this.runExtension();
    return 0;
  }
  
  private void compile() {
    Languages result = null;
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
    String moveFileName = FileLogic.getMoveFileName(this.lang, this.baseName);
    Path buildPath = FileLogic.getBuildPath(this.pathList, this.lang, this.baseName);
    try {
      if(Files.isReadable(buildPath))
        Files.delete(this.directoryPath.resolve(moveFileName));
      Files.move(buildPath, this.directoryPath.resolve(moveFileName));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void runExtension() {
    // このあと実行処理を実装するので消さないで！
    FileLogic fl = new FileLogic();
    Path buildPath = fl.getBuildPath(this.pathList, this.lang, this.baseName);
    Path changePath = buildPath.resolve(fl.getBuildExtension(Languages.getLang(this.lang)));
  }
}
