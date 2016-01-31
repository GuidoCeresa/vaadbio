package it.algos.vaadbio.giorno;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibTimeBio;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per i Giorni
 */
public abstract class GiornoService {


    /**
     * Crea 366 records per tutti i giorni dell'anno
     * <p>
     * La colonna n, è il progressivo del giorno negli anni normali
     * La colonna b, è il progressivo del giorno negli anni bisestili
     */
    public static boolean creaGiorni() {
        boolean creati = false;
        ArrayList<HashMap> listaAnno;
        String mese;
        String nome;
        String titolo;
        int normale;
        int bisestile;
        Giorno giorno;

        if (Giorno.count() > 0) {
            return false;
        }// end of if cycle

        //--cancella tutti i records

        //costruisce i 366 records
        listaAnno = LibTimeBio.getAllGiorni();
        for (HashMap mappaGiorno : listaAnno) {
            mese = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO);
            nome = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME);
            titolo = (String) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO);
            normale = (int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE);
            bisestile = (int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE);
            new Giorno(mese, nome, titolo, normale, bisestile).save();
        }// end of for cycle

        return true;
    }// end of method


}// end of static class
