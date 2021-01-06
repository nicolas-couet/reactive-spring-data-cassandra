package com.acme.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;

public abstract class EntityService {
    @Autowired
    private ReactiveCassandraOperations cassandraOperations;

    public ReactiveCassandraOperations getCassandraOperations() {
        return cassandraOperations;
    }
}
