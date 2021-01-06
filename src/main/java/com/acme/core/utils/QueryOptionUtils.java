package com.acme.core.utils;

import com.datastax.oss.driver.api.core.ConsistencyLevel;
import org.springframework.data.cassandra.core.InsertOptions;

public class QueryOptionUtils {
    public static InsertOptions strongConsistencyInsertOptions(){
        return InsertOptions.builder().ifNotExists(true).consistencyLevel(ConsistencyLevel.QUORUM)
                .build();
    }
}
