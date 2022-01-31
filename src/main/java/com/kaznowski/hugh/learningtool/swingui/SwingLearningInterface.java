package com.kaznowski.hugh.learningtool.swingui;

import com.kaznowski.hugh.learningtool.engine.LearningInterface;
import com.kaznowski.hugh.learningtool.model.engine.PromptMetadata;
import com.kaznowski.hugh.learningtool.model.engine.PromptResult;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SwingLearningInterface implements LearningInterface {

  private static final Logger logger = LoggerFactory.getLogger(SwingLearningInterface.class);

  private final JTextField promptField;
  private final JTextField answerField;
  private final JButton submitButton;

  public SwingLearningInterface(JTextField promptField, JTextField answerField, JButton submitButton) {
    this.promptField = promptField;
    this.answerField = answerField;
    this.submitButton = submitButton;
  }

  @SneakyThrows
  @Override
  public Future<PromptResult> promptExact(PromptMetadata promptMetadata, String key, String value) {
    logger.debug("Requested prompt for {}", key);
    promptField.setText(key);
    answerField.setText("");
    final AtomicInteger attemptsCounter = new AtomicInteger(0);
    final AtomicReference<PromptResult> promptResult = new AtomicReference<>();
    submitButton.addActionListener(action -> {
      logger.debug("Action triggered for prompt '{}'", key);
      String submitted = answerField.getText();
      int attempts = attemptsCounter.incrementAndGet();
      if (submitted.equals(promptField.getText())) {
        promptResult.set(new PromptResult(true, Duration.ZERO, attempts));
        logger.debug("Prompt resolved within action for '{}'", key);
      }
    });
    FutureTask<PromptResult> task = new FutureTask<>(new Callable<PromptResult>() {
      @Override
      public PromptResult call() throws Exception {
        while (promptResult.get() == null) {
          // blocking until result, TODO handle with futures
          Thread.sleep(100);
        }
        return promptResult.get();
      }
    });
    return task;
  }
}
