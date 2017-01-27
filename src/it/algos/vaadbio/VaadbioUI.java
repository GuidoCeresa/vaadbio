package it.algos.vaadbio;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import it.algos.vaad.VaadApp;
import it.algos.vaad.wiki.WikiLogin;
import it.algos.vaadbio.anno.AnnoMod;
import it.algos.vaadbio.attivita.AttivitaMod;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.bio.BioMod;
import it.algos.vaadbio.biologo.BioLogoMod;
import it.algos.vaadbio.cognome.CognomeMod;
import it.algos.vaadbio.genere.GenereMod;
import it.algos.vaadbio.giorno.GiornoMod;
import it.algos.vaadbio.lib.LibBio;
import it.algos.vaadbio.mese.MeseMod;
import it.algos.vaadbio.nazionalita.NazionalitaMod;
import it.algos.vaadbio.nome.NomeMod;
import it.algos.vaadbio.secolo.SecoloMod;
import it.algos.webbase.domain.pref.PrefMod;
import it.algos.webbase.domain.utente.UtenteModulo;
import it.algos.webbase.domain.vers.VersMod;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.lib.LibSession;
import it.algos.webbase.web.login.Login;
import it.algos.webbase.web.login.LoginEvent;
import it.algos.webbase.web.query.AQuery;
import it.algos.webbase.web.ui.AlgosUI;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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

        //--faccio partire una classe statica per eseguire uno 'static initialisation block'
        new VaadbioApp();

        super.menuAddressModuloPartenza = "Wiki";
        super.init(request);
        this.setCopyRight();

//        this.checkLogin();

        VaadApp.WIKI_LOGIN = new WikiLogin("Gacbot@Gacbot", "tftgv0vhl16c0qnmfdqide3jqdp1i5m7");

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
//        MenuBar.MenuItem menuUtilities = topLayout.addItem("Utilities", null);
//
//        addMod(menuUtilities, new UtenteModulo("User"));
//        addMod(menuUtilities, new VersMod());
//        addMod(menuUtilities, new BioLogoMod());
//        addMod(menuUtilities, new PrefMod());
        this.addModulo(new UtenteModulo("User"));
        this.addModulo(new VersMod());
        this.addModulo(new BioLogoMod());
        this.addModulo(new PrefMod());

        this.addModulo(new BioMod());
//        this.addModulo(new MeseMod());
//        this.addModulo(new SecoloMod());
        this.addModulo(new GiornoMod());
        this.addModulo(new AnnoMod());

        this.addModulo(new NomeMod());
        this.addModulo(new CognomeMod());
//        this.addModulo(new ProfessioneMod());
        this.addModulo(new GenereMod());
        this.addModulo(new AttivitaMod());
        this.addModulo(new NazionalitaMod());
    }// end of method


    @Override
    public void onUserLogin(LoginEvent evento) {
        Login loginBase = (Login) evento.getSource();
        String nick = loginBase.getUser().getNickname();
        String pass = loginBase.getUser().getPassword();
        WikiLogin login = new WikiLogin(nick, pass);
        if (login.isValido()) {
            LibSession.setAttribute(WikiLogin.WIKI_LOGIN_KEY_IN_SESSION, login);
        }// end of if cycle

        Object obj = LibSession.getAttribute(WikiLogin.WIKI_LOGIN_KEY_IN_SESSION);
    }// end of method

    private void setCopyRight() {
//        footerLayout.addComponent(new Label("Vaadbio versione 1.7 del 4 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 1.8 del 8 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 1.9 del 11 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.0 del 13 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.1 del 14 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.2 del 17 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.3 del 18 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.4 del 20 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.5 del 23 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.6 del 25 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.7 del 25 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.8 del 25 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 2.9 del 26 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.0 del 27 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.1 del 27 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.2 del 27 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.3 del 31 gen 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.4 del 1 feb 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.5 del 2 feb 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.6 del 9 feb 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.7 del 19 feb 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.8 del 5 mar 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 3.9 del 19 mar 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.0 del 24 mar 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.1 del 2 apr 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.2 del 17 apr 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.3 del 18 apr 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.4 del 21 apr 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.5 del 23 apr 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.6 del 11 mag 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.7 del 27 ott 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.8 del 28 ott 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 4.9 del 31 ott 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.0 del 1 nov 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.1 del 3 nov 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.2 del 4 nov 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.3 del 6 nov 2016"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.4 del 19 gen 2017"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.5 del 20 gen 2017"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.6 del 21 gen 2017"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.7 del 22 gen 2017"));
//        footerLayout.addComponent(new Label("Vaadbio versione 5.8 del 23 gen 2017"));
        footerLayout.addComponent(new Label("Vaadbio versione 5.9 del 27 gen 2017"));
    }// end of method

}//end of UI class

