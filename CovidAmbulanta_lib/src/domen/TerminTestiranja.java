/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Milica
 */
public class TerminTestiranja implements OpstiDomenskiObjekat{
    private Long terminTestID;
    private Date datum;
    private Laborant laborant;
    private Test test;
    private List<Pacijent> pacijenti;

    public TerminTestiranja() {
    }

    public TerminTestiranja(Long terminTestID, Date datum, Laborant laborant, Test test) {
        this.terminTestID = terminTestID;
        this.datum = datum;
        this.laborant = laborant;
        this.test = test;
        //pacijenti = new ArrayList<>();
    }

    /**
     * @return the terminTestID
     */
    public Long getTerminTestID() {
        return terminTestID;
    }

    /**
     * @param terminTestID the terminTestID to set
     */
    public void setTerminTestID(Long terminTestID) {
        this.terminTestID = terminTestID;
    }

    /**
     * @return the datum
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * @param datum the datum to set
     */
    public void setDatum(Date datum) {
        this.datum = datum;
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

    /**
     * @return the pacijenti
     
    public List<Pacijent> getPacijenti() {
        return pacijenti;
    }

    /**
     * @param pacijenti the pacijenti to set
     
    public void setPacijenti(List<Pacijent> pacijenti) {
        this.pacijenti = pacijenti;
    }
*/
    
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "vreme: "+sdf.format(datum) + ", testiranje vršio: " + laborant.getIme()+" "+laborant.getPrezime();
    }

    @Override
    public String getTableName() {
        return "terminTestiranja";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Date datum = new Date(resultSet.getDate("datum").getTime());
            Long laborantID = resultSet.getLong("laborantID");
            Laborant laborant = new Laborant();
            laborant.setLaborantID(laborantID);
            Long testID = resultSet.getLong("testID");
            Test test = new Test();
            test.setTestID(testID);

            TerminTestiranja terminTestiranja = new TerminTestiranja(id, datum, laborant, test);
            list.add(terminTestiranja);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat odo=null;
        if(resultSet.next()){
            Long id = resultSet.getLong("id");
            Date datum = new Date(resultSet.getDate("datum").getTime());
            Long laborantID = resultSet.getLong("laborantID");
            Laborant laborant = new Laborant();
            laborant.setLaborantID(laborantID);
            Long testID = resultSet.getLong("testID");
            Test test = new Test();
            test.setTestID(testID);
            
            odo = new TerminTestiranja(id, datum, laborant, test);
            return odo;
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "datum,laborantID,testID";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat odo) throws Exception {
        TerminTestiranja terminTestiranja = (TerminTestiranja)odo;
        ps.setDate(1, new java.sql.Date(terminTestiranja.getDatum().getTime()));
        ps.setLong(2, terminTestiranja.getLaborant().getLaborantID());
        ps.setLong(3, terminTestiranja.getTest().getTestID());
        
    }

    @Override
    public String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getID(OpstiDomenskiObjekat odo) {
        TerminTestiranja tt = (TerminTestiranja)odo;
        return String.valueOf(tt.getTerminTestID());
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getOrderCondition() {
        return "id";
    }

    /**
     * @return the pacijenti
     */
    public List<Pacijent> getPacijenti() {
        return pacijenti;
    }

    /**
     * @param pacijenti the pacijenti to set
     */
    public void setPacijenti(List<Pacijent> pacijenti) {
        this.pacijenti = pacijenti;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TerminTestiranja other = (TerminTestiranja) obj;
        if (!Objects.equals(this.terminTestID, other.terminTestID)) {
            return false;
        }
        return true;
    }
    
}
