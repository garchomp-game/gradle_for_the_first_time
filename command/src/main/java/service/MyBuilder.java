package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

import logic.FileLogic;
import myEnum.Languages;
import myEnum.PathName;
import myException.NotMatchLangException;

public class MyBuilder {
  public Path[] getPathList() {
    return pathList;
  }

  public void setPathList(final Path[] pathList) {
    this.pathList = pathList;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(final String lang) {
    this.lang = lang;
  }

  public String getExtension() {
    return extension;
  }

  public String getFileName() {
    return fileName;
  }

  public String getBaseName() {
    return baseName;
  }

  public FileLogic getFileLogic() {
    return fileLogic;
  }

  public void setFileLogic(final FileLogic fileLogic) {
    this.fileLogic = fileLogic;
  }

  private Path[] pathList = new Path[PathName.PATHLENGTH.get()];
  private String lang;
  private String extension;
  private String fileName;
  private String baseName;
  FileLogic fileLogic;

  public MyBuilder(final Path[] pathList, final String lang) {
    this.pathList = pathList;
    this.lang = lang;
    this.fileLogic = new FileLogic();
  }

  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }

  public void setExtension(final String extension) {
    this.extension = extension;
  }

  public void setBaseName(final String baseName) {
    this.baseName = baseName;
  }

  public Languages build(final Languages language) {
    switch (language) {
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
    if (!this.extension.equals("c")) {
      try {
        throw new NotMatchLangException("C言語");
      } catch (final NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    final StringBuilder command = new StringBuilder();
    command.append("gcc ");
    command.append(this.fileName);
    if (this.fileLogic.isMoveDirectory(this.pathList)) {
      command.append(" -o ");
      command.append(this.baseName);
    }
    if (build(command.toString()))
      return Languages.CLANG;
    return null;
  }

  private Languages java() {
    System.out.println("java");
    if (!this.extension.equals("java")) {
      try {
        throw new NotMatchLangException("java");
      } catch (final NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    final StringBuilder command = new StringBuilder();
    command.append("javac ");
    if (this.fileLogic.isMoveDirectory(this.pathList)) {
      try {
        command.append("-d ");
        command.append(this.pathList[PathName.DIRECTORYNAME.get()].toString());
      } catch (final Exception e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    command.append(this.fileName);
    if (build(command.toString()))
      return Languages.JAVA;
    return null;
  }

  private Languages node() {
    if (!this.extension.equals("js")) {
      try {
        throw new NotMatchLangException("js");
      } catch (final NotMatchLangException e) {
        e.printStackTrace();
        System.exit(0);
      }
    }
    final String command = "node " + this.fileName;
    if (build(command))
      return Languages.NODE;
    return null;
  }

  private boolean build(final String command) {
    try {
      final Process process = Runtime.getRuntime().exec(command);
      final BufferedReader reader = new BufferedReader(
          new InputStreamReader(process.getInputStream()));

      final StringBuilder output = new StringBuilder();
      String line;
      final String br = System.getProperty("line.separator");
      while ((line = reader.readLine()) != null) {
        output.append(line);
        output.append(br);
      }
      System.out.println(output.toString());
      return true;
    } catch (final Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
