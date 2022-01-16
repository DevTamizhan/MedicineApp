package com.medicine.medicineapp.repo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class JdbcService {
    
    @Value("${jdbc.url}")
    private  String _jdbcUrl;

    @Value("${jdbc.username}")
    private String _jdbcUserName;

    @Value("${jdbc.password}")
    private String _jdbcPassword;

    private Connection connection = null;

    public Connection getConnection() throws SQLException
    {
        if(connection == null)
        {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            if(_jdbcUrl == null)
                throw new NoSuchFieldError("jdbc url is null in properties file. Set it using jdbc.url");
            if(_jdbcUserName == null)
                throw new NoSuchFieldError("jdbc username is null in properties file. Set it using jdbc.username");
            if(_jdbcPassword == null)
                throw new NoSuchFieldError("jdbc password is null in properties file. Set it using jdbc.password");
                Properties info = new Properties();
                info.put("user",_jdbcUserName);
            info.put("password", _jdbcPassword);
            connection =  driver.connect(_jdbcUrl, info);
            initializeDatabase();
        }
        return connection;
    }

    public void closeConnection() throws SQLException
    {
        if(connection != null && !connection.isClosed())
            connection.close();
    }

    private void initializeDatabase() throws SQLException
    {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        path = path.replace("file:/", "");
        path += "data.sql";
        Statement stmt = connection.createStatement();
        try{
            String query = Files.readString(Path.of(path));
            String[] queries = query.split(";");
            for(String q : queries){
                stmt.execute(q);
            }
        }
        catch(IOException e)
        {
            
        }
        
    }

}
