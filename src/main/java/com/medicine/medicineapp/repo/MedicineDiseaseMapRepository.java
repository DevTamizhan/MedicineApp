package com.medicine.medicineapp.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDiseaseMap;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.util.Constants;

import org.springframework.stereotype.Repository;

@Repository
public class MedicineDiseaseMapRepository implements IRepository<MedicineDiseaseMap,Integer> {

    private final Connection connection;

    private final String insertQuery = 
            String.format("INSERT INTO %s(%s, %s) VALUES (?, ?) ", 
            Constants.MEDICINE_DISEASE_MAP_TABLE,
            Constants.MEDICINE_DISEASE_MAP_MEDICINE_ID,
            Constants.MEDICINE_DISEASE_MAP_DISEASE_NAME);

    private final String updateQuery = 
            String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", 
            Constants.MEDICINE_DISEASE_MAP_TABLE,
            Constants.MEDICINE_DISEASE_MAP_MEDICINE_ID,
            Constants.MEDICINE_DISEASE_MAP_DISEASE_NAME,
            Constants.MEDICINE_DISEASE_MAP_ENTRY_ID);
    
    private final String deleteQuery = 
            String.format("DELETE FROM %s WHERE %s = ?", 
            Constants.MEDICINE_DISEASE_MAP_TABLE,
            Constants.MEDICINE_DISEASE_MAP_ENTRY_ID);

    private final String getAll = "SELECT * FROM " + Constants.MEDICINE_DISEASE_MAP_TABLE;

    private final String getByField = "SELECT * FROM %s WHERE %s = ?";

    private final String getLast = 
            String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1", 
            Constants.MEDICINE_DISEASE_MAP_TABLE,
            Constants.MEDICINE_DISEASE_MAP_ENTRY_ID);

    public MedicineDiseaseMapRepository(JdbcService service) throws SQLException
    {   
        connection = service.getConnection();
    }
    @Override
    public MedicineDiseaseMap insert(MedicineDiseaseMap data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.insertQuery);
        statement.setInt(1, data.getMedicineId());
        statement.setString(2, data.getDiseaseName());
        int res = statement.executeUpdate();
        System.out.println(res);
        if(res == 1)
        {
            statement = connection.prepareStatement(this.getLast);
            ResultSet set = statement.executeQuery();
            if(set.next())
                return parse(set);
            else
            {
                throw new DataInsertionException("MedicineDiseaseMap data cannot be inserted");
            }
        }
        throw new DataInsertionException("Insertion of MedicineDiseaseMap with id " + data.getEntryId() +" failed");
    }

    @Override
    public MedicineDiseaseMap update(MedicineDiseaseMap data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.updateQuery);
        statement.setInt(1, data.getMedicineId());
        statement.setString(2, data.getDiseaseName());
        statement.setInt(3, data.getEntryId());
        connection.setAutoCommit(false);
        connection.commit();
        if(statement.executeUpdate() == 1)
        {
            connection.commit();
            connection.setAutoCommit(true);
            return data;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new DataInsertionException("Updation of MedicineDiseaseMap with id " + data.getEntryId() +" failed");
    }

    @Override
    public MedicineDiseaseMap delete(MedicineDiseaseMap data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.deleteQuery);
        statement.setInt(1, data.getEntryId());
        connection.setAutoCommit(false);
        connection.commit();
        if(statement.executeUpdate() == 1)
        {
            connection.commit();
            connection.setAutoCommit(true);
            return data;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new DataInsertionException("Deletion of MedicineDiseaseMap with id " + data.getEntryId() +" failed");
    }

    @Override
    public MedicineDiseaseMap getById(Integer id) throws SQLException {
        List<MedicineDiseaseMap> _all =  this.getAllByField(Constants.MEDICINE_DISEASE_MAP_ENTRY_ID, id);
        if(_all.size() == 0)
            throw new DataNotFoundException("MedicineDiseaseMap with Id "+ id +" is not found");
        return _all.get(0);
    }

    @Override
    public List<MedicineDiseaseMap> getAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.getAll);
        return processToList(statement);
    }

    @Override
    public List<MedicineDiseaseMap> getAllByField(String fieldName, Object value) throws SQLException {
        String query = String.format(this.getByField,Constants.MEDICINE_DISEASE_MAP_TABLE, fieldName);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, value);
        return processToList(statement);
    }

    private MedicineDiseaseMap parse(ResultSet set) throws SQLException
    {
        MedicineDiseaseMap map = new MedicineDiseaseMap();
        map.setEntryId(set.getInt(1));
        map.setMedicineId(set.getInt(2));
        map.setDiseaseName(set.getString(3));
        System.out.println(map.getEntryId());
        return map;
    }

    private List<MedicineDiseaseMap> processToList(PreparedStatement statement) throws SQLException
    {
        ResultSet set = statement.executeQuery();
        List<MedicineDiseaseMap> _all = new ArrayList<>();
        while(set.next())
        {
            _all.add(parse(set));
        }
        return _all;
    }
    
}
