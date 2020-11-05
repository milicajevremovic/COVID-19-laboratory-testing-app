/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Milica
 */
public class Administrator implements OpstiDomenskiObjekat{
    private Long adminID;
    private String username;
    private String password;
    private String ime;
    private String prezime;
    

    public Administrator() {
    }

    public Administrator(Long adminID, String username, String password, String ime, String prezime) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
        this.ime = ime;
        this.prezime = prezime;
    }


    /**
     * @return the adminID
     */
    public Long getAdminID() {
        return adminID;
    }

    /**
     * @param adminID the adminID to set
     */
    public void setAdminID(Long adminID) {
        this.adminID = adminID;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the ime
     */
    public String getIme() {
        return ime;
    }

    /**
     * @param ime the ime to set
     */
    public void setIme(String ime) {
        this.ime = ime;
    }

    /**
     * @return the prezime
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * @param prezime the prezime to set
     */
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public String getTableName() {
        return "administrator";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            Administrator a = new Administrator(id, username, password, ime, prezime);
            list.add(a);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat odo = null;
        if(resultSet.next()) {
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            odo = new Administrator(id, username, password, ime, prezime);
            return odo;
            
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "username,password,ime,prezime";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat odo) throws Exception {
        Administrator a = (Administrator) odo;
        ps.setString(1, a.getUsername());
        ps.setString(2, a.getPassword());
        ps.setString(3, a.getIme());
        ps.setString(4, a.getPrezime());
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID(OpstiDomenskiObjekat odo) {
        Administrator u = (Administrator) odo;
        return String.valueOf(u.getAdminID());
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOrderCondition() {
        return "username";
    }
    
    
}
