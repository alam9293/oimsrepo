package org.zkoss.reference.developer.hibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;
    static {
        try {
            //sessionFactory = new Configuration().configure().buildSessionFactory();
            StandardServiceRegistryBuilder registryBuilder =
                    new StandardServiceRegistryBuilder();

              Map<String, Object> settings = new HashMap<>();
              settings.put(Environment.DRIVER, "oracle.jdbc.driver.OracleDriver");
              settings.put(Environment.URL, "jdbc:oracle:thin:@//localhost:1521/orcl");
              settings.put(Environment.USER, "CDG_IBS");
              settings.put(Environment.PASS, "CDG_IBS@SINGAPORE");
              settings.put(Environment.HBM2DDL_AUTO, "update");
              settings.put(Environment.SHOW_SQL, true);

              // c3p0 configuration
              settings.put(Environment.C3P0_MIN_SIZE, 5);         //Minimum size of pool
              settings.put(Environment.C3P0_MAX_SIZE, 20);        //Maximum size of pool
              settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);//Number of connections acquired at a time when pool is exhausted 
              settings.put(Environment.C3P0_TIMEOUT, 1800);       //Connection idle time
              settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
              settings.put(Environment.C3P0_MAX_STATEMENTS, 150); //PreparedStatement cache size
              settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread"); //PreparedStatement cache size
              //settings.put(Environment.POOL_SIZE, 0);
              
              registryBuilder.applySettings(settings);
              
              registry = registryBuilder.build();
              MetadataSources sources = new MetadataSources(registry).addPackage("com.cdgtaxi.ibs.common.model");
              Metadata metadata = sources.getMetadataBuilder().build();
              sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}