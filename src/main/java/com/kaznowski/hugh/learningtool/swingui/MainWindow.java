package com.kaznowski.hugh.learningtool.swingui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaznowski.hugh.learningtool.engine.Learner;
import com.kaznowski.hugh.learningtool.model.LearningResource;
import com.kaznowski.hugh.learningtool.terminal.ResourceSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainWindow {
  private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
  private final ExecutorService executorService;

  private final Learner learner;
  private final JFrame jFrame;
  private final JButton submitButton;
  private final JTextField promptField;
  private final JTextField answerField;

  public MainWindow() {
    executorService = new ThreadPoolExecutor(1, 1, 60l, TimeUnit.SECONDS, new SynchronousQueue<>());
    jFrame = new JFrame("Window");
    submitButton = new JButton("Button");
    promptField = new JTextField("Prompt");
    answerField = new JTextField("Answer");
    SwingLearningInterface learningInterface = new SwingLearningInterface(promptField, answerField, submitButton);
    learner = new Learner(learningInterface);
  }

  public void display() {
//    submitButton.addActionListener(e -> promptField.setText(promptField.getText() + randint()));
    BorderLayout layout = new BorderLayout();
    jFrame.setLayout(layout);
    addTopLevelComponents();
    jFrame.setVisible(true);
    jFrame.setSize(200, 200);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void addTopLevelComponents() {
    FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
    JPanel jPanel = new JPanel(flowLayout);
    jPanel.add(promptField, 0);
    jPanel.add(answerField, 1);
    jFrame.add(jPanel, BorderLayout.CENTER);
    jFrame.add(submitButton, BorderLayout.NORTH);
    addMenu();
  }

  private void addMenu() {
    JMenuBar jMenuBar = new JMenuBar();
    JMenu jMenu = new JMenu("Dataset");
    JMenuItem jMenuItem = new JMenuItem("Load");
    jMenuItem.addActionListener(action -> {
      JFileChooser jFileChooser = new JFileChooser(new File("."));
      int i = jFileChooser.showOpenDialog(jFrame);
      if (i == JFileChooser.APPROVE_OPTION) {
        File file = jFileChooser.getSelectedFile();
        System.out.printf("Opened file is %s\n", file);
        InputStream resourceStream = null;
        try {
          resourceStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          LearningResource learningResource = objectMapper.readValue(resourceStream, LearningResource.class);
          // TODO track this and cancel when new resource loaded
          Future<Void> learningFuture = executorService.submit(() -> {
            logger.debug("Started learning future from main");
            learner.learn(learningResource);
            logger.debug("Finished learning future from main");
            return null;
          });
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      } else if (i == JFileChooser.CANCEL_OPTION) {
        // Do nothing
      } else {
        throw new RuntimeException("Unknown file option: " + i);
      }
    });
    jMenu.add(jMenuItem);
    jMenuBar.add(jMenu);

    jFrame.setJMenuBar(jMenuBar);
  }

  String randint() {
    return "" + ((int) (Math.random() * 10));
  }
}
