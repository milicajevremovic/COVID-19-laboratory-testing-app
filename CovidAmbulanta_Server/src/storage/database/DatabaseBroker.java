/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage.database;


import domen.OpstiDomenskiObjekat;
import domen.Rezultat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import storage.IDBBroker;
import storage.database.connection.DatabaseConnection;

/**
 *
 * @author Milica
 */
public class DatabaseBroker implements IDBBroker{

    @Override
    public List<OpstiDomenskiObjekat> dajSve(OpstiDomenskiObjekat odo) throws Exception {
        List<OpstiDomenskiObjekat> list = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName()+" ORDER BY "+odo.getOrderCondition();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = odo.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    @Override
    public OpstiDomenskiObjekat nadji(OpstiDomenskiObjekat odo, Long id) throws Exception {
        OpstiDomenskiObjekat generalEntity=null ;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName()+" WHERE id="+id;
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            generalEntity=odo.getResult(resultSet);
            resultSet.close();
            statement.close();
            return generalEntity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public Long sacuvaj(OpstiDomenskiObjekat odo) throws Exception {
        ResultSet resultSet;
        Long id=null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO " + odo.getTableName()+" ("+odo.getAttributeNames()+") VALUES("+odo.getUnknownValues()+")";
            //System.out.println(query);
            PreparedStatement ps=DatabaseConnection.getInstance().getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            odo.prepareStatement(ps,odo);
            int updatedRow = ps.executeUpdate();
            if (updatedRow == 1) {
                resultSet = ps.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getLong(1);
            }
            ps.close(); 
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void azuriraj(OpstiDomenskiObjekat odo) throws Exception {
        try {
            String query = "UPDATE " + odo.getTableName() + " SET " + odo.getUpdateQuery() + " WHERE id=" + odo.getID(odo);
            //System.out.println(query);
            PreparedStatement ps = DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            odo.prepareStatement(ps, odo);
            int updatedRow = ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                DatabaseConnection.getInstance().getConnection().rollback();
            }
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void obrisi(OpstiDomenskiObjekat odo) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "DELETE FROM " + odo.getTableName() + " WHERE id=" + odo.getID(odo);
            Statement statement = connection.createStatement();
            //System.out.println(query);
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception ex) {
            if (DatabaseConnection.getInstance().getConnection() != null) {
                DatabaseConnection.getInstance().getConnection().rollback();
            }
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public OpstiDomenskiObjekat uloguj(OpstiDomenskiObjekat odo, String username, String password) throws Exception {
        OpstiDomenskiObjekat generalEntity=null ;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName()+" WHERE username='"+username+"' AND password='"+password+"'";
//          System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            generalEntity=odo.getResult(resultSet);
            resultSet.close();
            statement.close();
            return generalEntity;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> filtriraj(OpstiDomenskiObjekat odo) throws Exception {
        List<OpstiDomenskiObjekat> list = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT * FROM " + odo.getTableName()+" WHERE "+odo.getCondition(odo)+" ORDER BY "+odo.getOrderCondition();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = odo.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void sacuvajR(Rezultat rezultat) throws Exception {
        ResultSet resultSet;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "INSERT INTO " + rezultat.getTableName()+" ("+rezultat.getAttributeNames()+") VALUES("+rezultat.getUnknownValues()+")";
            //System.out.println(query);
            PreparedStatement ps=DatabaseConnection.getInstance().getConnection().prepareStatement(query);
            rezultat.prepareStatement(ps,rezultat);
            int updatedRow = ps.executeUpdate();
            ps.close(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    
    
}
