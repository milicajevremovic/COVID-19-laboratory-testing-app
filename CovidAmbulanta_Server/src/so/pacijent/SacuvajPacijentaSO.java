/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.pacijent;

import domen.OpstiDomenskiObjekat;
import domen.Pacijent;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class SacuvajPacijentaSO extends OpstaSistemskaOperacija{
    private Long id;
    
     @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Pacijent)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Pacijent pacijent=(Pacijent)entity;
        id = databaseBroker.sacuvaj(pacijent);
    }
    
    public Long getId() {
        return id;
    }
}
