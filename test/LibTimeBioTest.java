import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibTimeBio;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by gac on 15 dic 2015.
 * .
 */
public class LibTimeBioTest {



    @Test
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
    public void getGiorniMese() {
        ArrayList<HashMap> listaMese;
        HashMap mappaGiorno;

        listaMese = LibTimeBio.getGiorniMese(1, 0);
        assertNotNull(listaMese);
        assertTrue(listaMese.size() == 31);
        mappaGiorno = listaMese.get(0);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 1);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("gennaio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("1 gennaio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals(CostBio.PRIMO_GIORNO_MESE + CostBio.SPAZIO + "gennaio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 1);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 1);
        mappaGiorno = listaMese.get(14);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 1);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("gennaio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("15 gennaio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("15 gennaio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 15);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 15);

        listaMese = LibTimeBio.getGiorniMese(2, 31);
        assertNotNull(listaMese);
        assertTrue(listaMese.size() == 29);
        mappaGiorno = listaMese.get(0);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 2);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("1 febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals(CostBio.PRIMO_GIORNO_MESE + CostBio.SPAZIO + "febbraio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 32);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 32);
        mappaGiorno = listaMese.get(26);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 2);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("27 febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("27 febbraio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 58);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 58);
        mappaGiorno = listaMese.get(27);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 2);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("28 febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("28 febbraio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 59);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 59);
        mappaGiorno = listaMese.get(28);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 2);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("29 febbraio"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("29 febbraio"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 0);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 60);

        listaMese = LibTimeBio.getGiorniMese(3, 60);
        assertNotNull(listaMese);
        assertTrue(listaMese.size() == 31);
        mappaGiorno = listaMese.get(0);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 3);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("marzo"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("1 marzo"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals(CostBio.PRIMO_GIORNO_MESE + CostBio.SPAZIO + "marzo"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 60);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 61);
    }// end of single test


    @Test
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
    public  void getAllGiorni() {
        ArrayList<HashMap> listaAnno;
        HashMap mappaGiorno;

        listaAnno = LibTimeBio.getAllGiorni();
        assertNotNull(listaAnno);
        assertTrue(listaAnno.size() == 366);
        mappaGiorno = listaAnno.get(60);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 3);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("marzo"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("1 marzo"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals(CostBio.PRIMO_GIORNO_MESE + CostBio.SPAZIO + "marzo"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 60);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 61);
        mappaGiorno = listaAnno.get(349);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 12);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("dicembre"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("15 dicembre"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("15 dicembre"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 349);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 350);
        mappaGiorno = listaAnno.get(365);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_NUMERO) == 12);
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_MESE_TESTO).equals("dicembre"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NOME).equals("31 dicembre"));
        assertTrue(mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_TITOLO).equals("31 dicembre"));
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_NORMALE) == 365);
        assertTrue((int) mappaGiorno.get(CostBio.KEY_MAPPA_GIORNI_BISESTILE) == 366);
    }// end of single test

}// end of test class