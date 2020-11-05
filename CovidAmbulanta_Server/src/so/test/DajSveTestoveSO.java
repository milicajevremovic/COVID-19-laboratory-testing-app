/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.test;

import domen.OpstiDomenskiObjekat;
import domen.Test;
import java.util.List;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class DajSveTestoveSO extends OpstaSistemskaOperacija{
    private List<OpstiDomenskiObjekat> list;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Test)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        list = databaseBroker.dajSve((Test)entity);
    }
    
    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
    
}
