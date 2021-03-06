package com.wpkk.stepicJavaServer03_01.dbService;

import com.wpkk.stepicJavaServer03_01.dbService.dataSets.UsersDataSet;

import java.util.List;

public interface DBService {

    public long addUser(String name, String password) throws DBException;

    public UsersDataSet getUserByLogin(String login) throws DBException;

    public List<UsersDataSet> getAllUsers() throws DBException;

    public void cleanUp() throws DBException;
}
