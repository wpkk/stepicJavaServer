package com.wpkk.stepicJavaServer03_02.dbService;

import com.wpkk.stepicJavaServer03_02.dbService.dao.UsersDAO;
import com.wpkk.stepicJavaServer03_02.dbService.dataSets.UsersDataSet;
import com.wpkk.stepicJavaServer03_02.dbService.executor.Executor;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class DBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);    }

//    public UsersDataSet getUser(long id) throws DBException {
//        try {
//            return (new UsersDAO(connection).get(id));
//        } catch (SQLException e) {
//            throw new DBException(e);
//        }
//    }

//    public List<UsersDataSet> getAllUsers() throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            List<UsersDataSet> users = new UsersDAO(session).getAllUsers();
//            session.close();
//            return users;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }

    @Override
    public List<UsersDataSet> getAllUsers() throws DBException {
        try {
            Executor executor = new Executor(sessionFactory);
            return executor.execute(s -> new UsersDAO(s).getAllUsers());
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

//    public UsersDataSet getUserByLogin(String login) throws DBException {
//        try {
//            Session session = sessionFactory.openSession();
//            UsersDataSet dataSet = new UsersDAO(session).getUserByLogin(login);
//            session.close();
//            return dataSet;
//        } catch (HibernateException e) {
//            throw new DBException(e);
//        }
//    }


    @Override
    public UsersDataSet getUserByLogin(String login) throws DBException {
        try {
            Executor executor = new Executor(sessionFactory);
            return executor.execute(s -> new UsersDAO(s).getUserByLogin(login));
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

//    @Override
//    public long  addUser(String name, String password) throws DBException {
//        UsersDAO dao = null;
//        try {
//            Session session = sessionFactory.openSession();
//            Transaction transaction = session.beginTransaction();
//            dao = new UsersDAO(session);
//            dao.insertUser(name, password);
//            transaction.commit();
//            session.close();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//        }
//        return dao.getUserId(name);
//    }

    @Override
    public long addUser(String name, String password) throws DBException {
        try {
            Executor executor = new Executor(sessionFactory);
            return executor.execute(s -> {
                Transaction transaction = s.beginTransaction();
                UsersDAO dao = new UsersDAO(s);
                dao.insertUser(name, password);
                transaction.commit();
                return dao.getUserId(name);
            });
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
            Connection connection = sessionFactoryImpl.getConnectionProvider().getConnection();
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        configuration.setProperty("hibernate.connection.username", "tully");
        configuration.setProperty("hibernate.connection.password", "tully");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
