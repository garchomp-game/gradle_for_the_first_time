package controller;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import service.MyBuilder;
import myEnum.PathName;
import myEnum.Languages;
import org.apache.commons.io.FilenameUtils;
import logic.FileManager;

public class FileController {
  private File[] files = new File[2];
  private String option = "";
  private String lang = "";
  private boolean compile = false;
  private boolean execution = false;
  private boolean full = false;
  private Path[] pathList = new Path[PathName.PATHLENGTH.get()];
  private String fileName;
  private String extension;
  private String baseName;
  private String directoryName;
  public FileController(File[] files, String option, Path[] pathList) {
    this.files = files;
    this.option = option;
    this.pathList = pathList;
    this.fileName = this.pathList[0].toString();
    this.extension = FilenameUtils.getExtension(this.fileName);
    this.baseName = FilenameUtils.getBaseName(this.fileName);
    this.directoryName = pathList[PathName.DIRECTORYNAME.get()].toString();
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
    Languages result = null;
    MyBuilder mb = new MyBuilder(this.pathList, this.lang);
    mb.setFileName(this.fileName);
    mb.setExtension(this.extension);
    mb.setBaseName(this.baseName);
    for(Languages val : Languages.values())
      if(this.lang.equals(val.toString()))
        result = mb.run(val);
    
    if(Objects.isNull(result)) {
      try {
        throw new Exception("言語を指定してください");
      } catch (Exception e) {
        e.printStackTrace();
        return 0;
      }
    }
    if(isMoveDirectory(this.pathList)) {
      // 後で関数に処理を分ける
      Path filePath = this.pathList[PathName.FILENAME.get()];
      Path directoryPath = this.pathList[PathName.DIRECTORYNAME.get()];
      try {
        String buildExtension = FileManager.getBuildExtension(result);
        String moveFileName = this.baseName;
        if(!buildExtension.isEmpty())
          moveFileName += "." + buildExtension;
        File buildFile = new File(moveFileName);
        Path buildPath = buildFile.toPath();
        Files.move(buildPath, directoryPath.resolve(moveFileName));
        mb.runExtension(buildPath);// ← このあと実行処理を実装する
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    return 0;
  }
  
  public static boolean isMoveDirectory(Path[] pathList) {
    return Files.isReadable(pathList[PathName.FILENAME.get()]) &&
        Files.isDirectory(pathList[PathName.DIRECTORYNAME.get()]);
  }
}