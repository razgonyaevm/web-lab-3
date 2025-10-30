package com.areaapp.service;

import com.areaapp.entity.PointResult;
import com.areaapp.util.HibernateUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Named
@ApplicationScoped
public class PointService {

  public void saveResult(PointResult result) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try {
      transaction = session.beginTransaction();
      session.persist(result);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public List<PointResult> getAllResults() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    try {
      return session
          .createSelectionQuery("FROM PointResult ORDER BY timestamp DESC", PointResult.class)
          .list();
    } finally {
      session.close();
    }
  }

  public void clearAllResults() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try {
      transaction = session.beginTransaction();
      session.createMutationQuery("DELETE FROM PointResult").executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) transaction.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }
  }
}
