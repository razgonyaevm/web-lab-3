package com.areaapp.bean;

import com.areaapp.entity.PointResult;
import com.areaapp.service.PointService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Named
@SessionScoped
@Getter
@Setter
public class AreaCheckBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private Double x = 0.0;
  private Double y = 0.0;
  private Double r = 1.0;

  @Inject private PointService pointService;

  public void checkPoint() {
    System.out.println("Проверка точки: X=" + x + ", Y=" + y + ", R=" + r);
    if (isValidCoordinates(x, y, r)) {
      long startTime = System.nanoTime();
      boolean check = checkArea(x, y, r);
      long endTime = System.nanoTime();
      PointResult pointResult = new PointResult(x, y, r, check, endTime - startTime);
      pointService.saveResult(pointResult);
    }
  }

  public void checkClick() {
    FacesContext context = FacesContext.getCurrentInstance();
    String clickXStr = context.getExternalContext().getRequestParameterMap().get("clickX");
    String clickYStr = context.getExternalContext().getRequestParameterMap().get("clickY");
    String currentRStr = context.getExternalContext().getRequestParameterMap().get("currentR");

    System.out.println(
        "Координаты клика: X=" + clickXStr + ", Y=" + clickYStr + ", R=" + currentRStr);

    if (clickXStr != null && clickYStr != null) {
      try {
        this.x = Double.parseDouble(clickXStr);
        this.y = Double.parseDouble(clickYStr);

        // Если передано значение R, используем его
        if (currentRStr != null && !currentRStr.isEmpty()) {
          this.r = Double.parseDouble(currentRStr);
        }

        System.out.println("Установлены координаты: X=" + x + ", Y=" + y + ", R=" + r);

        // Проверяем, что координаты находятся в допустимых диапазонах
        if (isValidCoordinates(x, y, r)) {
          checkPoint();
        } else {
          System.err.println("Координаты вне допустимого диапазона после округления");
        }
      } catch (NumberFormatException e) {
        System.err.println("Ошибка парсинга координат: " + e.getMessage());
      }
    }
  }

  private boolean isValidCoordinates(Double x, Double y, Double r) {
    return x != null && y != null && r != null && x >= -5 && x <= 3 && y >= -3 && y <= 3 && r >= 1
        && r <= 3;
  }

  private boolean checkArea(Double x, Double y, Double r) {
    if (!isValidCoordinates(x, y, r)) {
      return false;
    }

    // Проверка попадания в треугольник в первой четверти
    if (x >= 0 && y >= 0) {
      return (x <= r) && (y <= r / 2) && (y <= -0.5 * x + r / 2);
    }

    // Проверка попадания в четверть круга во второй четверти
    if (x <= 0 && y >= 0) {
      return (x * x + y * y) <= (r / 2) * (r / 2);
    }

    // Проверка попадания в квадрат в третьей четверти
    if (x <= 0 && y <= 0) {
      return (x >= -r) && (y >= -r);
    }

    return false;
  }
}
