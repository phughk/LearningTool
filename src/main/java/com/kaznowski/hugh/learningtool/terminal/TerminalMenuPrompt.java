package com.kaznowski.hugh.learningtool.terminal;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TerminalMenuPrompt {
  private final Scanner scanner;
  private final PrintStream output;

  public TerminalMenuPrompt(Scanner scanner, PrintStream output) {
    this.scanner = scanner;
    this.output = output;
  }

  public String request(String prompt, List<String> choices) {
    for (int index = 0; index < choices.size(); index++) {
      output.printf("%d. %s\n", index, choices.get(index));
    }
    while (true) {
      output.println(prompt);
      String line = scanner.nextLine().trim();
      Optional<Integer> number = asNumber(line);
      if (number.isPresent()) {
        if (number.get() >= 0 && number.get() < choices.size()) {
          return choices.get(number.get());
        } else {
          output.printf("Selection %d not in range <0, %d)\n", number.get(), choices.size());
        }
      } else {
        output.printf("'%s' was not a number, and keywords aren't supported yet\n", line);
      }
    }
  }

  private static Optional<Integer> asNumber(String line) {
    try {
      Integer i = Integer.parseInt(line);
      return Optional.of(i);
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }
}
