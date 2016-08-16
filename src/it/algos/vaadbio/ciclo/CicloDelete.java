package it.algos.vaadbio.ciclo;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
 * Il ciclo necessita del login come bot per il funzionamento normale
 */
public class CicloDelete extends CicloDownload{

    /**
     * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
     */
    public CicloDelete(ArrayList<Long> listaEccedenti) {
        super();
        deleteRecordsEccedenti(listaEccedenti);
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
     * Cancella tutti i records non più presenti nella categoria
     *
     * @param listaEccedenti elenco di id dei records da cancellare
     */
    public void deleteRecordsEccedenti(ArrayList<Long> listaEccedenti) {
        long inizio = System.currentTimeMillis();
        int numRecordsCancellati = 0;
        int vociDaCancellare = 0;
        Bio bio;

        if (listaEccedenti != null) {
            vociDaCancellare = listaEccedenti.size();
        }// fine del blocco if

        if (vociDaCancellare > 0) {
            for (Long pageId : listaEccedenti) {
                bio = Bio.findByPageid(pageId);
                if (bio != null) {
                    bio.delete();
                    numRecordsCancellati++;
                }// end of if cycle
            } // fine del ciclo for-each

            if (Pref.getBoolean(CostBio.USA_LOG_CICLO, true)) {
                if (numRecordsCancellati > 0) {
                    Log.info("delete", "Cancellati " + LibNum.format(numRecordsCancellati) + " records (che non avevano più la corrispondente voce nella categoria) in " + LibTime.difText(inizio));
                }// end of if cycle
            }// fine del blocco if
        }// fine del blocco if
    }// end of method


}// end of class
