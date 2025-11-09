package com.irentaspro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class IrentasproApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void probarConexionSupabase() throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("✅ Conexión exitosa a BD: " + connection.getMetaData().getURL());
            assertNotNull(connection);
        } catch (Exception e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            throw e;
        }
    }
}
