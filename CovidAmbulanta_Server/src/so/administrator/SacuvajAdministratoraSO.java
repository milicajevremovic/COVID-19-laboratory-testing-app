/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.administrator;

import domen.Administrator;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class SacuvajAdministratoraSO extends OpstaSistemskaOperacija{
    private Long id;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Administrator)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Administrator a =(Administrator)entity;
        id = databaseBroker.sacuvaj(a);
    }
    
    public Long getId() {
        return id;
    }
}
