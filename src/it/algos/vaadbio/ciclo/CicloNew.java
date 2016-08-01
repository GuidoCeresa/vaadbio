package it.algos.vaadbio.ciclo;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
 * Il ciclo necessita del login come bot per il funzionamento normale
 * <p>
 * Controlla il flag USA_LIMITE_DOWNLOAD
 * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare in totale (se USA_LIMITE_DOWNLOAD=true)
 * Scarica MAX_DOWNLOAD voci dal server e crea MAX_DOWNLOAD nuovi records di Bio
 */
public class CicloNew extends CicloDownload {


    /**
     * Costruttore completo
     */
    public CicloNew(ArrayList<Long> listaMancanti) {
        super();
        downloadVociMancanti(listaMancanti);
    }// end of constructor

    /**
     * Devo mettere questo metodo (che non fa nulla) per sovrascrivere il metodo della superclasse,
     * che viene chiamato automaticamente dalla superclasse stessa
     * Non posso implementare direttamente in questo metodo le funzionalità del metodo downloadVociMancanti,
     * perché downloadVociMancanti usa un parametro
     */
    protected void doInit() {
    }// end of method

    /**
     * Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
     * <p>
     * Controlla il flag USA_LIMITE_DOWNLOAD
     * Usa il numero massimo (MAX_DOWNLOAD) di voci da scaricare in totale (se USA_LIMITE_DOWNLOAD=true)
     * Esegue una serie di RequestWikiReadMultiPages a blocchi di PAGES_PER_REQUEST per volta
     * Per ogni page crea un record bio
     *
     * @param listaVociMancanti elenco (pageids) delle pagine mancanti da scaricare
     */
    private void downloadVociMancanti(ArrayList<Long> listaVociMancanti) {
        long inizio = System.currentTimeMillis();
        long fine = 0;
        int numVociTotali;
        int numVociRegistrate = 0;
        int numVociUploadate = 0;
        int numVociDaScaricare;
        ArrayList<Long> listaVociDaScaricare;
        String txtMessage;

        if (listaVociMancanti != null && listaVociMancanti.size() > 0) {
            numVociDaScaricare = listaVociMancanti.size();
        } else {
            return;
        }// end of if/else cycle

        if (Pref.getBoolean(CostBio.USA_LIMITE_CICLO, false)) {
            numVociDaScaricare = Math.min(numVociDaScaricare, Pref.getInteger(CostBio.MAX_CICLO, 1000));
        }// fine del blocco if

        listaVociDaScaricare = new ArrayList<Long>(listaVociMancanti.subList(0, numVociDaScaricare));
        numVociRegistrate = downloadPagine(listaVociDaScaricare);
//        if (Pref.getBool(CostBio.USA_COMMIT_MULTI_RECORDS, true)) {
//            numVociRegistrate = downloadPagine(listaVociDaScaricare);
//            if (Pref.getBool(CostBio.USA_LOG_DEBUG, true)) {
//                inizioCommit = System.currentTimeMillis();
//            }// end of if cycle
//            MANAGER.getTransaction().commit();
//            if (Pref.getBool(CostBio.USA_LOG_DEBUG, true)) {
//                fineCommit = System.currentTimeMillis();
//                Log.setDebug("test", "Eseguito un commit unico per " + LibNum.format(numVociRegistrate) + " voci in " + LibNum.format(fineCommit - inizioCommit) + " milliSec.");
//            }// end of if cycle
//        } else {
//        }// end of if/else cycle

        //--info
        fine = System.currentTimeMillis();
        if (Pref.getBoolean(CostBio.USA_LOG_CICLO, true)) {
            if (Pref.getBoolean(CostBio.USA_COMMIT_MULTI_RECORDS, true)) {
                txtMessage = " nuove voci con commit multirecords in ";
            } else {
                txtMessage = " nuove voci con commit di ogni singolo record in ";
            }// end of if/else cycle
            if (Pref.getBoolean(CostBio.USA_LOG_DEBUG, true)) {
                numVociTotali = Bio.count();
                Log.setDebug("new", "Create " + LibNum.format(numVociRegistrate) + "/" + LibNum.format(numVociTotali) + txtMessage + LibNum.format((fine - inizio) / 1000) + " sec.");
            } else {
                Log.setInfo("new", "Create " + LibNum.format(numVociRegistrate) + txtMessage + LibTime.difText(inizio));
            }// end of if/else cycle
        }// fine del blocco if

    }// end of method

}// end of class
