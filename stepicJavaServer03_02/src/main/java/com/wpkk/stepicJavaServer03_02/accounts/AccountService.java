package com.wpkk.stepicJavaServer03_02.accounts;

import com.wpkk.stepicJavaServer03_02.dbService.DBException;
import com.wpkk.stepicJavaServer03_02.dbService.DBService;
import com.wpkk.stepicJavaServer03_02.dbService.DBServiceImpl;
import com.wpkk.stepicJavaServer03_02.dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;


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
    DBService dbService;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
        dbService = new DBServiceImpl();
    }

    public void addNewUser(UserProfile userProfile) {
        try {
            dbService.addUser(userProfile.getLogin(), userProfile.getPass());
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
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
