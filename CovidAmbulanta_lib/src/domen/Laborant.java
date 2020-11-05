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
public class Laborant implements OpstiDomenskiObjekat{
    private Long laborantID;
    private String ime;
    private String prezime;
    private String brojDosijea;
    private double brojOrdinacije;
    private Test test;

    public Laborant() {
    }

    public Laborant(Long laborantID, String ime, String prezime, String brojDosijea, double brojOrdinacije, Test test) {
        this.laborantID = laborantID;
        this.ime = ime;
        this.prezime = prezime;
        this.brojDosijea = brojDosijea;
        this.brojOrdinacije = brojOrdinacije;
        this.test = test;
    }

    /**
     * @return the laborantID
     */
    public Long getLaborantID() {
        return laborantID;
    }

    /**
     * @param laborantID the laborantID to set
     */
    public void setLaborantID(Long laborantID) {
        this.laborantID = laborantID;
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
     * @return the brojDosijea
     */
    public String getBrojDosijea() {
        return brojDosijea;
    }

    /**
     * @param brojDosijea the brojDosijea to set
     */
    public void setBrojDosijea(String brojDosijea) {
        this.brojDosijea = brojDosijea;
    }

    /**
     * @return the brojOrdinacije
     */
    public double getBrojOrdinacije() {
        return brojOrdinacije;
    }

    /**
     * @param brojOrdinacije the brojOrdinacije to set
     */
    public void setBrojOrdinacije(double brojOrdinacije){
        this.brojOrdinacije = brojOrdinacije;
    }

    @Override
    public String toString() {
        return test+" vrši: "+ime+", dosije: "+brojDosijea+"";
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
        final Laborant other = (Laborant) obj;
        if (this.laborantID != other.laborantID) {
            return false;
        }
        return true;
    }

    /**
     * @return the test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(Test test) {
        this.test = test;
    }

    
    @Override
    public String getTableName() {
        return "laborant";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            String brojDosijea = resultSet.getString("brojDosijea");
            Double brojOrdinacije = resultSet.getDouble("brojOrdinacije");
            Long testID = resultSet.getLong("testID");
            Test t = new Test();
            t.setTestID(testID);

            Laborant laborant = new Laborant(id, ime, prezime, brojDosijea, brojOrdinacije, t);
      
            list.add(laborant);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat odo=null;
        if(resultSet.next()){
            Long id = resultSet.getLong("id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            String brojDosijea = resultSet.getString("brojDosijea");
            Double brojOrdinacije = resultSet.getDouble("brojOrdinacije");
            Long testID = resultSet.getLong("testID");
            Test t = new Test();
            
            t.setTestID(testID);

            odo = new Laborant(id, ime, prezime, brojDosijea, brojOrdinacije, t);
            return odo;
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "ime,prezime,brojDosijea,brojOrdinacije,testID";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat entity) throws Exception {
        Laborant laborant = (Laborant) entity;
        ps.setString(1, laborant.getIme());
        ps.setString(2, laborant.getPrezime());
        ps.setString(3, laborant.getBrojDosijea());
        ps.setDouble(4, laborant.getBrojOrdinacije());
        ps.setLong(5, laborant.getTest().getTestID());
    }

    @Override
    public String getUpdateQuery() {
        return "ime=?,prezime=?,brojDosijea=?,brojOrdinacije=?,testID=?";
    }

    @Override
    public String getID(OpstiDomenskiObjekat odo) {
        Laborant laborant = (Laborant)odo;
        return String.valueOf(laborant.getLaborantID());
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat odo) {
        Laborant laborant = (Laborant) odo;
        String prezime="";
        if(laborant.getPrezime() != null){
            prezime = laborant.getPrezime();
        }
        return "prezime LIKE '"+prezime+"%'";
    }

    @Override
    public String getOrderCondition() {
        return "prezime";
    }
    
    
}
