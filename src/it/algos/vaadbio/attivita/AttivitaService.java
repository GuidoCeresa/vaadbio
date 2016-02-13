package it.algos.vaadbio.attivita;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 25 dic 2015.
 * .
 */
public abstract class AttivitaService {

    private static String TITOLO_MODULO = "Modulo:Bio/Plurale_attività";

    /**
     * Aggiorna i records leggendoli dalla pagina wiki
     * <p>
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Aggiunge al database i records mancanti
     */
    public static boolean download() {
        long inizio = System.currentTimeMillis();
        LinkedHashMap<String, String> mappa;
        String titolo = "";
        String secondi;
        String records;

        // Recupera la mappa dalla pagina wiki
        mappa = LibWiki.leggeMappaModulo(TITOLO_MODULO);

        // Aggiunge i records mancanti
        if (mappa == null) {
            return false;
        } else {
            for (Map.Entry<String, String> elementoDellaMappa : mappa.entrySet()) {
                aggiungeRecord(elementoDellaMappa);
            }// end of for cycle

            if (false) {//@todo leggere da Pref
                secondi = LibTime.difText(inizio);
                records = LibNum.format(mappa.size());
                Log.setInfo("attivita", "Aggiornati in ${secondi} i ${records} records di attività (plurale)");
            }// fine del blocco if
            return true;
        }// end of if/else cycle
    } // fine del metodo

    /**
     * Aggiunge il record mancante
     */
    public static void aggiungeRecord(Map.Entry<String, String> elementoDellaMappa) {
        String singolare;
        String plurale;
        Attivita attivita;

        if (elementoDellaMappa != null) {
            singolare = elementoDellaMappa.getKey();
            plurale = elementoDellaMappa.getValue();
            if (!plurale.equals(CostBio.VUOTO)) {
                attivita = Attivita.findBySingolare(singolare);
                if (attivita == null) {
                    attivita = new Attivita();
                }// fine del blocco if
                attivita.setSingolare(singolare);
                attivita.setPlurale(plurale);
                attivita.save();
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Calcola la lunghezza dell'attività più lunga
     */
    public static int maxLength() {
        ArrayList<Attivita> lista = Attivita.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Attivita attivita : lista) {
                len = attivita.getPlurale().length();
                max = Math.max(max, len);
                len = attivita.getSingolare().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class

