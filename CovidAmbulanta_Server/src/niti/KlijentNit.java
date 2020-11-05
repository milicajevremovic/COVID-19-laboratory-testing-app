/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import konstante.Operacije;
import kontroler.Kontroler;
import domen.Laborant;
import domen.TerminTestiranja;
import domen.OpstiDomenskiObjekat;
import domen.Test;
import domen.Rezultat;
import domen.Pacijent;
import domen.Administrator;
import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;


/**
 *
 * @author Milica
 */
public class KlijentNit extends Thread{
     private ServerNit server;
     private Socket socket;
     private ObjectOutputStream out;
     private ObjectInputStream in;
     private Administrator administrator;



    KlijentNit(Socket socket, ServerNit aThis) throws IOException {      
        this.socket = socket;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.server=aThis;
    }
   

    @Override
    public void run() {
        try {
            komunikacija();
        } catch (Exception ex) {
            if (administrator != null) {
                System.out.println("Administrator: " + administrator + " se odjavio/la sa sistema.");
                server.getKlijenti().remove(this);
            }else {
                    System.out.println("Administrator je odustao od logovanja.");
                }
            }
            //Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
            //Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
    }

    private void komunikacija() throws Exception {

        while (!isInterrupted()) {
            Object object = in.readObject();
            if (object instanceof KlijentskiZahtev) {
                KlijentskiZahtev kz = (KlijentskiZahtev) object;
                ServerskiOdgovor so = obradiZahtev(kz);
                posaljiOdgovor(socket, so);
            }      
        }
    }

