/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author roberto
 */
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/*@DataSourceDefinition(
    name = "java:global/jdbc/mockbusterDS",  // Modificato per allinearsi con il persistence.xml
    className = "com.mysql.cj.jdbc.Driver",
    url = "jdbc:mysql://localhost:3306/mockbuster",
    user = "username",
    password = "password",
    properties = {
        "useUnicode=true",
        "characterEncoding=UTF-8",
        "serverTimezone=UTC"
    }
)*/
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "mockbusterPU")
    private EntityManager em;
}

