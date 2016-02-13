package it.algos.vaadbio.secolo;


import it.algos.webbase.web.lib.SecoloEnum;

import java.util.ArrayList;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per i Secoli
 */
public abstract class SecoloService {


    /**
     * Crea i 12 mesi prendendoli dalla Enumeration SecoloEnum
     * <p>
     */
    public static boolean creaSecoli() {
        String titolo;
        int inizio;
        int fine;
        boolean anteCristo;

        if (Secolo.count() > 0) {
            return false;
        }// end of if cycle

        //costruisce i 12 records
        for (SecoloEnum meseEnum : SecoloEnum.values()) {
            titolo = meseEnum.getTitolo();
            inizio = meseEnum.getInizio();
            fine = meseEnum.getFine();
            anteCristo = meseEnum.isAnteCristo();
            new Secolo(titolo, inizio, fine, anteCristo).save();
        }// end of for cycle

        return true;
    }// end of static method

    /**
     * Calcola la lunghezza del titolo più lungo
     */
    public static int maxLength() {
        ArrayList<Secolo> lista = Secolo.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Secolo secolo : lista) {
                len = secolo.getTitolo().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class