    private ServerskiOdgovor obradiZahtev(KlijentskiZahtev kz) {
        int operacija = kz.getOperacija();
        ServerskiOdgovor response = new ServerskiOdgovor();

        switch (operacija) {
            case Operacije.ULOGUJ_ADMINISTRATORA:
                Administrator a = (Administrator) kz.getData();
                 {
                    try {
                         OpstiDomenskiObjekat odo = Kontroler.getInstance().ulogujAdministratora(a.getUsername(), a.getPassword());
                         if (odo != null) {
                            Administrator administrator = (Administrator) odo;
                            boolean exists = false;
                            for (KlijentNit kn : server.getKlijenti()) {
                                if (kn.getAdministrator() != null && kn.getAdministrator().getUsername().equals(administrator.getUsername()) && kn.getAdministrator().getPassword().equals(administrator.getPassword())) {
                                    exists = true;
                                    break;
                                }
                            }
                             if (!exists) {
                                 server.getKlijenti().add(this);
                                 this.administrator = (Administrator) odo;
                                 System.out.println("Administrator: " + odo + " je pristupio/la sistemu.");
                                 response.setUspesno(true);
                                 response.setParametar(odo);
                                 response.setOperacija(Operacije.ULOGUJ_ADMINISTRATORA);
                            }
                            else
                            {
                                Exception e = new Exception("Administrator sa tim korisnickim imenom je vec ulogovan!");
                                response.setUspesno(false);
                                response.setOperacija(Operacije.ULOGUJ_ADMINISTRATORA);
                                response.setGreska(e);
                            }
                        }else{
                        response.setUspesno(true);
                        response.setParametar(odo);
                        response.setOperacija(Operacije.ULOGUJ_ADMINISTRATORA);
                         }
                         
                         

                     } catch (Exception ex) {
                         Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                         response.setUspesno(false);
                         response.setOperacija(Operacije.ULOGUJ_ADMINISTRATORA);
                         response.setGreska(ex);
                     }
                }
                break;
            
            case Operacije.DAJ_SVE_LABORANTE: {
                try {
                    List<OpstiDomenskiObjekat> laboranti = Kontroler.getInstance().dajSveLaborante();
                    response.setUspesno(true);
                    response.setParametar(laboranti);
                    response.setOperacija(Operacije.DAJ_SVE_LABORANTE);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setUspesno(false);
                    response.setOperacija(Operacije.DAJ_SVE_LABORANTE);
                    response.setGreska(ex);
                }
            }
            break;
            case Operacije.FILTRIRAJ_PACIJENTE_LABORANT:
                Laborant l = (Laborant) kz.getData();
                 {
                    try {
                        List<OpstiDomenskiObjekat> pacijenti = Kontroler.getInstance().filtrirajPacijente(l);
                        response.setUspesno(true);
                        response.setParametar(pacijenti);
                        response.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_LABORANT);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_LABORANT);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.DAJ_SVE_TESTOVE: {
                try {
                    List<OpstiDomenskiObjekat> testovi = Kontroler.getInstance().dajSveTestove();
                    response.setUspesno(true);
                    response.setParametar(testovi);
                    response.setOperacija(Operacije.DAJ_SVE_TESTOVE);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setUspesno(false);
                    response.setOperacija(Operacije.DAJ_SVE_TESTOVE);
                    response.setGreska(ex);
                }
            }
            break;
            case Operacije.SACUVAJ_PACIJENTA:
                Pacijent p = (Pacijent) kz.getData();
                System.out.println("tu sam");
                 {
                    try {
                        Long id = Kontroler.getInstance().sacuvajPacijenta(p);
                        response.setUspesno(true);
                        response.setOperacija(Operacije.SACUVAJ_PACIJENTA);
                        response.setParametar(id);
                        System.out.println("novi pacijent try");
                    } catch (Exception ex) {
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.SACUVAJ_PACIJENTA);
                        response.setGreska(ex);
                        System.out.println("novi pacijent catch");
                    }
                }
                break;
            case Operacije.NADJI_PACIJENTA_ID:
                Long pacijentID = (Long) kz.getData();
                 {
                    try {
                        Pacijent pacijent = (Pacijent) Kontroler.getInstance().dajPacijenta(pacijentID);
                        response.setUspesno(true);
                        response.setParametar(pacijent);
                        response.setOperacija(Operacije.NADJI_PACIJENTA_ID);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.NADJI_PACIJENTA_ID);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.OBRISI_PACIJENTA:
                Pacijent pacijent = (Pacijent) kz.getData();
                 {
                    try {
                        Kontroler.getInstance().obrisiPacijenta(pacijent);
                        response.setUspesno(true);
                        response.setOperacija(Operacije.OBRISI_PACIJENTA);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.OBRISI_PACIJENTA);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.AZURIRAJ_PACIJENTA:
                Pacijent azuriraniPacijent = (Pacijent) kz.getData();
                 {
                    try {
                        Kontroler.getInstance().azurirajPacijenta(azuriraniPacijent);
                        response.setUspesno(true);
                        response.setOperacija(Operacije.AZURIRAJ_PACIJENTA);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.AZURIRAJ_PACIJENTA);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.FILTRIRAJ_PACIJENTE_IME_PREZIME:
                List<Object> requestList = (List<Object>) kz.getData();
                String ime = (String) requestList.get(0);
                String prezime = (String) requestList.get(1);
                 {
                    try {
                        List<OpstiDomenskiObjekat> pacijenti = Kontroler.getInstance().filtrirajPacijente(ime, prezime);
                        response.setUspesno(true);
                        response.setParametar(pacijenti);
                        response.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_IME_PREZIME);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setGreska(ex);
                        response.setOperacija(Operacije.FILTRIRAJ_PACIJENTE_IME_PREZIME);
                    }
                }
                break;
            case Operacije.DAJ_SVE_PACIJENTE: {
                try {
                    List<OpstiDomenskiObjekat> pacijenti = Kontroler.getInstance().dajSvePacijente();
                    response.setUspesno(true);
                    response.setParametar(pacijenti);
                    response.setOperacija(Operacije.DAJ_SVE_PACIJENTE);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setUspesno(false);
                    response.setOperacija(Operacije.DAJ_SVE_PACIJENTE);
                    response.setGreska(ex);
                }
            }
            break;
                case Operacije.SACUVAJ_REZULTAT:
                List<Rezultat> rezultati = (List<Rezultat>) kz.getData();
                 {
                     try {
                         
                         Kontroler.getInstance().sacuvajSveRezultate(rezultati);
                         response.setUspesno(true);
                         response.setOperacija(Operacije.SACUVAJ_REZULTAT);
                     } catch (Exception ex) {
                         Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                         response.setUspesno(false);
                         response.setOperacija(Operacije.SACUVAJ_REZULTAT);
                         response.setGreska(ex);
                     }
                }
                break;
            case Operacije.OBRISI_LABORANTA:
                Laborant laborant = (Laborant) kz.getData();
                 {
                    try {
                        Kontroler.getInstance().obrisiLaboranta(laborant);
                        response.setUspesno(true);
                        response.setOperacija(Operacije.OBRISI_LABORANTA);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.OBRISI_LABORANTA);
                        response.setGreska(ex);
                    }
                }
                    break;
            case Operacije.OBRISI_TEST:
                Test test = (Test) kz.getData();
                 {
                    try {
                        Kontroler.getInstance().obrisiTest(test);
                        response.setUspesno(true);
                        response.setOperacija(Operacije.OBRISI_TEST);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.OBRISI_TEST);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.FILTRIRAJ_LABORANTE_IME:
                Laborant laborant2 = (Laborant) kz.getData();
                 {
                    try {
                        System.out.println(laborant2.getPrezime());
                        List<OpstiDomenskiObjekat> laboranti = Kontroler.getInstance().filtrirajLaborante(laborant2.getPrezime());
                        response.setUspesno(true);
                        response.setParametar(laboranti);
                        response.setOperacija(Operacije.FILTRIRAJ_LABORANTE_IME);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.FILTRIRAJ_LABORANTE_IME);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.NADJI_LABORANTA_ID:
                  Long laborantID = (Long) kz.getData();
                 {
                    try {
                        Laborant laborant3 = (Laborant) Kontroler.getInstance().dajLaboranta(laborantID);
                        response.setUspesno(true);
                        response.setParametar(laborant3);
                        response.setOperacija(Operacije.NADJI_LABORANTA_ID);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.NADJI_LABORANTA_ID);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.NADJI_TEST_ID: 
                  Long testID = (Long) kz.getData();
                 {
                    try {
                        Test test2 = (Test) Kontroler.getInstance().dajTest(testID);
                        response.setUspesno(true);
                        response.setParametar(test2);
                        response.setOperacija(Operacije.NADJI_TEST_ID);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.NADJI_TEST_ID);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.DAJ_SVE_TERMINE_TESTIRANJA: 
            {
                try {
                    List<OpstiDomenskiObjekat> termini = Kontroler.getInstance().dajSveTermineTestiranja();
                    response.setUspesno(true);
                    response.setParametar(termini);
                    response.setOperacija(Operacije.DAJ_SVE_TERMINE_TESTIRANJA);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setUspesno(false);
                    response.setOperacija(Operacije.DAJ_SVE_TERMINE_TESTIRANJA);
                    response.setGreska(ex);
                }
            }
                break;
            case Operacije.SACUVAJ_LABORANTA: 
                Laborant laborant4 = (Laborant) kz.getData();
                
                 {
                    try {
                        Long id = Kontroler.getInstance().sacuvajLaboranta(laborant4);
                        response.setUspesno(true);
                        response.setParametar(id);
                        response.setOperacija(Operacije.SACUVAJ_LABORANTA);
                    } catch (Exception ex) {
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.SACUVAJ_LABORANTA);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.SACUVAJ_TERMIN_TESTIRANJA: 
                TerminTestiranja termin = (TerminTestiranja) kz.getData();
                 {
                    try {
                        Long id = Kontroler.getInstance().sacuvajTerminTestiranja(termin);
                        response.setUspesno(true);
                        response.setParametar(id);
                        response.setOperacija(Operacije.SACUVAJ_TERMIN_TESTIRANJA);
                    } catch (Exception ex) {
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.SACUVAJ_TERMIN_TESTIRANJA);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.SACUVAJ_TEST:
                Test test2 = (Test) kz.getData();
                 {
                    try {
                        Long id = Kontroler.getInstance().sacuvajTest(test2);
                        response.setUspesno(true);
                        response.setParametar(id);
                        response.setOperacija(Operacije.SACUVAJ_TEST);
                    } catch (Exception ex) {
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.SACUVAJ_TEST);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.AZURIRAJ_LABORANTA:
                Laborant laborant3 = (Laborant) kz.getData();
                 {
                    try {
                        System.out.println("pre");
                        Kontroler.getInstance().azurirajLaboranta(laborant3);
                        System.out.println("posle");
                        response.setUspesno(true);
                        response.setOperacija(Operacije.AZURIRAJ_LABORANTA);
                        
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.AZURIRAJ_LABORANTA);
                        response.setGreska(ex);
                    }
                }
                break;
            case Operacije.FILTRIRAJ_REZULTATE_TERMIN:
                TerminTestiranja tt = (TerminTestiranja) kz.getData();
                 {
                    try {
                        List<OpstiDomenskiObjekat> rezultati1 = Kontroler.getInstance().filtrirajRezultate(tt);
                        response.setUspesno(true);
                        response.setParametar(rezultati1);
                        response.setOperacija(Operacije.FILTRIRAJ_REZULTATE_TERMIN);
                    } catch (Exception ex) {
                        Logger.getLogger(KlijentNit.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(ServerNit.class.getName()).log(Level.SEVERE, null, ex);
                        response.setUspesno(false);
                        response.setOperacija(Operacije.FILTRIRAJ_REZULTATE_TERMIN);
                        response.setGreska(ex);
                    }
                }
                break;
                
                
        }
        System.out.println(response.getOperacija());
        return response;
    }
    private void posaljiOdgovor(Socket socket, ServerskiOdgovor so) throws IOException {
        out.writeObject(so);
    }

    /**
     * @return the administrator
     */
    public Administrator getAdministrator() {
        return administrator;
    }

    public void diskonektujAdministratora() throws IOException {
        System.out.println("Prestanak sa radom");
        ServerskiOdgovor so = new ServerskiOdgovor(Operacije.FILTRIRAJ_REZULTATE_TERMIN, null, false, null);
        posaljiOdgovor(socket, so);
        this.interrupt();
    }
    
}
