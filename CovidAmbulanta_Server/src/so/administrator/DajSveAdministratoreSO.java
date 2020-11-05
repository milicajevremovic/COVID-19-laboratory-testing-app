/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.administrator;


import domen.OpstiDomenskiObjekat;
import domen.Administrator;
import java.util.List;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class DajSveAdministratoreSO extends OpstaSistemskaOperacija{

    private List<OpstiDomenskiObjekat> list;
    
    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Administrator)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        list = databaseBroker.dajSve((Administrator) entity);
    }
    
    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
    
}
