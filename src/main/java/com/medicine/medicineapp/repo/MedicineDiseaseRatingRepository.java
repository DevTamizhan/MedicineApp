package com.medicine.medicineapp.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.medicine.medicineapp.dao.MedicineDiseaseRating;
import com.medicine.medicineapp.exception.DataInsertionException;
import com.medicine.medicineapp.exception.DataNotFoundException;
import com.medicine.medicineapp.util.Constants;

import org.springframework.stereotype.Repository;

@Repository
public class MedicineDiseaseRatingRepository implements IRepository<MedicineDiseaseRating, Integer> {

    private final Connection connection;

    private final String insertQuery = String.format("INSERT INTO %s(%s,%s,%s) VALUES(?,?,?)",
            Constants.MEDICINE_DISEASE_RATING_TABLE,
            Constants.MEDICINE_DISEASE_RATING_USER_ID,
            Constants.MEDICINE_DISEASE_RATING_ENTRY_ID,
            Constants.MEDICINE_DISEASE_RATING_RATING_FILED);

    private final String updateQuery = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?",
            Constants.MEDICINE_DISEASE_RATING_TABLE,
            Constants.MEDICINE_DISEASE_RATING_RATING_FILED,
            Constants.MEDICINE_DISEASE_RATING_USER_ID,
            Constants.MEDICINE_DISEASE_RATING_ENTRY_ID);

    private final String getAll = "SELECT * FROM " + Constants.MEDICINE_DISEASE_RATING_TABLE;

    private final String getAllByRating = "SELECT * FROM " + Constants.MEDICINE_DISEASE_RATING_TABLE
            + " WHERE round(?) = " + Constants.MEDICINE_DISEASE_RATING_RATING_FILED;

    private final String getLastEntered = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT 1",
            Constants.MEDICINE_DISEASE_RATING_TABLE,
            Constants.MEDICINE_DISEASE_RATING_RATING_ID);

    private final String deleteQuery = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?",
            Constants.MEDICINE_DISEASE_RATING_TABLE,
            Constants.MEDICINE_DISEASE_RATING_USER_ID,
            Constants.MEDICINE_DISEASE_RATING_ENTRY_ID);

    private final String getAllByField = "SELECT * FROM %s WHERE %s = ?";

    public MedicineDiseaseRatingRepository(JdbcService service) throws SQLException {
        connection = service.getConnection();
    }

    @Override
    public MedicineDiseaseRating insert(MedicineDiseaseRating data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.insertQuery);
        statement.setString(1, data.getUserId());
        statement.setInt(2, data.getEntryId());
        statement.setDouble(3, data.getRating());
        if (statement.executeUpdate() == 1) {
            PreparedStatement inserted = connection.prepareStatement(this.getLastEntered);
            ResultSet set = inserted.executeQuery();
            if (set.next())
                return parse(set);
        }
        throw new DataInsertionException("Insertion of MedicineDiseaseRating for user id " + data.getUserId() +
                "and for disease " + data.getEntryId() + " failed");
    }

    @Override
    public MedicineDiseaseRating update(MedicineDiseaseRating data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.updateQuery);
        statement.setString(2, data.getUserId());
        statement.setInt(3, data.getEntryId());
        statement.setDouble(1, data.getRating());
        connection.setAutoCommit(false);
        connection.commit();
        if (statement.executeUpdate() == 1) {
            connection.commit();
            connection.setAutoCommit(true);
            return data;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new DataInsertionException("Updation of MedicineDiseaseRating for user id " + data.getUserId() +
                "and for disease " + data.getEntryId() + " failed");
    }

    @Override
    public MedicineDiseaseRating delete(MedicineDiseaseRating data) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.deleteQuery);
        statement.setString(1, data.getUserId());
        statement.setInt(2, data.getEntryId());
        connection.setAutoCommit(false);
        connection.commit();
        if (statement.executeUpdate() == 1) {
            connection.commit();
            connection.setAutoCommit(true);
            return data;
        }
        connection.rollback();
        connection.setAutoCommit(true);
        throw new DataInsertionException("Deletion of MedicineDiseaseRating for user id " + data.getUserId() +
                "and for disease " + data.getEntryId() + " failed");
    }

    @Override
    public MedicineDiseaseRating getById(Integer id) throws SQLException {
        List<MedicineDiseaseRating> _all = this.getAllByField(Constants.MEDICINE_DISEASE_RATING_RATING_ID, id);
        if (_all.size() == 0)
            throw new DataNotFoundException("MedicineDiseaseRating with id " + id + " is not found");
        return _all.get(0);
    }

    @Override
    public List<MedicineDiseaseRating> getAll() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(this.getAll);
        return processToList(statement);
    }

    @Override
    public List<MedicineDiseaseRating> getAllByField(String fieldName, Object value) throws SQLException {
        String query = String.format(this.getAllByField, Constants.MEDICINE_DISEASE_RATING_TABLE,fieldName);
        if(fieldName.equalsIgnoreCase(Constants.MEDICINE_DISEASE_RATING_RATING_FILED))
        {
            query = this.getAllByRating;
        }
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setObject(1, value);
        return processToList(statement);
    }

    private MedicineDiseaseRating parse(ResultSet set) throws SQLException {
        MedicineDiseaseRating rating = new MedicineDiseaseRating();
        rating.setRatingId(set.getInt(1));
        rating.setUserId(set.getString(2));
        rating.setEntryId(set.getInt(3));
        rating.setRating(set.getDouble(4));
        return rating;
    }

    private List<MedicineDiseaseRating> processToList(PreparedStatement statement) throws SQLException {
        List<MedicineDiseaseRating> _all = new ArrayList<>();
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            _all.add(parse(set));
        }
        return _all;
    }

}
