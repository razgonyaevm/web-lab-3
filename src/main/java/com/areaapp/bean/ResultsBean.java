package com.areaapp.bean;

import com.areaapp.entity.PointResult;
import com.areaapp.service.PointService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class ResultsBean {

  @Inject private PointService pointService;

  public List<PointResult> getResults() {
    return pointService.getAllResults();
  }

  public void clearResults() {
    pointService.clearAllResults();
  }

  public String getPointsJson() {
    List<PointResult> list = getResults();
    if (list.isEmpty()) return "[]";
    return list.stream()
        .map(
            p ->
                String.format(
                    Locale.US,
                    "{\"x\":%.2f,\"y\":%.2f,\"r\":%.2f,\"result\":%s}",
                    p.getX(),
                    p.getY(),
                    p.getR(),
                    p.getResult()))
        .collect(Collectors.joining(",", "[", "]"));
  }
}
