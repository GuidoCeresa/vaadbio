package it.algos.vaadbio.daemons;

import it.algos.vaad.VaadApp;
import it.algos.vaad.wiki.WikiLogin;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.esegue.Esegue;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.bootstrap.ABootStrap;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import javax.servlet.ServletContext;

/**
 * Created by gac on 21 ago 2015.
 * <p>
 * Ciclo standard giornaliero:
 * -CicloDownload per scaricare dal server le nuove voci ed eliminare quelle cancellate
 * -CicloUpdate per aggiornare tutte le voci modificate dall'ultimo (probabilmente il giorno precedente) controllo
 * -CicloElabora per ricalcolare tutti i link interni e preparare le didascalie
 * -CicloUpload per creare e caricare sul server wiki le liste di Giorni ed Anni
 */
public class DaemonCicloCrono extends Scheduler {

    public static final String DAEMON_NAME = "daemonCicloCrono";
    private final static int NUMERO_VOCI_MINIMO_PER_OPERATIVITA_NORMALE = 297000;
    private static DaemonCicloCrono instance;


    private DaemonCicloCrono() {
        super();
    }// end of constructor

    public static DaemonCicloCrono getInstance() {
        if (instance == null) {
            instance = new DaemonCicloCrono();
        }// fine del blocco if
        return instance;
    }// end of static method


    @Override
    public void start() throws IllegalStateException {
        if (!isStarted()) {
            super.start();

            // save daemons status flag into servlet context
            ServletContext svc = ABootStrap.getServletContext();
            svc.setAttribute(DAEMON_NAME, true);

            // Schedule task
            // Ogni giorno
            schedule("1 0 * * *", new TaskCicloBio()); //dal 11 dic 2015
            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, false)) {
                Log.debug("daemonCicloCrono", "Attivato ciclo daemonCicloCrono; flag in preferenze per confermare esecuzione alle 0:01");
            }// fine del blocco if
        }// fine del blocco if
    }// end of method

    @Override
    public void stop() throws IllegalStateException {
        if (isStarted()) {
            super.stop();

            // save daemons status flag into servlet context
            ServletContext svc = ABootStrap.getServletContext();
            svc.setAttribute(DAEMON_NAME, false);

            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, false)) {
                Log.debug("daemonCicloCrono", "Spento");
            }// fine del blocco if

        }// fine del blocco if
    }// end of method

    class TaskCicloBio extends Task {

        @Override
        public void execute(TaskExecutionContext context) throws RuntimeException {

            WikiLogin wikiLogin = VaadApp.WIKI_LOGIN;
            if (wikiLogin == null) {
                wikiLogin = new WikiLogin("Gacbot@Gacbot", "tftgv0vhl16c0qnmfdqide3jqdp1i5m7");
                VaadApp.WIKI_LOGIN = wikiLogin;
            }// end of if cycle

            if (Pref.getBool(CostBio.USA_DAEMONS_DOWNLOAD, true)) {
                Esegue.cicloDownload();
            }// fine del blocco if

            if (Bio.count() > NUMERO_VOCI_MINIMO_PER_OPERATIVITA_NORMALE) {
                Esegue.cicloUpdate();
                Esegue.cicloElabora();

                if (Pref.getBool(CostBio.USA_DAEMONS_CRONO, true)) {
                    Esegue.cicloUpload();
                }// end of if cycle
            }// end of if cycle

        }// end of method
    }// end of inner class

}// end of class
