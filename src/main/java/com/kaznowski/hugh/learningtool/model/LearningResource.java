package com.kaznowski.hugh.learningtool.model;

import lombok.Data;

import java.util.List;

@Data
public class LearningResource {
  public ResourceMetadata metadata;
  public List<PromptEntry> prompts;
}
