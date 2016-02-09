package com.wpkk.stepicJavaServer03_01.accounts;

import java.util.HashMap;
import java.util.Map;

import com.wpkk.stepicJavaServer03_01.dbService.DBException;
import com.wpkk.stepicJavaServer03_01.dbService.DBService;
import com.wpkk.stepicJavaServer03_01.dbService.dataSets.UsersDataSet;

import javax.jws.soap.SOAPBinding;


/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        DBService dbService = new DBService();
        try {
            dbService.addUser(userProfile.getLogin(), userProfile.getPass());
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        DBService dbService = new DBService();
        UsersDataSet dataSet = null;
        try {
            dataSet = dbService.getUserByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (dataSet != null)
            return new UserProfile(dataSet.getName(),dataSet.getPassword(),"email");
        else
            return null;
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
