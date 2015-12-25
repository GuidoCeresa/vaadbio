package it.algos.vaadbio.nazionalita;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.log.Log;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibTime;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 25 dic 2015.
 * .
 */
public class NazionalitaService {

    private static String TITOLO_MODULO = "Modulo:Bio/Plurale_nazionalità";

    /**
     * Aggiorna i records leggendoli dalla pagina wiki
     * <p>
     * Recupera la mappa dalla pagina wiki
     * Ordina alfabeticamente la mappa
     * Aggiunge al database i records mancanti
     */
    public static void download() {
        long inizio = System.currentTimeMillis();
        LinkedHashMap<String, String> mappa;
        String titolo = "";
        String secondi;
        String records;

        // Recupera la mappa dalla pagina wiki
        mappa = LibWiki.leggeMappaModulo(TITOLO_MODULO);

        // Aggiunge i records mancanti
        if (mappa != null) {
            for (Map.Entry<String, String> elementoDellaMappa : mappa.entrySet()) {
                aggiungeRecord(elementoDellaMappa);
            }// end of for cycle

            if (false) {//@todo leggere da Pref
                secondi = LibTime.difText(inizio);
                records = LibNum.format(mappa.size());
                Log.setInfo("attivita", "Aggiornati in ${secondi} i ${records} records di nazionalità (plurale)");
            }// fine del blocco if
        }// fine del blocco if
    } // fine del metodo

    /**
     * Aggiunge il record mancante
     */
    public static void aggiungeRecord(Map.Entry<String, String> elementoDellaMappa) {
        String singolare;
        String plurale;
        Nazionalita nazionalita;

        if (elementoDellaMappa != null) {
            singolare = elementoDellaMappa.getKey();
            plurale = elementoDellaMappa.getValue();
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
}// end of class
