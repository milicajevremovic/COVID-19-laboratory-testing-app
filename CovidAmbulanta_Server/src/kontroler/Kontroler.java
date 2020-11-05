/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Laborant;
import domen.TerminTestiranja;
import domen.OpstiDomenskiObjekat;
import domen.Test;
import domen.Rezultat;
import domen.Pacijent;
import domen.Administrator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.ServerNit;
import so.OpstaSistemskaOperacija;
import so.laborant.ObrisiLaborantaSO;
import so.laborant.FiltrirajLaboranteSO;
import so.laborant.DajSveLaboranteSO;
import so.laborant.NadjiLaborantaSO;
import so.laborant.SacuvajLaborantaSO;
import so.laborant.AzurirajLaborantaSO;
import so.termin.DajSveTermineTestiranjaSO;
import so.termin.SacuvajTerminTestiranjaSO;
import so.test.ObrisiTestSO;
import so.test.DajSveTestoveSO;
import so.test.NadjiTestSO;
import so.test.SacuvajTestSO;
import so.rezultat.SacuvajRezultatSO;
import so.pacijent.ObrisiPacijentaSO;
import so.pacijent.FiltrirajPacijenteSO;
import so.pacijent.DajSvePacijenteSO;
import so.pacijent.NadjiPacijentaSO;
import so.pacijent.SacuvajPacijentaSO;
import so.pacijent.AzurirajPacijentaSO;
import so.administrator.ObrisiAdministratoraSO;
import so.administrator.DajSveAdministratoreSO;
import so.administrator.UlogujAdministratoraSO;
import so.administrator.SacuvajAdministratoraSO;
import so.rezultat.FiltrirajRezultateSO;
import storage.IDBBroker;
import storage.database.DatabaseBroker;

/**
 *
 * @author Milica
 */
public class Kontroler {

    private static Kontroler instance;
    private final IDBBroker databaseBroker;
    private ServerNit server;

