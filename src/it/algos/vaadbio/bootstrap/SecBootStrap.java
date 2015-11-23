package it.algos.vaadbio.bootstrap;

import it.algos.webbase.domain.ruolo.Ruolo;
import it.algos.webbase.web.lib.LibSecurity;
import it.algos.webbase.web.lib.LibSession;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Security BootStrap.<br>
 */
public class SecBootStrap implements ServletContextListener {

    private String botRole = "bot";
    private String botName = "biobot";

    /**
     * This method is called prior to the servlet context being
     * initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p/>
     * <p/>
     * Se le versioni aumentano, conviene spostare in una classe esterna
     */
    /**
     * Executed on container startup
     * <p>
     * Setup non-UI logic here <br>
     * Viene normalmente sovrascritta dalla sottoclasse per regolare alcuni flag dell'applicazione <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        this.creaRuoli();
        this.creaUtenti();
        this.effettuaRegistrazione();
        this.checkRegistrazione();
    }// end of method

    /**
     * This method is invoked when the Servlet Context
     * (the Web application) is undeployed or
     * WebLogic Server shuts down.
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method


    /**
     * Crea una serie di ruoli
     * <p/>
     * Alcuni generali controlla se esistono (dovrebbero esserci) e li crea solo se mancano (la prima volta)
     * Alcuni specifici di questa applicazione e li crea
     */
    private void creaRuoli() {
//        LibSecurity.checkRuoli();
        this.creaRuolo(botRole);
    }// end of method

    /**
     * Crea una serie di utenti <br>
     */
    private void creaUtenti() {
        LibSecurity.creaDeveloper("gac", "pippo");
        LibSecurity.creaAdmin("gac", "pippo");
        LibSecurity.creaUser("gac", "pippo");
        LibSecurity.creaGuest("ospite", "");
        LibSecurity.creaUtente(botName, "", botRole);
    }// end of method


    /**
     * Effettua la registrazione
     * <p/>
     * Prima cerca se esistono cookies e tenta di connettersi usandoli
     * Poi cerca se esistono preferenze e tenta di connettersi usando i valori delle pref
     * Se non riesce presenta il bottone/menu di login che apre il dialogo di registrazione
     */
    private void effettuaRegistrazione() {
        if (checkCookies()) {
            return;
        }// fine del blocco if

//        if (checkPreferenze()) {
//            return;
//        }// fine del blocco if

        if (checkLogin()) {
            return;
        }// fine del blocco if
//        Notification.show("Avviso", "Nessun login effettuato", Notification.Type.HUMANIZED_MESSAGE);
    }// end of method

    /**
     * Controlla se esistono cookies e tenta di connettersi usandoli
     */
    private boolean checkCookies() {
        return false;
    }// end of method


    /**
     * Dialogo di login, oppure registrazione automatica
     */
    private boolean checkLogin() {
        return false;
    }// end of method

    /**
     * Controlla il login effettuato
     */
    private boolean checkRegistrazione() {
        return false;
    }// end of method

    /**
     * Crea un singolo ruolo
     */
    private void creaRuolo(String nome) {
        Ruolo ruolo = Ruolo.read(nome);

        if (ruolo == null) {
            ruolo = new Ruolo(nome);
            ruolo.save();
        }// fine del blocco if
    }// end of method


}// end of boot class

