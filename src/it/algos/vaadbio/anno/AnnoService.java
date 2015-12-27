package it.algos.vaadbio.anno;

import it.algos.webbase.web.lib.Secolo;

/**
 * Created by gac on 27 dic 2015.
 * <p>
 * Classe astratta di metodi statici per gli Anni
 */
public abstract class AnnoService {


    /**
     * Crea tutti i records
     * Cancella prima quelli esistenti
     * <p>
     * Ante cristo dal 1000
     * Dopo cristo fino al 2030
     */
    public static boolean creaAnni() {
        boolean creati = false;
        //--usato nell'ordinamento delle categorie
        final int ANNO_INIZIALE = 2000;
        int anteCristo = 1000;
        int dopoCristo = 2030;
        String tag = Secolo.TAG_AC;
        int progressivo;
        String titolo;
        String secolo;

        if (Anno.count() > 0) {
            return false;
        }// end of if cycle

        //--cancella tutti i records
//        Anno.executeUpdate('delete Anno')

        //costruisce gli anni prima di cristo dal 1000
        for (int k = anteCristo; k > 0; k--) {
            progressivo = ANNO_INIZIALE - k;
            titolo = k + tag;
            secolo = Secolo.getSecoloAC(k);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titolo, secolo, progressivo).save();
            }// end of if cycle
        }// end of for cycle

        //costruisce gli anni dopo cristo fino al 2030
        for (int k = 0; k <= dopoCristo; k++) {
            progressivo = k + ANNO_INIZIALE;
            titolo = k + "";
            secolo = Secolo.getSecoloDC(k);
            if (progressivo != ANNO_INIZIALE) {
                new Anno(titolo, secolo, progressivo).save();
            }// end of if cycle
        }// end of for cycle

        return true;
    }// fine del metodo


}// end of static class
