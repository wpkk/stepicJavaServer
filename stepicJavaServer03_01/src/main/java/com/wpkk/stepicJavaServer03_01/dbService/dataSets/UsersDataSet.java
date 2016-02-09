package com.wpkk.stepicJavaServer03_01.dbService.dataSets;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {
    private long id;
    private String name;
    private String password;

    public UsersDataSet(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public  String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
