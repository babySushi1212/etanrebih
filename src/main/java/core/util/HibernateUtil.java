package core.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    // for reading hibernate.cfg.xml
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    private static void buildSessionFactory() {
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            // registry = new StandardServiceRegistryBuilder().configure("核心組態路徑檔名").build();
            MetadataSources metadataSource = new MetadataSources(registry);
            // metadataSource.addResource("映射檔路徑檔名");
            // metadataSource.addAnnotatedClass(實體類別名.class);
            Metadata metadata = metadataSource.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
