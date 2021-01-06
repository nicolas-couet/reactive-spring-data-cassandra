package com.acme.core.manager;

import com.acme.core.utils.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;

public abstract class EntityService {
    @Autowired
    private ReactiveCassandraOperations cassandraOperations;

    public ReactiveCassandraOperations getCassandraOperations() {
        return cassandraOperations;
    }
}
