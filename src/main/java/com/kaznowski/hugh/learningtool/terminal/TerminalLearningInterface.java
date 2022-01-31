package com.kaznowski.hugh.learningtool.terminal;

import com.kaznowski.hugh.learningtool.engine.LearningInterface;
import com.kaznowski.hugh.learningtool.model.engine.PromptMetadata;
import com.kaznowski.hugh.learningtool.model.engine.PromptResult;
import lombok.SneakyThrows;

import java.io.PrintStream;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TerminalLearningInterface implements LearningInterface {
  private final Scanner scanner;
  private final PrintStream output;

  public TerminalLearningInterface(Scanner scanner, PrintStream output) {
    this.scanner = scanner;
    this.output = output;
  }

  @SneakyThrows
  @Override
  public Future<PromptResult> promptExact(PromptMetadata promptMetadata, String key, String value) {
    QuizPrompt quizPrompt = new QuizPrompt(scanner, output);
    double ratio = 0;
    if (promptMetadata.getTotal() > 0) {
      ratio = ((double) promptMetadata.getCorrect()) / ((double) promptMetadata.getTotal());
    }
    boolean correct = false;
    int attempts = 0;
    while (!correct) {
      attempts++;
      correct = quizPrompt.test(String.format("[%d/%d - %f] %s", promptMetadata.getCorrect(), promptMetadata.getTotal(), ratio, key), value);
    }
    PromptResult promptResult = new PromptResult(correct, Duration.ZERO, attempts);
    FutureTask<PromptResult> future = new FutureTask<>(() -> promptResult);
    future.get();
    return future;
  }
}
