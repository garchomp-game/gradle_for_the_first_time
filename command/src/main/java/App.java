import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.io.File;
import java.nio.file.*;
import java.util.concurrent.Callable;
import java.util.*;

import service.FileManager;
import myEnum.PathName;
/*
例えばpath_configに
lang=gcc // node ruby java...
default_mode=full // compile run...
default_code_path=path/to/
default_source_path=path/to/
*/
@Command(
  name = "run", 
  mixinStandardHelpOptions = true, 
  version = "1.0",
  description = "様々な言語のコンパイルと実行をしやすくしたコマンド"
)
public class App implements Callable<Integer> {
  @Option(names = {"-l", "--lang"},
  defaultValue = "",
  description = "言語のセットができます")
  private String lang;
  
  @Option(names = {"-c", "--compile"},
  description = "コンパイルをします")
  private boolean compile;

  @Option(names = {"-e", "--execution"},
  description = "コンパイルしたファイルを実行します")
  private boolean execution;
  
  @Option(names = {"-f", "--full"},
  description = "フル実行")
  private boolean full;
  
  @Parameters(paramLabel = "FILE", description = "ファイル取得")
  File[] files;
  
  @Option(names = {"-h", "--help"}, usageHelp = true, description = "ヘルプ一覧")
  private boolean helpRequested = false;
  
  @Override
  public Integer call() throws Exception {
    Path[] pathList = new Path[PathName.PATHLENGTH.get()];
    if(Objects.isNull(this.files))
      throw new NullPointerException("ファイルを指定してください");  
    else
      for(int i = 0; i < files.length; i++)
        pathList[i] = this.files[i].toPath();
    FileManager fm = new FileManager(this.files, pathList);
    fm.setLang(this.lang);
    fm.setCompile(this.compile);
    fm.setExecution(this.execution);
    fm.setFull(this.full);
    return fm.run();
  }

  public static void main(String... args) {
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode);
  }
}
