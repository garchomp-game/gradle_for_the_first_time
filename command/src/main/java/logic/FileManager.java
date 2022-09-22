package logic;

import org.apache.commons.io.FilenameUtils;
import java.util.*;
import java.nio.file.*;
import java.io.*;
import myEnum.Languages;

public class FileManager {
    public static String getBuildExtension(Languages lang) {
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
}