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
import java.util.Objects;

/**
 *
 * @author Milica
 */
public class Test implements OpstiDomenskiObjekat{
    private Long testID;
    private String naziv;
    private String opis;
    private String uputstvoZaPrimenu;
    private Vrsta vrsta;
    private String oznaka;
    private List<Laborant> laboranti;
    

    public Test() {
    }

    public Test(Long testID, String naziv, String opis, String uputstvoZaPrimenu, Vrsta vrsta, String oznaka) {
        this.testID = testID;
        this.naziv = naziv;
        this.opis = opis;
        this.uputstvoZaPrimenu = uputstvoZaPrimenu;
        this.vrsta = vrsta;
        this.oznaka = oznaka;
    }

    /**
     * @return the profesorID
     */
    public Long getTestID() {
        return testID;
    }

    /**
     * @param profesorID the profesorID to set
     */
    public void setTestID(Long profesorID) {
        this.testID = profesorID;
    }

    /**
     * @return the naziv
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * @param naziv the naziv to set
     */
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    /**
     * @return the opis
     */
    public String getOpis() {
        return opis;
    }

    /**
     * @param opis the opis to set
     */
    public void setOpis(String opis) {
        this.opis = opis;
    }

    /**
     * @return the uputstvoZaPrimenu
     */
    public String getUputstvoZaPrimenu() {
        return uputstvoZaPrimenu;
    }

    /**
     * @param uputstvoZaPrimenu the uputstvoZaPrimenu to set
     */
    public void setUputstvoZaPrimenu(String uputstvoZaPrimenu) {
        this.uputstvoZaPrimenu = uputstvoZaPrimenu;
    }

    /**
     * @return the vrsta
     */
    public Vrsta getVrsta() {
        return vrsta;
    }

    /**
     * @param vrsta the vrsta to set
     */
    public void setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
    }

    /**
     * @return the oznaka
     */
    public String getOznaka() {
        return oznaka;
    }

    /**
     * @param oznaka the oznaka to set
     */
    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    /**
     * @return the laboranti
     */
    public List<Laborant> getLaboranti() {
        return laboranti;
    }

    /**
     * @param laboranti the laboranti to set
     */
    public void setLaboranti(List<Laborant> laboranti) {
        this.laboranti = laboranti;
    }

    @Override
    public String toString() {
        return naziv+" - "+opis;
    }

    
    @Override
    public String getTableName() {
        return "test";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            String opis = resultSet.getString("opis");
            String uputstvoZaPrimenu = resultSet.getString("uputstvoZaPrimenu");
            Vrsta vrsta = Vrsta.valueOf(resultSet.getString("vrsta"));
            String oznaka = resultSet.getString("oznaka");
            

            Test t = new Test(id, naziv, opis, uputstvoZaPrimenu, vrsta, oznaka);
            list.add(t);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat generalEntity=null;
        if(resultSet.next()){
            Long id = resultSet.getLong("id");
            String naziv = resultSet.getString("naziv");
            String opis = resultSet.getString("opis");
            String upustvoZaPrimenu = resultSet.getString("uputstvoZaPrimenu");
            Vrsta vrsta = Vrsta.valueOf(resultSet.getString("vrsta"));
            String oznaka = resultSet.getString("oznaka");
            

            generalEntity = new Test(id, naziv, opis, upustvoZaPrimenu, vrsta, oznaka);
            return generalEntity;
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "naziv,opis,uputstvoZaPrimenu,vrsta,oznaka";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat odo) throws Exception {
        Test t = (Test) odo;
        ps.setString(1, t.getNaziv());
        ps.setString(2, t.getOpis());
        ps.setString(3, t.getUputstvoZaPrimenu());
        ps.setString(4, t.getVrsta().toString());
        ps.setString(5, t.getOznaka());
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID(OpstiDomenskiObjekat odo) {
        Test t = (Test) odo;
        return String.valueOf(t.getTestID());
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOrderCondition() {
        return "naziv";
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Test other = (Test) obj;
        if (!Objects.equals(this.testID, other.testID)) {
            return false;
        }
        return true;
    }

    
    

}
