package logic;

import org.apache.commons.io.FilenameUtils;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import myEnum.*;

public class FileLogic {
  public String getBuildExtension(Languages lang) {
    switch(lang) {
      case CLANG:
        return "";
      case JAVA:
        return "class";
      case NODE:
        return "";
    }
    return "";
  }
  public String getMoveFileName(String lang, String baseName) {
    String buildExtension = this.getBuildExtension(Languages.getLang(lang));
    String moveFileName = baseName;
    if(!buildExtension.isEmpty())
      moveFileName += "." + buildExtension;
    return moveFileName;
  }
  public boolean isMoveDirectory(Path[] pathList) {
    return Files.isReadable(pathList[PathName.FILENAME.get()]) &&
        Files.isDirectory(pathList[PathName.DIRECTORYNAME.get()]);
  }
  
  public Path getBuildPath(Path[] pathList, String lang, String baseName) {
    Path filePath = pathList[PathName.FILENAME.get()];
    String moveFileName = this.getMoveFileName(lang, baseName);
    File buildFile = new File(moveFileName);
    Path buildPath = buildFile.toPath();
    return buildPath;
  }
}
