package com.areaapp.service;

import com.areaapp.entity.PointResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
@Transactional
public class PointService {

  @PersistenceContext(unitName = "mongoPU")
  private EntityManager entityManager;

  public void saveResult(PointResult result) {
    try {
      entityManager.persist(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public List<PointResult> getAllResults() {
    try {
      List<PointResult> results =
          entityManager
              .createQuery(
                  "SELECT p FROM PointResult p ORDER BY p.timestamp DESC", PointResult.class)
              .getResultList();
      return results;
    } catch (Exception e) {
      e.printStackTrace();
      return List.of();
    }
  }

  public void clearAllResults() {
    try {
      int deleted = entityManager.createQuery("DELETE FROM PointResult").executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
