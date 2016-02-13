package it.algos.vaadbio.giorno;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibTimeBio;
import it.algos.vaadbio.mese.Mese;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per i Giorni
 */
public abstract class GiornoService {


    /**
     * Crea 366 records per tutti i giorni dell'anno (compreso bisestile)
     * <p>
     */
    public static boolean creaGiorni() {
        ArrayList<HashMap> lista;
        String titolo;
        int bisestile;
        Mese mese;

        if (Giorno.count() > 0) {
            return false;
        }// end of if cycle

        //costruisce i 366 records
        lista = LibTimeBio.getAllGiorni();
        for (HashMap mappaGiorno : lista) {
            titolo = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO);
            bisestile = (int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE);
            mese = Mese.findByTitoloLungo((String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO));
          new Giorno(titolo, bisestile, mese).save();
        }// end of for cycle

        return true;
    }// end of static method

    /**
     * Calcola la lunghezza del titolo più lungo
     */
    public static int maxLength() {
        ArrayList<Giorno> lista = Giorno.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Giorno giorno : lista) {
                len = giorno.getTitolo().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class
