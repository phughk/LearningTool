package com.kaznowski.hugh.learningtool.engine;

import com.kaznowski.hugh.learningtool.model.LearningResource;
import com.kaznowski.hugh.learningtool.model.PromptEntry;
import com.kaznowski.hugh.learningtool.model.engine.PromptMetadata;
import com.kaznowski.hugh.learningtool.model.engine.PromptResult;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

public class Learner {
  private final LearningInterface learningInterface;
  private final Logger logger = LoggerFactory.getLogger(Learner.class);

  public Learner(LearningInterface learningInterface) {
    this.learningInterface = learningInterface;
  }

  @SneakyThrows
  public void learn(LearningResource learningResource) {
    logger.debug("Started learning {}", learningResource.getMetadata());
    int total = 0;
    int correct = 0;
    while (true) {
      int randomIndex = (int) (Math.random() * learningResource.prompts.size());
      PromptEntry randomPrompt = learningResource.prompts.get(randomIndex);
      logger.debug("Starting prompt {}", randomPrompt.getKey());
      PromptMetadata promptMetadata = new PromptMetadata(correct, total);
      Future<PromptResult> result = learningInterface.promptExact(promptMetadata, randomPrompt.getKey(), randomPrompt.getValue());
      while (!result.isDone() && !result.isCancelled()) {
        Thread.sleep(100);
      }
      if (result.get().isCorrect()) {
        correct++;
      }
      total += result.get().getAttempts();
    }
  }
}
