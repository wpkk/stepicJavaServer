package com.wpkk.stepicJavaServer03_02.dbService.executor;

import org.hibernate.Session;

public interface Handler<T> {
    T handle(Session session);
}
