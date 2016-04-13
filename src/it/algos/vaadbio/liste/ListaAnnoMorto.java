package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by gac on 26 dic 2015.
 * <p>
 * Crea la lista dei morti nell'anno e la carica sul server wiki
 */
public class ListaAnnoMorto extends ListaAnno {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnnoMorto(Anno anno) {
        super(anno);
    }// fine del costruttore

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    @Override
    protected String getTagTitolo() {
        return "Morti ";
    }// fine del metodo



    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    @Override
    protected void elaboraMappaBiografie() {
        ArrayList<Bio> listaMorti = null;
        Anno anno = super.getAnno();
        Giorno giorno;
        String giornoTxt;
        String didascalia;
        String didascaliaShort = CostBio.VUOTO;
        ArrayList<String> lista;

        if (anno != null) {
            listaMorti = anno.bioMorti();
        }// end of if cycle

        if (listaMorti != null && listaMorti.size() > 0) {
            for (Bio bio : listaMorti) {
                giornoTxt = CostBio.VUOTO;
                giorno = bio.getGiornoMortoPunta();
                if (giorno != null) {
                    giornoTxt = giorno.getTitolo();
                }// end of if cycle
                didascalia = bio.getDidascaliaAnnoMorto();

                if (didascalia.contains(CostBio.TAG_SEPARATORE)) {
                    didascaliaShort = didascalia.substring(didascalia.indexOf(CostBio.TAG_SEPARATORE) + CostBio.TAG_SEPARATORE.length());
                } else {
                    didascaliaShort = didascalia;
                }// end of if/else cycle

                if (mappaBiografie.containsKey(giornoTxt)) {
                    lista = mappaBiografie.get(giornoTxt);
                    lista.add(didascaliaShort);
                } else {
                    lista = new ArrayList<>();
                    lista.add(didascaliaShort);
                    mappaBiografie.put(giornoTxt, lista);
                }// end of if/else cycle
            }// end of if cycle
        }// end of for cycle
        numPersone = listaMorti.size();
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di morti per anno";
    }// fine del metodo

}// fine della classe
