package com.acme.core.utils;

import java.io.File;

public class DatabaseSchemaUtils {
    private  static final String basePath = "database";

    public static final String SCHEMA_NAME = "acme";

    public static final String SCHEMA_FILE_PATH = basePath + File.separator + "schema.cql";

    public static final String UDT_PATH = basePath + File.separator + "udt" + File.separator;

    public static final String TABLES_PATH = basePath + File.separator + "tables" + File.separator;

    public static final String INDEXES_PATH = basePath + File.separator + "indexes" + File.separator;

    public static final String VIEWS_PATH = basePath + File.separator + "views" + File.separator;

}
