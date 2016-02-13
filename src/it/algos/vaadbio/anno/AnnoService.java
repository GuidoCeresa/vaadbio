package it.algos.vaadbio.anno;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.secolo.Secolo;
import it.algos.webbase.web.lib.SecoloEnum;

import java.util.ArrayList;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per gli Anni
 */
public abstract class AnnoService {

    //--usato nell'ordinamento delle categorie
    static final int ANNO_INIZIALE = 2000;

    static final int ANTE_CRISTO = 1000;
    static final int DOPO_CRISTO = 2030;

    /**
     * Crea tutti i records
     * <p>
     * Ante cristo dal 1000
     * Dopo cristo fino al 2030
     */
    public static boolean creaAnni() {
        int progressivo;
        String titoloAnno;
        SecoloEnum secoloEnum;
        Secolo secolo;
        String titoloSecolo;

        if (Anno.count() > 0) {
            return false;
        }// end of if cycle

        //costruisce gli anni prima di cristo dal 1000
        for (int k = ANTE_CRISTO; k > 0; k--) {
            progressivo = ANNO_INIZIALE - k;
            titoloAnno = k + SecoloEnum.TAG_AC;
            secoloEnum = SecoloEnum.getSecoloAC(k);
            titoloSecolo = secoloEnum.getTitolo();
            secolo = Secolo.findByTitolo(titoloSecolo);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titoloAnno, progressivo, secolo).save();
            }// end of if cycle
        }// end of for cycle

        //costruisce gli anni dopo cristo fino al 2030
        for (int k = 1; k <= DOPO_CRISTO; k++) {
            progressivo = k + ANNO_INIZIALE;
            titoloAnno = k + CostBio.VUOTO;
            secoloEnum = SecoloEnum.getSecoloDC(k);
            titoloSecolo = secoloEnum.getTitolo();
            secolo = Secolo.findByTitolo(titoloSecolo);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titoloAnno, progressivo, secolo).save();
            }// end of if cycle
        }// end of for cycle

        return true;
    }// fine del metodo

    /**
     * Calcola la lunghezza del titolo più lungo
     */
    public static int maxLength() {
        ArrayList<Anno> lista = Anno.findAll();
        int max = 0;
        int len;

        if (lista != null && lista.size() > 0) {
            for (Anno anno : lista) {
                len = anno.getTitolo().length();
                max = Math.max(max, len);
            }// end of for cycle
        }// end of if cycle

        return max;
    } // fine del metodo

}// end of abstract static class
