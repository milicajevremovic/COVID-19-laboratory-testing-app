/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.laborant;

import domen.Laborant;
import so.OpstaSistemskaOperacija;

/**
 *
 * @author Milica
 */
public class AzurirajLaborantaSO extends OpstaSistemskaOperacija{
    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Laborant)) {
            throw new Exception("Invalid entity parameter!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Laborant laborant=(Laborant)entity;
        databaseBroker.azuriraj(laborant);
    }
}
