/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.integration;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import org.springframework.test.jdbc.JdbcTestUtils;

/**
 * Base makes sure that before any test empty database is available.
 *
 * @author jaroslaw
 */
@ContextConfiguration("/testApplicationContext.xml")
public abstract class DomainAwareBase extends AbstractJUnit4SpringContextTests {

    private final String deleteScript = "src/test/resources/cleanup.sql";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void deleteAllDomainEntities() {
        JdbcTestUtils.executeSqlScript(jdbcTemplate,
                new FileSystemResource(deleteScript), false);
    }
}
