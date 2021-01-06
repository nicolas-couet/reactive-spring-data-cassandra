package com.acme.core.configuration.test;

import com.acme.core.configuration.ApplicationContextTest;
import com.acme.core.utils.DatabaseSchemaUtils;
import com.datastax.oss.driver.api.core.CqlSession;
import com.github.nosan.boot.autoconfigure.embedded.cassandra.EmbeddedCassandraAutoConfiguration;
import com.github.nosan.embedded.cassandra.cql.CqlScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@DataCassandraTest(properties = {"spring.data.cassandra.local-datacenter=datacenter1"})
@ComponentScan("com.acme")
@TestPropertySource({"classpath:applicationTest.properties"})
@ImportAutoConfiguration({ApplicationContextTest.class, EmbeddedCassandraAutoConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({"IntegrationTest"})
public abstract class EmbeddedCassandraCoreIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddedCassandraCoreIntegrationTest.class);

    @Autowired
    private CqlSession session;

    private boolean isSchemaInitialized = false;

    private boolean isTestInitialized = false;

    @BeforeAll
    public void schemaInitialization() throws IOException{
        if (!isSchemaInitialized) {
            Assert.notNull(session, "Failed to connect to cluster...");

            logger.info("Init cassandra schema");
            CqlScript.ofClassPath(DatabaseSchemaUtils.SCHEMA_FILE_PATH).forEachStatement(session::execute);

            logger.info("Swith to '{}' schema", DatabaseSchemaUtils.SCHEMA_NAME);
            session.execute("USE " + DatabaseSchemaUtils.SCHEMA_NAME + ";");

            logger.info("Create tables");
            execute(DatabaseSchemaUtils.TABLES_PATH);

            this.isSchemaInitialized = true;
        }
    }

    private void execute(String path) throws IOException{
        logger.info("Read files from '<classpath>{}{}'...", File.separator, path);

        Stream.of(Objects.requireNonNull(new ClassPathResource(path).getFile()
                .listFiles()))
                .filter(file -> !file.isDirectory())
                .filter(file -> file.getName().toLowerCase().endsWith(".cql"))
                .map(File::getName).forEach(filename -> {
                    logger.info("...{}", filename);
                    CqlScript.ofClassPath(path + filename)
                            .forEachStatement(session::execute);
        });
    }

    public boolean isTestInitialized() {
        return isTestInitialized;
    }

    public void setTestInitialized(boolean testInitialized) {
        isTestInitialized = testInitialized;
    }
}