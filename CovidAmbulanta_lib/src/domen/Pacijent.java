/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Milica
 */
public class Pacijent implements OpstiDomenskiObjekat{
    private Long pacijentID;
    private String ime;
    private String prezime;
    private Date datumRodjenja;
    private String telefon;
    private String email;
    private Laborant laborant;
    

    public Pacijent() {
    }

    public Pacijent(Long pacijentID, String ime, String prezime, Date datumRodjenja, String telefon, String email) {
        this.pacijentID = pacijentID;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.telefon = telefon;
        this.email = email;
    }

    /**
     * @return the pacijentID
     */
    public Long getPacijentID() {
        return pacijentID;
    }

    /**
     * @param pacijentID the pacijentID to set
     */
    public void setPacijentID(Long pacijentID) {
        this.pacijentID = pacijentID;
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

    /**
     * @return the datumRodjenja
     */
    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    /**
     * @param datumRodjenja the datumRodjenja to set
     */
    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    /**
     * @return the telefon
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * @param telefon the telefon to set
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return ime+" "+prezime;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pacijent other = (Pacijent) obj;
        if (this.pacijentID != other.pacijentID) {
            return false;
        }
        return true;
    }

    /**
     * @return the laborant
     */
    public Laborant getLaborant() {
        return laborant;
    }

    /**
     * @param laborant the laborant to set
     */
    public void setLaborant(Laborant laborant) {
        this.laborant = laborant;
    }

    @Override
    public String getTableName() {
        return "pacijent";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            Date datumRodjenja = new Date(resultSet.getDate("datumRodjenja").getTime());
            String telefon = resultSet.getString("telefon");
            String email = resultSet.getString("email");
            Long laborantID = resultSet.getLong("laborantID");
            Laborant laborant = new Laborant();
            laborant.setLaborantID(laborantID);
            
            Pacijent p = new Pacijent(id, ime, prezime, datumRodjenja, telefon, email);
            p.setLaborant(laborant);
            list.add(p);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat odo = null;
        if(resultSet.next()) {
            Long id = resultSet.getLong("id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            Date datumRodjenja = new Date(resultSet.getDate("datumRodjenja").getTime());
            String telefon = resultSet.getString("telefon");
            String email = resultSet.getString("email");
            Long laborantID = resultSet.getLong("laborantID");
            Laborant laborant = new Laborant();
            
            laborant.setLaborantID(laborantID);
            
            odo = new Pacijent(id, ime, prezime, datumRodjenja, telefon, email);
            return odo;
            
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "ime,prezime,datumRodjenja,telefon,email,laborantID";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?,?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat odo) throws Exception {
        Pacijent p = (Pacijent) odo;
        ps.setString(1, p.getIme());
        ps.setString(2, p.getPrezime());
        ps.setDate(3, new java.sql.Date(p.getDatumRodjenja().getTime()));
        ps.setString(4, p.getTelefon());
        ps.setString(5, p.getEmail());
        ps.setLong(6, p.getLaborant().getLaborantID());
    }

    @Override
    public String getUpdateQuery() {
        return "ime=?,prezime=?,datumRodjenja=?,telefon=?,email=?,laborantID=?";
    }

    @Override
    public String getID(OpstiDomenskiObjekat odo) {
        Pacijent p = (Pacijent) odo;
        return String.valueOf(p.getPacijentID());
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat odo) {
        Pacijent pacijent=(Pacijent)odo;
        String ime = "";
        String prezime = "";
        if (pacijent.getIme()!= null) {
            ime = pacijent.getIme();
        }
        if (pacijent.getPrezime()!= null) {
            prezime = pacijent.getPrezime();
        }
        if(pacijent.getLaborant()==null)
        {
           return "ime LIKE '"+ime+"%' AND prezime LIKE '"+prezime+"%'";
        }
        else
        {
            return "laborantID="+pacijent.getLaborant().getLaborantID();
        }
    }

    @Override
    public String getOrderCondition() {
        return "ime";
    }
    
    
    
}
