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
import java.util.Objects;

/**
 *
 * @author Milica
 */
public class Rezultat implements OpstiDomenskiObjekat{
    private Pacijent pacijent;
    private TerminTestiranja terminTestiranja;
    private int vrednost = -1;
    int rezultat;
    private boolean novi;

    public Rezultat() {
    }

    public Rezultat(Pacijent pacijent, TerminTestiranja terminTestiranja) {
        this.pacijent = pacijent;
        this.terminTestiranja = terminTestiranja;
    }
    
    public Rezultat(Pacijent pacijent, TerminTestiranja termin, int vrednost){
        this.pacijent = pacijent;
        this.terminTestiranja = termin;
        this.vrednost = vrednost;
    }

    /**
     * @return the pacijent
     */
    public Pacijent getPacijent() {
        return pacijent;
    }

    /**
     * @param pacijent the pacijent to set
     */
    public void setPacijent(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    /**
     * @return the terminTestiranja
     */
    public TerminTestiranja getTerminTestiranja() {
        return terminTestiranja;
    }

    /**
     * @param terminTestiranja the terminTestiranja to set
     */
    public void setTerminTestiranja(TerminTestiranja terminTestiranja) {
        this.terminTestiranja = terminTestiranja;
    }

    /**
     * @return the vrednost
     */
    public int getVrednost() {
        return vrednost;
    }

    /**
     * @param vrednost the vrednost to set
     */
    public void setVrednost(int vrednost) {
        this.vrednost = vrednost;
    }

    @Override
    public String toString() {
        return pacijent+" "+terminTestiranja+" "+rezultat;
    }

    @Override
    public String getTableName() {
        return "rezultat";
    }

    @Override
    public List<OpstiDomenskiObjekat> getList(ResultSet resultSet) throws Exception {
        List<OpstiDomenskiObjekat> list = new LinkedList<>();
        while (resultSet.next()) {
            Long ID = resultSet.getLong("pacijentID");
            Pacijent p = new Pacijent();
            p.setPacijentID(ID);
            Long terminTestID = resultSet.getLong("terminTestID");
            TerminTestiranja tt = new TerminTestiranja();
            tt.setTerminTestID(terminTestID);
            int vrednost = resultSet.getInt("vrednost");
            System.out.println("vredsnot je " + vrednost);

            Rezultat rezultat = new Rezultat(p, tt);
            rezultat.setVrednost(vrednost);
            
            list.add(rezultat);
            
        }
        return list;
    }

    @Override
    public OpstiDomenskiObjekat getResult(ResultSet resultSet) throws Exception {
        OpstiDomenskiObjekat odo=null;
        if(resultSet.next()){
            Long ID = resultSet.getLong("pacijentID");
            Pacijent p = new Pacijent();
            p.setPacijentID(ID);
            Long terminTestID = resultSet.getLong("terminTestID");
            TerminTestiranja tt = new TerminTestiranja();
            tt.setTerminTestID(terminTestID);
            int vrednost = resultSet.getInt("vrednost");

            odo = new Rezultat(p, tt);
            
            return odo;
        }
        return null;
    }

    @Override
    public String getAttributeNames() {
        return "pacijentID,terminTestID,vrednost";
    }

    @Override
    public String getUnknownValues() {
        return "?,?,?";
    }

    @Override
    public void prepareStatement(PreparedStatement ps, OpstiDomenskiObjekat odo) throws Exception {
        Rezultat r = (Rezultat) odo;
        ps.setLong(1, r.getPacijent().getPacijentID());
        ps.setLong(2, r.getTerminTestiranja().getTerminTestID());
        ps.setInt(3, r.getVrednost());
    }

    @Override
    public String getUpdateQuery() {
        return "vrednost=?";
    }

    @Override
    public String getID(OpstiDomenskiObjekat entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCondition(OpstiDomenskiObjekat entity) {
        return "terminTestID="+terminTestiranja.getTerminTestID();
    }

    @Override
    public String getOrderCondition() {
        return "vrednost";
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Rezultat other = (Rezultat) obj;
        if (!Objects.equals(this.pacijent, other.pacijent)) {
            return false;
        }
        if (!Objects.equals(this.terminTestiranja, other.terminTestiranja)) {
            return false;
        }
        return true;
    }

    public boolean isNovi() {
        return novi;
    }

    public void setNovi(boolean novi) {
        this.novi = novi;
    }

    
    
    
}
