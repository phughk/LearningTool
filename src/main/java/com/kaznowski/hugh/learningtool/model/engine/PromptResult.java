package com.kaznowski.hugh.learningtool.model.engine;

import lombok.Data;

import java.time.Duration;

@Data
public class PromptResult {
  private final boolean correct;
  private final Duration duration;
  private final int attempts;
}
