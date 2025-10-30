package com.areaapp.util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
  @Getter private static SessionFactory sessionFactory = buildSessionFactory();

  private static SessionFactory buildSessionFactory() {
    if (sessionFactory == null) {
      try {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error building SessionFactory: " + e.getMessage(), e);
      }
    }
    return sessionFactory;
  }
}
