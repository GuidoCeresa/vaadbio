package it.algos.vaadbio.nazionalita;

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
 * Created by gac on 25 dic 2015.
 * .
 */
public abstract class NazionalitaService {

    private static String TITOLO_MODULO = "Modulo:Bio/Plurale_nazionalità";

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
        String titolo = "";
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

            if (Pref.getBool(CostBio.USA_LOG_DEBUG, false)) {
                secondi = LibTime.difText(inizio);
                records = LibNum.format(mappa.size());
                Log.setDebug("attivita", "Aggiornati in " + secondi + " i " + records + " records di nazionalità (plurale)");
            }// fine del blocco if
            return true;
        }// end of if/else cycle

    } // fine del metodo

    /**
     * Aggiunge il record mancante
     */
    public static void aggiungeRecord(Map.Entry<String, Object> elementoDellaMappa) {
        String singolare;
        String plurale = CostBio.VUOTO;
        Nazionalita nazionalita;

        if (elementoDellaMappa != null) {
            singolare = elementoDellaMappa.getKey();

            if (elementoDellaMappa.getValue() instanceof String) {
                plurale = (String) elementoDellaMappa.getValue();
            }// end of if cycle

            if (!plurale.equals(CostBio.VUOTO)) {
                nazionalita = Nazionalita.findBySingolare(singolare);
                if (nazionalita == null) {
                    nazionalita = new Nazionalita();
                }// fine del blocco if
                nazionalita.setSingolare(singolare);
                nazionalita.setPlurale(plurale);
                nazionalita.save();
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo


    /**
     * Calcola la lunghezza della nazionalità più lunga
     */
    public static int maxLength() {
        ArrayList<Nazionalita> lista = Nazionalita.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Nazionalita nazionalita : lista) {
                len = nazionalita.getPlurale().length();
                max = Math.max(max, len);
                len = nazionalita.getSingolare().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class
