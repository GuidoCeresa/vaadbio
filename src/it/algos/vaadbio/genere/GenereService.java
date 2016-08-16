package it.algos.vaadbio.genere;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 29 mar 2016.
 * Nomi delle attività per genere
 */
public abstract class GenereService {
    private final static String TITOLO_MODULO = "Modulo:Bio/Plurale attività genere";

    /**
     * Aggiorna i records leggendoli dalla pagina wiki
     * <p>
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Aggiunge al database i records mancanti
     */
    public static boolean download() {
        long inizio = System.currentTimeMillis();
        LinkedHashMap<String, Object> mappa;
        String secondi;
        String records;

        // Recupera la mappa dalla pagina wiki
        mappa = LibWiki.leggeMappaModulo(TITOLO_MODULO);

        // Aggiunge i records mancanti
        if (mappa == null) {
            return false;
        } else {
            for (Map.Entry<String, Object> elementoDellaMappa : mappa.entrySet()) {
                aggiungeRecord(elementoDellaMappa);
            }// end of for cycle

            if (Pref.getBoolean(CostBio.USA_LOG_DEBUG, false)) {
                secondi = LibTime.difText(inizio);
                records = LibNum.format(mappa.size());
                Log.debug("gen", "Aggiornati in " + secondi + " i " + records + " records di attività per genere");
            }// fine del blocco if
            return true;
        }// end of if/else cycle
    } // fine del metodo statico

    /**
     * Aggiunge il record mancante
     */
    public static void aggiungeRecord(Map.Entry<String, Object> elementoDellaMappa) {
        ArrayList listaValori = null;
        String singolare;
        String plurale;
        String sesso;
        Genere genere;

        if (elementoDellaMappa != null) {
            singolare = elementoDellaMappa.getKey();

            if (elementoDellaMappa.getValue() instanceof ArrayList) {
                listaValori = (ArrayList) elementoDellaMappa.getValue();
            }// end of if cycle

            if (listaValori != null && listaValori.size() > 1) {
                if (listaValori.size() == 2) {
                    plurale = (String) listaValori.get(0);
                    sesso = (String) listaValori.get(1);
                    genere = Genere.findBySingolareAndSesso(singolare,sesso);
                    if (genere == null) {
                        genere = new Genere(singolare, plurale, sesso);
                        genere.save();
                    }// end of if cycle
                }// end of if cycle

                if (listaValori.size() == 4) {
                    plurale = (String) listaValori.get(0);
                    sesso = (String) listaValori.get(1);
                    genere = Genere.findBySingolareAndSesso(singolare,sesso);
                    if (genere == null) {
                        genere = new Genere(singolare, plurale, sesso);
                        genere.save();
                    }// end of if cycle
                    plurale = (String) listaValori.get(2);
                    sesso = (String) listaValori.get(3);
                    genere = Genere.findBySingolareAndSesso(singolare,sesso);
                    if (genere == null) {
                        genere = new Genere(singolare, plurale, sesso);
                        genere.save();
                    }// end of if cycle
                }// end of if cycle
            }// end of if cycle

        }// fine del blocco if
    } // fine del metodo statico

}// end of abstract static class
