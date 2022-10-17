package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;

import logic.FileLogic;
import myEnum.Languages;
import myEnum.PathName;

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

  public FileManager(final File[] files, final Path[] pathList) {
    this.files = files;
    this.pathList = pathList;
    this.fileName = this.pathList[0].toString();
    this.extension = FilenameUtils.getExtension(this.fileName);
    this.baseName = FilenameUtils.getBaseName(this.fileName);
    if (pathList.length == 1) {
      this.directoryName = this.pathList[PathName.DIRECTORYNAME.get()].toString();
      this.directoryPath = this.pathList[PathName.DIRECTORYNAME.get()];
    }
  }

  public File[] getFiles() {
    return files;
  }

  public void setFiles(final File[] files) {
    this.files = files;
  }

  public String getLang() {
    return lang;
  }

  public boolean isCompile() {
    return compile;
  }

  public boolean isExecution() {
    return execution;
  }

  public boolean isFull() {
    return full;
  }

  public Path[] getPathList() {
    return pathList;
  }

  public void setPathList(final Path[] pathList) {
    this.pathList = pathList;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(final String fileName) {
    this.fileName = fileName;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(final String extension) {
    this.extension = extension;
  }

  public String getBaseName() {
    return baseName;
  }

  public void setBaseName(final String baseName) {
    this.baseName = baseName;
  }

  public String getDirectoryName() {
    return directoryName;
  }

  public void setDirectoryName(final String directoryName) {
    this.directoryName = directoryName;
  }

  public Path getDirectoryPath() {
    return directoryPath;
  }

  public void setDirectoryPath(final Path directoryPath) {
    this.directoryPath = directoryPath;
  }

  public void setLang(final String lang) {
    this.lang = lang;
  }

  public void setCompile(final boolean compile) {
    this.compile = compile;
  }

  public void setExecution(final boolean execution) {
    this.execution = execution;
  }

  public void setFull(final boolean full) {
    this.full = full;
  }

  public int run() {
    if (this.compile || this.full)
      this.compile();
    final FileLogic fl = new FileLogic();
    if (fl.isMoveDirectory(this.pathList))
      this.fileMove();
    if (BooleanUtils.isFalse(this.compile))
      try {
        throw new Exception("言語を指定してください");
      } catch (final Exception e) {
        e.printStackTrace();
      }
    // 実行メソッド
    this.runExtension();
    return 0;
  }

  public void runExtension() {
    // このあと実行処理を実装するので消さないで！
    final FileLogic fl = new FileLogic();
    final Path buildPath = fl.getBuildPath(this.pathList, this.lang, this.baseName);
    final Path changePath = buildPath.resolve(fl.getBuildExtension(Languages.getLang(this.lang)));
    System.out.println(changePath.toString());
  }

  private void compile() {
    Languages result = null;
    System.out.println(this.lang + " compile");
    final MyBuilder mb = new MyBuilder(this.pathList, this.lang);
    mb.setFileName(this.fileName);
    mb.setExtension(this.extension);
    mb.setBaseName(this.baseName);
    for (final Languages val : Languages.values())
      if (this.lang.equals(val.toString()))
        result = mb.build(val);
    if (Objects.isNull(result)) {
      try {
        throw new Exception("言語を指定してください");
      } catch (final Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void fileMove() {
    final FileLogic fi = new FileLogic();
    final String moveFileName = fi.getMoveFileName(this.lang, this.baseName);
    final Path buildPath = fi.getBuildPath(this.pathList, this.lang, this.baseName);
    try {
      if (Files.isReadable(buildPath))
        Files.delete(this.directoryPath.resolve(moveFileName));
      Files.move(buildPath, this.directoryPath.resolve(moveFileName));
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
