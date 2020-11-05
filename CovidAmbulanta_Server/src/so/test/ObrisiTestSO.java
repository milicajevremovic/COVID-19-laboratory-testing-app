/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.test;

import domen.Test;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class ObrisiTestSO extends OpstaSistemskaOperacija{

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Test)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Test test =(Test)entity;
        databaseBroker.obrisi(test);
    }
    
}
