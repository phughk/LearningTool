package com.kaznowski.hugh.learningtool;

import com.kaznowski.hugh.learningtool.engine.Learner;
import com.kaznowski.hugh.learningtool.engine.LearningInterface;
import com.kaznowski.hugh.learningtool.model.LearningResource;
import com.kaznowski.hugh.learningtool.swingui.MainWindow;
import com.kaznowski.hugh.learningtool.terminal.ResourceSelector;
import com.kaznowski.hugh.learningtool.terminal.TerminalLearningInterface;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * Hello world!
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);

  @SneakyThrows
  public static void main(String[] args) {
    InputStream stream = App.class.getClassLoader().getResourceAsStream("logging.properties");
    LogManager manager = LogManager.getLogManager();
    if (manager==null) {
      logger.error("Manager was null");
      return;
    }
    manager.readConfiguration(stream);
    logger.error("error");
    logger.warn("warn");
    logger.info("info");
    logger.debug("debug");
    new MainWindow().display();
  }

  private static void terminal() {
    Scanner scanner = new Scanner(System.in);
    LearningResource learningResource = new ResourceSelector(scanner, System.out).selectResource();
    LearningInterface learningInterface = new TerminalLearningInterface(scanner, System.out);
    new Learner(learningInterface).learn(learningResource);
  }
}
