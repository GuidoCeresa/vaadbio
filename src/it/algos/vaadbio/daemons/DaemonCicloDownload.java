package it.algos.vaadbio.daemons;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.statistiche.StatSintesi;
import it.algos.vaadbio.upload.UploadAnni;
import it.algos.vaadbio.upload.UploadGiorni;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.bootstrap.ABootStrap;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import javax.servlet.ServletContext;

/**
 * Created by gac on 21 ago 2015.
 * .
 */
public class DaemonCicloDownload extends Scheduler {

    public static final String DAEMON_NAME = "daemonCicloDownload";
    private static DaemonCicloDownload instance;


    private DaemonCicloDownload() {
        super();
    }// end of constructor

    public static DaemonCicloDownload getInstance() {
        if (instance == null) {
            instance = new DaemonCicloDownload();
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
            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, true)) {
                Log.setDebug("daemonCicloDownload", "Attivato ciclo daemonCicloDownload; flag in preferenze per confermare esecuzione alle 0:01");
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

            if (Pref.getBool(CostBio.USA_LOG_DAEMONS, true)) {
                Log.setDebug("daemonCicloDownload", "Spento");
            }// fine del blocco if

        }// fine del blocco if
    }// end of method

    class TaskCicloBio extends Task {

        @Override
        public void execute(TaskExecutionContext context) throws RuntimeException {
            if (Pref.getBool(CostBio.USA_CRONO_DOWNLOAD, true)) {
                new CicloDownload();
                new UploadGiorni();
//                new UploadAnni();
                new StatSintesi();
            }// fine del blocco if
        }// end of method
    }// end of inner class

}// end of class
