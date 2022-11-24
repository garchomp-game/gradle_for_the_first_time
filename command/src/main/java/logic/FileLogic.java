package logic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import myEnum.Languages;
import myEnum.PathName;

public class FileLogic {
  public String getBuildExtension(final Languages lang) {
    switch (lang) {
      case CLANG:
        return "";
      case JAVA:
        return "class";
      case NODE:
        return "";
    }
    return "";
  }

  public String getMoveFileName(final String lang, final String baseName) {
    final String buildExtension = this.getBuildExtension(Languages.getLang(lang));
    String moveFileName = baseName;
    if (!buildExtension.isEmpty())
      moveFileName += "." + buildExtension;
    return moveFileName;
  }

  public boolean isMoveDirectory(final Path[] pathList) {
    return Files.isReadable(pathList[PathName.FILENAME.get()]) &&
        Files.isDirectory(pathList[PathName.DIRECTORYNAME.get()]);
  }

  public Path getBuildPath(final Path[] pathList, final String lang, final String baseName) {
    final String moveFileName = this.getMoveFileName(lang, baseName);
    final File buildFile = new File(moveFileName);
    final Path buildPath = buildFile.toPath();
    return buildPath;
  }
}
