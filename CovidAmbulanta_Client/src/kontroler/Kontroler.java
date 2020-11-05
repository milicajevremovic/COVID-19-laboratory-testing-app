/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;


import konstante.Operacije;
import domen.Laborant;
import domen.TerminTestiranja;
import domen.Test;
import domen.Rezultat;
import domen.Pacijent;
import domen.Administrator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import session.Session;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Milica
 */
public class Kontroler {
    private static Kontroler instance;
    private Administrator ulogovaniAdministrator;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfV;


    private Kontroler() {
       sdf = new SimpleDateFormat("dd.MM.yyyy");
       
    }
    
    public static Kontroler getInstance(){
        if(instance == null)
            instance = new Kontroler();
        return instance;
    }
public void initStreams() throws IOException
    {
        out=new ObjectOutputStream(Session.getInstance().getSocket().getOutputStream());
        in=new ObjectInputStream(Session.getInstance().getSocket().getInputStream());
        
    }

    
    public List<Pacijent> dajSvePacijente() throws Exception
    {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.DAJ_SVE_PACIJENTE);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Pacijent>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    
    public List<Laborant> dajSveLaborante() throws Exception
    {
       KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.DAJ_SVE_LABORANTE);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Laborant>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }
    
    public Administrator ulogujAdministratora(String username, String password) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.ULOGUJ_ADMINISTRATORA);
        kz.setData(new Administrator(null, username, password,"",""));
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Administrator) so.getParametar();
        } else {
            throw so.getGreska();
        }
        
    }

    public Long sacuvajPacijenta(Pacijent pacijent) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ_PACIJENTA);
        kz.setData(pacijent);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Long) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public Pacijent nadjiPacijenta(Long id) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.NADJI_PACIJENTA_ID);
        kz.setData(id);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Pacijent) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    
    public void azurirajPacijenta(Pacijent azuriraniPacijent) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.AZURIRAJ_PACIJENTA);
        kz.setData(azuriraniPacijent);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
           
        } else {
            throw so.getGreska();
        }
    }

    public void obrisiPacijenta(Pacijent pacijent) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.OBRISI_PACIJENTA);
        kz.setData(pacijent);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return;
        } else {
            throw so.getGreska();
        }
    }


    public List<Pacijent> filtrirajPacijente(Laborant laborant) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_LABORANT);
        kz.setData(laborant);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Pacijent>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public List<Pacijent> filtrirajPacijente(String ime, String prezime) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_IME_PREZIME);
        List<Object> list=new LinkedList();
        list.add(ime);
        list.add(prezime);
        kz.setData(list);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Pacijent>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }


   public void sacuvajSveVrednosti(List<Rezultat> vrednosti) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ_REZULTAT);
        kz.setData(vrednosti);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            
        } else {
            throw (Exception) so.getGreska();
        }
    }

     public Laborant nadjiLaborante(long id) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.NADJI_LABORANTA_ID);
        kz.setData(id);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Laborant) so.getParametar();
        } else {
            throw so.getGreska();
        }

    }
     
     public Test nadjiTest(long id) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.NADJI_TEST_ID);
        kz.setData(id);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Test) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }
     
     public List<Test> dajSveTestove() throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.DAJ_SVE_TESTOVE);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Test>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public void obrisiLaboranta(Laborant laborant) throws Exception {
        KlijentskiZahtev request = new KlijentskiZahtev();
        request.setOperacija(Operacije.OBRISI_LABORANTA);
        request.setData(laborant);
        out.writeObject(request);
        ServerskiOdgovor response = (ServerskiOdgovor) in.readObject();
        if(response.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (response.isUspesno() == true) {
            return;
        } else {
            throw response.getGreska();
        }
    }

    public void obrisiTest(Test test) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.OBRISI_TEST);
        kz.setData(test);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return;
        } else {
            throw so.getGreska();
        }
    }

    public List<Laborant> filtrirajLaborante(String prezimeLaboranta) throws Exception {
        Laborant laborant = new Laborant();
        laborant.setPrezime(prezimeLaboranta);
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.FILTRIRAJ_LABORANTE_IME);
        kz.setData(laborant);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Laborant>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public List<TerminTestiranja> dajSveTermineTestiranja() throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.DAJ_SVE_TERMINE_TESTIRANJA);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<TerminTestiranja>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public Long sacuvajLaboranta(Laborant laborant) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ_LABORANTA);
        kz.setData(laborant);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Long) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public Long sacuvajTerminTestiranja(TerminTestiranja termin) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ_TERMIN_TESTIRANJA);
        kz.setData(termin);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Long) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    public Long sacuvajTest(Test test) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.SACUVAJ_TEST);
        kz.setData(test);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (Long) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

    /**
     * @return the sdf
     */
    public SimpleDateFormat getSdf() {
        return sdf;
    }

    /**
     * @param sdf the sdf to set
     */
    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public void azurirajLaboranta(Laborant l) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.AZURIRAJ_LABORANTA);
        kz.setData(l);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            //exitApp();
            
        }
        if (so.isUspesno() == true) {
           
        } else {
            throw so.getGreska();
        }
    }

    private void exitApp() {
        System.out.println("Diskonektovanje klijenta");
        JOptionPane.showMessageDialog(null, "Izvinjavamo se server je prestao sa radom, program će se ugasiti, pokšajte ponovo kasnije");
        System.exit(0);
    }

    public List<Rezultat> filtrirajRezultate(TerminTestiranja termin) throws Exception {
        KlijentskiZahtev kz = new KlijentskiZahtev();
        kz.setOperacija(Operacije.FILTRIRAJ_REZULTATE_TERMIN);
        kz.setData(termin);
        out.writeObject(kz);
        ServerskiOdgovor so = (ServerskiOdgovor) in.readObject();
        if(so.getOperacija() == Operacije.DISKONEKTUJ_ADMINISTRATORA){
            exitApp();
        }
        if (so.isUspesno() == true) {
            return (List<Rezultat>) so.getParametar();
        } else {
            throw so.getGreska();
        }
    }

 

}
