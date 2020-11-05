/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.laborant;

import domen.OpstiDomenskiObjekat;
import domen.Laborant;
import java.util.List;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class DajSveLaboranteSO extends OpstaSistemskaOperacija{

    private List<OpstiDomenskiObjekat> list;
    
    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Laborant)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        list = databaseBroker.dajSve((Laborant) entity);
    }
    
    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
    
}
