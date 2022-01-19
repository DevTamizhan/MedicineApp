package com.medicine.medicineapp.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.UserLogin;
import com.medicine.medicineapp.dao.UserType;
import com.medicine.medicineapp.util.Constants;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;

import org.springframework.stereotype.Repository;

@Repository
public class UserLoginRepository implements IRepository<UserLogin,String> {

    private final Connection connection;

    public UserLoginRepository(JdbcService service) throws SQLException
    {
        connection = service.getConnection();
    }

    private final String insertQuery = 
            String.format("INSERT INTO %s VALUES(?, ?, ?)", 
            Constants.USER_LOGIN_TABLE);

    private final String updateQuery = 
            String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
            Constants.USER_LOGIN_TABLE,
            Constants.USER_LOGIN_PASSWORD,
            Constants.USER_LOGIN_TYPE,
            Constants.USER_LOGIN_USER_ID);

    private final String deleteQuery = 
            String.format("DELETE FROM %s WHERE %s = ? ",
            Constants.USER_LOGIN_TABLE,
            Constants.USER_LOGIN_USER_ID);
    
    private final String selectAll = 
            String.format("SELECT * FROM %s", 
            Constants.USER_LOGIN_TABLE);
    
    private final String selectAllByField = "SELECT * FROM %s WHERE %s = ?";

    @Override
    public UserLogin insert(UserLogin data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.insertQuery);
        statement.setString(1, data.getUserId());
        statement.setString(2, data.getPassword());
        statement.setString(3, data.getUserType().name());
        if(statement.executeUpdate() == 1)
            return data;
            throw new DataInsertionException("Insertion of UserLogin with id " + data.getUserId() +" failed");
    }

    @Override
    public UserLogin update(UserLogin data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.updateQuery);
        statement.setString(1, data.getPassword());
        statement.setString(2, data.getUserType().name());
        statement.setString(3, data.getUserId());
        connection.setAutoCommit(false);
        connection.commit();
        if(statement.executeUpdate() !=1)
        {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DataInsertionException("Updation of UserLogin with id " + data.getUserId() +" failed");
        }
        connection.commit();
        connection.setAutoCommit(true);
        return data;
    }

    @Override
    public UserLogin delete(UserLogin data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.deleteQuery);
        statement.setString(1, data.getUserId());
        connection.setAutoCommit(false);
        connection.commit();
        if(statement.executeUpdate() != 1)
        {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new DataInsertionException("Deletion of UserLogin with id " + data.getUserId() +" failed");
        }
        connection.commit();
        connection.setAutoCommit(true);
        return data;
    }

    @Override
    public UserLogin getById(String id) throws SQLException {
        String query = String.format(this.selectAllByField, Constants.USER_LOGIN_TABLE,Constants.USER_LOGIN_USER_ID);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        ResultSet set = statement.executeQuery();
        if(set.next())
        {
            return parse(set);
        }
        set.close();
        throw new DataNotFoundException("UserLogin with id "+ id +" is not found");
    }

    @Override
    public List<UserLogin> getAll() throws SQLException {

        PreparedStatement statement = connection.prepareStatement(this.selectAll);
        return processToList(statement);
        
    }

    @Override
    public List<UserLogin> getAllByField(String fieldName, Object value) throws SQLException {
        String query = String.format(this.selectAllByField, Constants.USER_LOGIN_TABLE, fieldName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, value.toString());
        return processToList(statement);
    }

    private List<UserLogin> processToList(PreparedStatement statement) throws SQLException
    {
        ResultSet set = statement.executeQuery();
        List<UserLogin> _all = new ArrayList<>();
        while(set.next())
        {
            _all.add(parse(set));
        }
        return _all;
    }
    private UserLogin parse(ResultSet set) throws SQLException
    {
        UserLogin login = new UserLogin();
        login.setUserId(set.getString(1));
        login.setPassword(set.getString(2));
        login.setUserType(UserType.valueOf(set.getString(3)));
        return login;
    }
    
}
