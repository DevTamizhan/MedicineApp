package com.medicine.medicineapp.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDetails;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.util.Constants;

import org.springframework.stereotype.Service;

@Service
public class MedicineDetailsRepository implements IRepository<MedicineDetails,Integer> {

    private final JdbcService _jdbcService;
    
    private final Connection connection;

    public MedicineDetailsRepository(JdbcService service) throws SQLException
    {
        this._jdbcService = service;
        connection = _jdbcService.getConnection();
    }

    private final String insertQuery = 
            String.format("INSERT INTO %s(%s, %s, %s, %s) VALUES (?, ?, ?, ?)", 
            Constants.MEDICINE_DETAILS_TABLE,
            Constants.MEDICINE_DETAILS_MEDICINE_NAME,
            Constants.MEDICINE_DETAILS_PRICE,
            Constants.MEDICINE_DETAILS_MIN_AGE,
            Constants.MEDICINE_DETAILS_MAX_AGE
            );

    private final String updateQuery = 
            String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ",
            Constants.MEDICINE_DETAILS_TABLE,
            Constants.MEDICINE_DETAILS_MEDICINE_NAME,
            Constants.MEDICINE_DETAILS_PRICE,
            Constants.MEDICINE_DETAILS_MIN_AGE,
            Constants.MEDICINE_DETAILS_MAX_AGE,
            Constants.MEDICINE_DETAILS_MEDICINE_ID
            );

    private final String selectQueryByField = "SELECT * FROM %s WHERE %s = ?";

    private final String selectAll = 
        String.format("SELECT * FROM %s", Constants.MEDICINE_DETAILS_TABLE);

    private final String deleteQuery = 
        String.format("DELETE FROM %s WHERE %s = ?", 
        Constants.MEDICINE_DETAILS_TABLE,
        Constants.MEDICINE_DETAILS_MEDICINE_ID
        );

    private final String selectLast = 
    String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1", Constants.MEDICINE_DETAILS_TABLE, Constants.MEDICINE_DETAILS_MEDICINE_ID);

    @Override
    public MedicineDetails insert(MedicineDetails data) throws SQLException {
        
        PreparedStatement statement= connection.prepareStatement(this.insertQuery);
        statement.setString(1, data.getMedicineName());
        statement.setDouble(2, data.getPrice());
        statement.setInt(3, data.getMinAge());
        statement.setInt(4, data.getMaxAge());
        if(statement.executeUpdate() == 1)
        {
            ResultSet inserted = statement.executeQuery(this.selectLast);
            MedicineDetails details = null;
            inserted.next();
            details = this.parseFromResultSet(inserted);
            inserted.close();
            return details;
        }
        throw new DataInsertionException("Insertion of MEdicineDetail with id " + data.getMedicineId() +" failed");
    }

    @Override
    public MedicineDetails update(MedicineDetails data) throws SQLException {
        
        PreparedStatement statement= connection.prepareStatement(this.updateQuery);
        statement.setString(1, data.getMedicineName());
        statement.setDouble(2, data.getPrice());
        statement.setInt(3, data.getMinAge());
        statement.setInt(4, data.getMaxAge());
        statement.setInt(5, data.getMedicineId());
        connection.setAutoCommit(false);
        connection.commit();
        if(statement.executeUpdate() == 1)
        {
            connection.commit();
            connection.setAutoCommit(true);
            statement.close();
            return this.getById(data.getMedicineId());
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new DataInsertionException("Updation of MEdicineDetail with id " + data.getMedicineId() +" failed");
    }

    @Override
    public MedicineDetails delete(MedicineDetails data) throws SQLException {
        
        PreparedStatement statement = connection.prepareStatement(this.deleteQuery);
        statement.setInt(1, data.getMedicineId());
        connection.setAutoCommit(false);
        connection.commit();
        int affected = statement.executeUpdate();
        if(affected != 1)
        {
            connection.rollback();
            connection.setAutoCommit(true);
            throw new RuntimeException("MedicineDetails not found");
        }
        connection.commit();
        connection.setAutoCommit(true);
        return data;
    }

    @Override
    public MedicineDetails getById(Integer id) throws SQLException {
        
        String query = String.format(this.selectQueryByField, Constants.MEDICINE_DETAILS_TABLE, Constants.MEDICINE_DETAILS_MEDICINE_ID);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet set = statement.executeQuery();
        while(set.next())
        {
            MedicineDetails details = this.parseFromResultSet(set);
            set.close();
            System.out.println("found the medicine detail");
            return details;
        }
        System.out.println("No data");
        throw new DataNotFoundException("MedicineDetails with id "+ id+" is not found");
    }

    @Override
    public List<MedicineDetails> getAll() throws SQLException {
        
        List<MedicineDetails> all = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(this.selectAll);
        while(set.next())
        {
            all.add(this.parseFromResultSet(set));
        }
        return all;
    }

    @Override
    public List<MedicineDetails> getAllByField(String fieldName, Object value) throws SQLException {
        
        List<MedicineDetails> all = new ArrayList<>();
        String query = String.format(this.selectQueryByField, Constants.MEDICINE_DETAILS_TABLE, fieldName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, value);
        ResultSet set = statement.executeQuery();
        while(set.next())
        {
            all.add(this.parseFromResultSet(set));
        }
        return all;
    }

    private MedicineDetails parseFromResultSet(ResultSet set) throws SQLException
    {
        MedicineDetails details = new MedicineDetails();
            details.setMedicineId(set.getInt(1));
            details.setMedicineName(set.getString(2));
            details.setPrice(set.getDouble(3));
            details.setMinAge(set.getInt(4));
            details.setMaxAge(set.getInt(5));
            return details;
    }

}
;