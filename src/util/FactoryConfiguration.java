package util;

import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfig;
    private final SessionFactory factory;

    private FactoryConfiguration(){
        factory = new Configuration().configure()
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class).buildSessionFactory();
    }

    public static FactoryConfiguration getInstance(){
        return (factoryConfig==null) ? (factoryConfig=new FactoryConfiguration()) : factoryConfig;
    }

    public Session getSession(){
        return factory.openSession();
    }
}
