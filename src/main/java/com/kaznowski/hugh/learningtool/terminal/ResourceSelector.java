package com.kaznowski.hugh.learningtool.terminal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaznowski.hugh.learningtool.model.LearningResource;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ResourceSelector {
  private final Scanner scanner;
  private final PrintStream output;

  private static final String e = "spanish/";

  private static final List<String> HARDCODED_SHIT = Arrays.asList(
      e + "colours.json",
      e + "dates.json",
      e + "numbers.json",
      e + "ser_estar_rules.json"
  );

  public ResourceSelector(Scanner scanner, PrintStream output) {
    this.scanner = scanner;
    this.output = output;
  }

  @SneakyThrows
  public LearningResource selectResource() {
    TerminalMenuPrompt resourcePrompt = new TerminalMenuPrompt(scanner, output);
    String choice = resourcePrompt.request("Pick a resource: ", HARDCODED_SHIT);
    InputStream resourceStream = ResourceSelector.class.getClassLoader().getResourceAsStream(choice);
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(resourceStream, LearningResource.class);
  }
}
