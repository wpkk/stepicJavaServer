package com.wpkk.stepicJavaServer03_02.dbService;

import com.wpkk.stepicJavaServer03_02.dbService.dataSets.UsersDataSet;

import java.util.List;

public interface DBService {

    public long addUser(String name, String password) throws DBException;

    public UsersDataSet getUserByLogin(String login) throws DBException;

    public List<UsersDataSet> getAllUsers() throws DBException;
}
