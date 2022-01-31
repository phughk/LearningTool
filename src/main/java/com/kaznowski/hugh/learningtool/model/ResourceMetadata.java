package com.kaznowski.hugh.learningtool.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResourceMetadata {
  private final String author;
  private final LocalDateTime creation;
  private final LocalDateTime lastUpdated;
  private final Integer versionMajor;
  private final Integer versionMinor;
  private final Integer versionPatch;
  private final String description;
  private final String language;
  private final String name;
  private final List<String> keywords;
  private final String license;
}
