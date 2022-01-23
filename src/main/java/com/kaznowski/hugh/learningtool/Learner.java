package com.kaznowski.hugh.learningtool;

import java.io.PrintStream;
import java.util.Scanner;

public class Learner {
  private final Scanner scanner;
  private final PrintStream output;

  public Learner(Scanner scanner, PrintStream out) {
    this.scanner = scanner;
    this.output = out;
  }

  public void learn(LearningResource learningResource) {
    int total = 0;
    int correct = 0;
    while (true) {
      int randomIndex = (int) (Math.random() * learningResource.prompts.size());
      PromptEntry randomPrompt = learningResource.prompts.get(randomIndex);
      QuizPrompt quizPrompt = new QuizPrompt(scanner, output);
      double ratio = 0;
      if (total > 0) {
        ratio = ((double) correct) / ((double) total);
      }
      if (quizPrompt.test(String.format("[%d/%d - %f] %s", correct, total, ratio, randomPrompt.getKey()), randomPrompt.getValue())) {
        correct++;
      }
      total++;
    }
  }
}
