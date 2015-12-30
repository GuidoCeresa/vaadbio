package it.algos.vaadbio;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import it.algos.vaad.wiki.WikiLogin;
import it.algos.vaadbio.anno.AnnoMod;
import it.algos.vaadbio.bio.BioMod;
import it.algos.vaadbio.biologo.BioLogoMod;
import it.algos.vaadbio.giorno.GiornoMod;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.PrefMod;
import it.algos.webbase.domain.utente.UtenteModulo;
import it.algos.webbase.domain.vers.VersMod;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.ui.AlgosUI;
import it.algos.vaadbio.nazionalita.NazionalitaMod;
import it.algos.vaadbio.attivita.AttivitaMod;

/**
 * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
 * Qui viene regolato il theme base per tutta l'applicazione
 * <p>
 * Layout standard composto da:
 * Top      - una barra composita di menu e login
 * Body     - un placeholder per il portale della tavola/modulo in uso
 * Footer   - un striscia per eventuali informazioni (Algo, copyright, ecc)
 * <p>
 * Se le applicazioni specifiche vogliono una UI completamente differente,
 * possono sovrascrivere il metodo startUI() della superclasse
 */
//@Theme("valo")
@Theme("algos")
public class VaadbioUI extends AlgosUI {

    /**
     * Initializes this UI. This method is intended to be overridden by subclasses to build the view and configure
     * non-component functionality. Performing the initialization in a constructor is not suggested as the state of the
     * UI is not properly set up when the constructor is invoked.
     * <p>
     * The {@link VaadinRequest} can be used to get information about the request that caused this UI to be created.
     * </p>
     * Se viene sovrascritto dalla sottoclasse, deve (DEVE) richiamare anche il metodo della superclasse
     * di norma dopo (DOPO) aver effettuato alcune regolazioni <br>
     * Nella sottoclasse specifica viene eventualmente regolato il nome del modulo di partenza <br>
     *
     * @param request the Vaadin request that caused this UI to be created
     */
    @Override
    protected void init(VaadinRequest request) {
        WikiLogin login;

        menuAddressModuloPartenza = "Wiki";
        super.init(request);
        footerLayout.addComponent(new Label("Vaadbio versione 1.6 del 30 dic 2015"));

//        this.checkLogin();
        boolean loggato = false;
        loggato = LibSession.getAttributeBool("logged");
        if (loggato) {
            login = LibBio.getLogin();
            if (login.isValido()) {
                Notification.show("Avviso", "Sei loggato come " + login.getLgusername(), Notification.Type.HUMANIZED_MESSAGE);
            } else {
                Notification.show("Avviso", "Login non valido", Notification.Type.HUMANIZED_MESSAGE);
            }// fine del blocco if-else
        } else {
            Notification.show("Avviso", "Nessun login effettuato", Notification.Type.HUMANIZED_MESSAGE);
        }// fine del blocco if-else
    }// end of method


    /**
     * Aggiunge i moduli specifici dell'applicazione (oltre a LogMod, VersMod, PrefMod)
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse per aggiungere i moduli alla menubar dell'applicazione <br>
     * Chiama il metodo  addModulo(...) della superclasse per ogni modulo previsto nella barra menu
     */
    @Override
    protected void addModuli() {
        this.addModulo(new UtenteModulo("User"));
        this.addModulo(new VersMod());
        this.addModulo(new BioLogoMod());
        this.addModulo(new PrefMod());
        this.addModulo(new AttivitaMod());
        this.addModulo(new NazionalitaMod());
        this.addModulo(new GiornoMod());
        this.addModulo(new AnnoMod());
        this.addModulo(new BioMod());
    }// end of method

}//end of UI class

