package it.algos.vaadbio.lib;

import it.algos.webbase.web.lib.LibArray;
import it.algos.webbase.web.lib.MeseEnum;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 15 dic 2015.
 * .
 */
public class LibTimeBio {

    /**
     * Costruisce tutti i giorni dell'anno
     * Considera anche l'anno bisestile
     * <p>
     * Restituisce un array di Map
     * Ogni mappa ha:
     * numeroMese
     * #progressivoNormale
     * #progressivoBisestile
     * nome  (numero per il primo del mese)
     * titolo (1° per il primo del mese)
     */
    public static ArrayList<HashMap> getAllGiorni() {
        ArrayList<HashMap> listaAnno = new ArrayList<HashMap>();
        ArrayList<HashMap> listaMese;
        int progAnno = 0;

        for (int k = 1; k <= 12; k++) {
            listaMese = getGiorniMese(k, progAnno);
            listaAnno = LibArray.somma(listaAnno, listaMese);
            progAnno += listaMese.size();
        }// end of for cycle

        return listaAnno;
    }// end of static method


    /**
     * Costruisce tutti i giorni del mese
     * Considera anche l'anno bisestile
     * <p>
     * Restituisce un array di Map
     * Ogni mappa ha:
     * numeroMese
     * nomeMese
     * #progressivoNormale
     * #progressivoBisestile
     * nome  (numero per il primo del mese)
     * titolo (1° per il primo del mese)
     *
     * @param numMese  numero del mese, partendo da 1 per gennaio
     * @param progAnno numero del giorno nell'anno, partendo da 1 per il 1° gennaio
     * @return lista di mappe, una per ogni giorno del mese considerato
     */
    public static ArrayList<HashMap> getGiorniMese(int numMese, int progAnno) {
        ArrayList<HashMap> listaMese = new ArrayList<HashMap>();
        HashMap mappa;
        int giorniDelMese;
        String nomeMese;
        MeseEnum mese = MeseEnum.getMese(numMese);
        nomeMese = MeseEnum.getLong(numMese);
        giorniDelMese = MeseEnum.getGiorni(numMese,2016);
        final int taglioBisestile = 60;
        String tag;
        String tagUno;

        //--patch per febbraio
        if (numMese == 2) {
            giorniDelMese++;
        }// fine del blocco if

        for (int k = 1; k <= giorniDelMese; k++) {
            progAnno++;
            tag = k + CostBio.SPAZIO + nomeMese;
            mappa = new HashMap();
            mappa.put(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO, numMese);
            mappa.put(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO, nomeMese);
            mappa.put(CostBio.KEY_MAPPA_GIORNI_NOME, tag);
            mappa.put(CostBio.KEY_MAPPA_GIORNI_BISESTILE, progAnno);
            mappa.put(CostBio.KEY_MAPPA_GIORNI_NORMALE, progAnno);
            mappa.put(CostBio.KEY_MAPPA_GIORNI_MESE_MESE, mese);
            if (k == 1) {
                mappa.put(CostBio.KEY_MAPPA_GIORNI_TITOLO, CostBio.PRIMO_GIORNO_MESE + CostBio.SPAZIO + nomeMese);
            } else {
                mappa.put(CostBio.KEY_MAPPA_GIORNI_TITOLO, tag);
            }// fine del blocco if-else

            //--gestione degli anni bisestili
            if (progAnno == taglioBisestile) {
                mappa.put(CostBio.KEY_MAPPA_GIORNI_NORMALE, 0);
            }// fine del blocco if
            if (progAnno > taglioBisestile) {
                mappa.put(CostBio.KEY_MAPPA_GIORNI_NORMALE, progAnno - 1);
            }// fine del blocco if

            listaMese.add(mappa);
        } // fine del ciclo for

        return listaMese;
    }// end of static method

}// end of abstract static class
