package colonies.pmardle.src;

import java.util.Properties;

public class Version {
  private static String major;
  private static String minor;
  private static String rev;
  private static String build;
  @SuppressWarnings("unused")
private static String mcversion;

  static void init(Properties properties) {
    if (properties != null) {
        major = properties.getProperty("ColonyChest.build.major.number");
        minor = properties.getProperty("ColonyChest.build.minor.number");
        rev = properties.getProperty("ColonyChest.build.revision.number");
        build = properties.getProperty("ColonyChest.build.number");
        mcversion = properties.getProperty("ColonyChest.build.mcversion");
    }
  }

  public static String fullVersionString() {
    return String.format("%s.%s.%s build %s", major,minor,rev, build);
  }
}