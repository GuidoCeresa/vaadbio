package it.algos.vaadbio.mese;

import it.algos.webbase.web.lib.MeseEnum;

import java.util.ArrayList;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per i Mesi
 */
public abstract class MeseService {


    /**
     * Crea i 12 mesi prendendoli dalla Enumeration MeseEnum
     * <p>
     */
    public static boolean creaMesi() {
        String titoloBreve;
        String titoloLungo;
        int giorni;
        int giorniBisestili;

        if (Mese.count() == 12) {
            return false;
        }// end of if cycle

        //costruisce i 12 records
        for (MeseEnum meseEnum : MeseEnum.values()) {
            titoloBreve = meseEnum.getBreve();
            titoloLungo = meseEnum.getLungo();
            giorni = meseEnum.getGiorni();
            giorniBisestili = meseEnum.getGiorniBis();
            new Mese(titoloBreve, titoloLungo, giorni, giorniBisestili).save();
        }// end of for cycle

        return true;
    }// end of static method

    /**
     * Calcola la lunghezza del titolo più lungo
     */
    public static int maxLength() {
        ArrayList<Mese> lista = Mese.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Mese mese : lista) {
                len = mese.getTitoloLungo().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class
