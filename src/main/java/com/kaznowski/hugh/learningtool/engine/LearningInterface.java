package com.kaznowski.hugh.learningtool.engine;

import com.kaznowski.hugh.learningtool.model.engine.PromptMetadata;
import com.kaznowski.hugh.learningtool.model.engine.PromptResult;

import java.util.concurrent.Future;

public interface LearningInterface {
  Future<PromptResult> promptExact(PromptMetadata promptMetadata, String key, String value);
}