    private Kontroler() {
        databaseBroker = new DatabaseBroker();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public List<OpstiDomenskiObjekat> dajSvePacijente() throws Exception {
        OpstaSistemskaOperacija so = new DajSvePacijenteSO();
        so.templateExecute(new Pacijent());
        List<OpstiDomenskiObjekat> pacijenti = ((DajSvePacijenteSO) so).getList();
        for (OpstiDomenskiObjekat p : pacijenti) {
            Pacijent pacijent = (Pacijent) p;
            pacijent.setLaborant((Laborant) dajLaboranta(pacijent.getLaborant().getLaborantID()));
        }
        return pacijenti;
    }

    public List<OpstiDomenskiObjekat> dajSveLaborante() throws Exception {
        OpstaSistemskaOperacija so = new DajSveLaboranteSO();
        so.templateExecute(new Laborant());
        List<OpstiDomenskiObjekat> laboranti = ((DajSveLaboranteSO) so).getList();
        for (OpstiDomenskiObjekat l : laboranti) {
            Laborant laborant = (Laborant) l;
            laborant.setTest((Test) dajTest(laborant.getTest().getTestID()));
        }
        return laboranti;
    }

    public OpstiDomenskiObjekat ulogujAdministratora(String username, String password) throws Exception {

        Administrator administrator = new Administrator();
        administrator.setUsername(username);
        administrator.setPassword(password);
        OpstaSistemskaOperacija so = new UlogujAdministratoraSO();
        so.templateExecute(administrator);
        return ((UlogujAdministratoraSO) so).getResult();
    }

    public OpstiDomenskiObjekat dajPacijenta(long id) throws Exception {

        Pacijent pacijent = new Pacijent();
        pacijent.setPacijentID(id);
        OpstaSistemskaOperacija so = new NadjiPacijentaSO();
        so.templateExecute(pacijent);
        return ((NadjiPacijentaSO) so).getResult();
    }

    public OpstiDomenskiObjekat dajLaboranta(long id) throws Exception {

        Laborant laborant = new Laborant();
        laborant.setLaborantID(id);
        OpstaSistemskaOperacija so = new NadjiLaborantaSO();
        so.templateExecute(laborant);
        return ((NadjiLaborantaSO) so).getResult();

    }

    public OpstiDomenskiObjekat dajTest(Long id) throws Exception {

        Test test = new Test();
        test.setTestID(id);
        OpstaSistemskaOperacija so = new NadjiTestSO();
        so.templateExecute(test);
        return ((NadjiTestSO) so).getResult();
    }

    public Long sacuvajPacijenta(Pacijent pacijent) throws Exception {

        OpstaSistemskaOperacija so = new SacuvajPacijentaSO();
        so.templateExecute(pacijent);
        return ((SacuvajPacijentaSO) so).getId();
    }

    public void azurirajPacijenta(Pacijent azuriranPacijent) throws Exception {

        OpstaSistemskaOperacija so = new AzurirajPacijentaSO();
        so.templateExecute(azuriranPacijent);

    }

    public void obrisiPacijenta(Pacijent pacijent) throws Exception {
        OpstaSistemskaOperacija so = new ObrisiPacijentaSO();
        so.templateExecute(pacijent);
    }

    public void sacuvajSveRezultate(List<Rezultat> rezultati) throws Exception {
        OpstaSistemskaOperacija so = new SacuvajRezultatSO();
        so.templateExecute(rezultati);
    }

    public List<OpstiDomenskiObjekat> filtrirajPacijente(Laborant laborant) throws Exception {

        Pacijent pacijent = new Pacijent();
        pacijent.setLaborant(laborant);
        OpstaSistemskaOperacija so = new FiltrirajPacijenteSO();
        so.templateExecute(pacijent);
        List<OpstiDomenskiObjekat> pacijents = ((FiltrirajPacijenteSO) so).getList();
        for (OpstiDomenskiObjekat p : pacijents) {
            Pacijent pac = (Pacijent) p;
            pac.setLaborant(laborant);
        }
        return pacijents;

    }

    public List<OpstiDomenskiObjekat> filtrirajPacijente(String ime, String prezime) throws Exception {

        Pacijent pacijent = new Pacijent();
        pacijent.setIme(ime);
        pacijent.setPrezime(prezime);
        OpstaSistemskaOperacija so = new FiltrirajPacijenteSO();
        so.templateExecute(pacijent);
        List<OpstiDomenskiObjekat> pacijenti = ((FiltrirajPacijenteSO) so).getList();
        for (OpstiDomenskiObjekat p : pacijenti) {
            Pacijent pac = (Pacijent) p;
            pac.setLaborant((Laborant) dajLaboranta(pac.getLaborant().getLaborantID()));
        }
        return pacijenti;
    }

    public List<OpstiDomenskiObjekat> dajSveTestove() throws Exception {
        OpstaSistemskaOperacija so = new DajSveTestoveSO();
        so.templateExecute(new Test());
        return ((DajSveTestoveSO) so).getList();
    }

    public void obrisiLaboranta(Laborant laborant) throws Exception {
        OpstaSistemskaOperacija so = new ObrisiLaborantaSO();
        so.templateExecute(laborant);
    }

    public void obrisiTest(Test test) throws Exception {
        OpstaSistemskaOperacija so = new ObrisiTestSO();
        so.templateExecute(test);
    }

    public List<OpstiDomenskiObjekat> filtrirajLaborante(String prezimeLaboranta) throws Exception {
        Laborant laborant = new Laborant();
        laborant.setPrezime(prezimeLaboranta);
        OpstaSistemskaOperacija so = new FiltrirajLaboranteSO();
        so.templateExecute(laborant);
        List<OpstiDomenskiObjekat> laboranti = ((FiltrirajLaboranteSO) so).getList();
        for (OpstiDomenskiObjekat l : laboranti) {
            Laborant la = (Laborant) l;
            la.setTest((Test) dajTest(la.getTest().getTestID()));
        }
        return laboranti;
    }

    public List<OpstiDomenskiObjekat> dajSveTermineTestiranja() throws Exception {
        OpstaSistemskaOperacija so = new DajSveTermineTestiranjaSO();
        so.templateExecute(new TerminTestiranja());
        List<OpstiDomenskiObjekat> termini = ((DajSveTermineTestiranjaSO) so).getList();
        for (OpstiDomenskiObjekat t : termini) {
            TerminTestiranja termin = (TerminTestiranja) t;
            termin.setLaborant((Laborant) dajLaboranta(termin.getLaborant().getLaborantID()));
            termin.setTest((Test) dajTest(termin.getTest().getTestID()));
        }
        return termini;
    }

    public Long sacuvajLaboranta(Laborant laborant) throws Exception {
        OpstaSistemskaOperacija so = new SacuvajLaborantaSO();
        so.templateExecute(laborant);
        return ((SacuvajLaborantaSO) so).getId();
    }

    public Long sacuvajTerminTestiranja(TerminTestiranja termin) throws Exception {
        OpstaSistemskaOperacija so = new SacuvajTerminTestiranjaSO();
        so.templateExecute(termin);
        return ((SacuvajTerminTestiranjaSO) so).getId();
    }

    public Long sacuvajTest(Test test) throws Exception {
        OpstaSistemskaOperacija so = new SacuvajTestSO();
        so.templateExecute(test);
        return ((SacuvajTestSO) so).getId();
    }

    public void azurirajLaboranta(Laborant laborant) throws Exception {
        OpstaSistemskaOperacija so = new AzurirajLaborantaSO();
        so.templateExecute(laborant);
    }

    public Long sacuvajAdministratora(Administrator a) throws Exception {
        OpstaSistemskaOperacija so = new SacuvajAdministratoraSO();
        so.templateExecute(a);
        return ((SacuvajAdministratoraSO) so).getId();
    }

    public List<OpstiDomenskiObjekat> dajSveAdministratore() throws Exception {
        OpstaSistemskaOperacija so = new DajSveAdministratoreSO();
        so.templateExecute(new Administrator());
        return ((DajSveAdministratoreSO) so).getList();
    }

    public void startServer() {
        if (server == null || !server.isAlive()) {
        server = new ServerNit();
        server.start();
        }
    }

    public void stopServer() {
        server.stopServer();
    }

    public void obrisiAdministratora(Administrator a) throws Exception {
        OpstaSistemskaOperacija so = new ObrisiAdministratoraSO();
        so.templateExecute(a);
    }

    public List<OpstiDomenskiObjekat> filtrirajRezultate(TerminTestiranja tt) throws Exception{
        Rezultat rezultat = new Rezultat();
        rezultat.setTerminTestiranja(tt);
        OpstaSistemskaOperacija so = new FiltrirajRezultateSO();
        so.templateExecute(rezultat);
        List<OpstiDomenskiObjekat> rezultati = ((FiltrirajRezultateSO) so).getList();
        for (OpstiDomenskiObjekat r : rezultati) {
            Rezultat rez = (Rezultat) r;
            rez.setTerminTestiranja(tt);
        }
        return rezultati;
    }

}
