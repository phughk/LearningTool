package com.kaznowski.hugh.learningtool;

import lombok.Data;

import java.util.List;

@Data
public class LearningResource {
  public List<PromptEntry> prompts;
}
