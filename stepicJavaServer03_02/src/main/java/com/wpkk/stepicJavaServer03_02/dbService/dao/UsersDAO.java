package com.wpkk.stepicJavaServer03_02.dbService.dao;

import com.wpkk.stepicJavaServer03_02.dbService.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class UsersDAO {

    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public UsersDataSet get(long id) throws SQLException {
       return (UsersDataSet) session.get(UsersDataSet.class, id);
    }

    public long getUserId(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("name", login)).uniqueResult()).getId();
    }

    public List<UsersDataSet> getAllUsers() throws HibernateException {
        return session.createQuery("from UsersDataSet").list();
    }

    public UsersDataSet getUserByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria.add(Restrictions.eq("name", login)).uniqueResult();
    }

    public void insertUser(String name, String password) throws HibernateException {
        session.save(new UsersDataSet(name, password));
    }
}
