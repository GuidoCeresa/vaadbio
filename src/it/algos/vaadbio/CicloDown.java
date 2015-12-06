package it.algos.vaadbio;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;

/**
 * Created by gac on 23 nov 2015.
 * <p>
 * Esegue un ciclo di sincronizzazione tra le pagine della categoria TAG_BIO ed i records della tavola Bio
 * <p>
 * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti nella categoria sul server e mancanti nel database
 * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
 * Esegue un ciclo (UPDATE) di controllo e aggiornamento di tutti i records esistenti nel database
 * <p>
 * Il ciclo viene chiamato da DaemonDownload (con frequenza giornaliera)
 * Il ciclo può essere invocato dal bottone 'Ciclo Down' nella tavola Bio
 * Il ciclo necessita del login come bot per il funzionamento normale
 */
public class CicloDown {


    private final static String TAG_BIO = "BioBot";
    private final static String TAG_CAT_DEBUG = "Nati nel 1420";


    public CicloDown() {
        doInit();
    }// end of constructor


    /**
     * <p>
     * Legge la categoria BioBot
     * Legge le voci Bio esistenti
     * <p>
     * Trova la differenza negativa (records mancanti)
     * Esegue un ciclo (NEW) di controllo e creazione di nuovi records esistenti sul server e mancanti nel database
     * Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
     * <p>
     * Trova la differenza positiva (records eccedenti)
     * Esegue un ciclo (DELETE) di cancellazione di records esistenti nel database e mancanti nella categoria
     * Cancella tutti i records non più presenti nella categoria
     */
    @SuppressWarnings("unchecked")
    private void doInit() {
        long inizio = System.currentTimeMillis();
        String nomeCategoria = "";
        ArrayList<Long> listaTotaleCategoria;
        ArrayList<Long> listaEsistentiDataBase;
        ArrayList<Long> listaMancanti;
        ArrayList<Long> listaEccedenti;

        // Il ciclo necessita del login come bot per il funzionamento normale
        // oppure del flag USA_CICLI_ANCHE_SENZA_BOT per un funzionamento ridotto
        if (!LibBio.checkLoggin()) {
            return;
        }// end of if cycle

        // seleziona la categoria normale o di debug
        if (Pref.getBool(CostBio.USA_DEBUG, true)) {
            nomeCategoria = TAG_CAT_DEBUG;
        } else {
            nomeCategoria = TAG_BIO;
        }// fine del blocco if-else

        // carica la categoria
        listaTotaleCategoria = Api.leggeCatLong(nomeCategoria);
        Log.setInfo("categoria", "Letti e caricati in memoria i pageids di " + LibNum.format(listaTotaleCategoria.size()) + " pagine della categoria '" + nomeCategoria + "' in " + LibTime.difText(inizio));

        // recupera la lista dei records esistenti nel database
        listaEsistentiDataBase = Bio.findAllPageid();

        // elabora le liste delle differenze per la sincronizzazione
        listaMancanti = LibWiki.delta(listaTotaleCategoria, listaEsistentiDataBase);
        listaEccedenti = LibWiki.delta(listaEsistentiDataBase, listaTotaleCategoria);

        // Scarica la lista di voci mancanti dal server e crea i nuovi records di Bio
        new CicloNew(listaMancanti);

        // Cancella tutti i records non più presenti nella categoria
        new CicloDelete(listaEccedenti);


        //        new CicloUpdate();
    }// end of method

}// end of class
