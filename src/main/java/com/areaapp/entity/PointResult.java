package com.areaapp.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "point_result")
@NoArgsConstructor
@Getter
@Setter
public class PointResult {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "x_value", nullable = false)
  private Double x;

  @Column(name = "y_value", nullable = false)
  private Double y;

  @Column(name = "r_value", nullable = false)
  private Double r;

  @Column(name = "result", nullable = false)
  private Boolean result;

  @Column(name = "timestamp", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;

  @Column(name = "execution_time_ms")
  private long executionTimeMs;

  public PointResult(Double x, Double y, Double r, Boolean result, long executionTimeMs) {
    this.x = x;
    this.y = y;
    this.r = r;
    this.result = result;
    this.executionTimeMs = executionTimeMs;
    this.timestamp = new Date(); // Используем java.util.Date
  }
}
