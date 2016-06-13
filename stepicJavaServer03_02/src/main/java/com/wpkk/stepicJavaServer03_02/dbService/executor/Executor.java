package com.wpkk.stepicJavaServer03_02.dbService.executor;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class Executor {
    private final SessionFactory sessionFactory;

    public Executor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T> T execute(Handler<T> handler)
            throws HibernateException {
        Session session = sessionFactory.openSession();
        T result = handler.handle(session);
        session.close();
        return result;
    }
}
