package unrc.dose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for system config framework based on Singleton
 * 
 * @author Elismer Bongiovanni
 * @version 0.1.0
 */
public class SystemConfig {
  /** int containing the port to be listening */
  private int port = 4567;
  /** Object containing the instance to be returned */
  private static SystemConfig singleton;

  private SystemConfig() {
  }

  /**
   * This return a instance of class
   * 
   * @return a Singleton whit the port that will be used
   */
  public static SystemConfig getInstance() {
    if (singleton == null) {
      singleton = new SystemConfig();
    }
    return singleton;
  }

  /**
   * This set a port to be listen
   * 
   * @param portToLisen
   */
  public void setPort(int portToListen) {
    this.port = portToListen;
  }

  /**
   * This retunr the port that is being listening
   * 
   * @return the port
   */
  public int getPort() {
    return port;
  }

  /**
   * We overwrite the clone method, so that an object of this class cannot be
   * cloned
   */
  @Override
  public SystemConfig clone() {
    try {
      Logger loggerSingleton = LoggerFactory.getLogger(SystemConfig.class);
      loggerSingleton.info("cloning attempt");
      throw new CloneNotSupportedException();
    } catch (CloneNotSupportedException ex) {
      System.out.println("Cannot clone an object of the Singleton class");
    }
    return null;
  }
}