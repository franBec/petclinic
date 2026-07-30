package dev.pollito.petclinic_java_gradle_react_tailwind_ts.util.metadata;

import java.time.OffsetDateTime;

public interface ResponseMetadata {
    String getInstance();

    Integer getStatus();

    OffsetDateTime getTimestamp();

    String getTrace();
}
