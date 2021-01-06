package com.acme.core.entity;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;

import java.time.LocalDateTime;

public class AbstractEntity {
  @Column("creation_date")
  private LocalDateTime creationDate;

  @Column("last_modification_date")
  private LocalDateTime lastModificationDate;

  @CassandraType(type = CassandraType.Name.INT)
  private EntityState state;

  public EntityState getState() {
    return state;
  }

  public void setState(EntityState state) {
    this.state = state;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getLastModificationDate() {
    return lastModificationDate;
  }

  public void setLastModificationDate(LocalDateTime lastModificationDate) {
    this.lastModificationDate = lastModificationDate;
  }
}
