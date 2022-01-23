package com.kaznowski.hugh.learningtool;

import java.io.PrintStream;
import java.util.Scanner;

public class QuizPrompt {
  private final Scanner scanner;
  private final PrintStream output;

  public QuizPrompt(Scanner scanner, PrintStream output) {
    this.scanner = scanner;
    this.output = output;
  }

  public boolean test(String prompt, String expected) {
    output.printf("%s: ", prompt);
    return expected.equals(scanner.nextLine());
  }
}
