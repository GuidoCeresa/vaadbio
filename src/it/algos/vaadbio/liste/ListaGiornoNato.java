package it.algos.vaadbio.liste;

import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by gac on 21 dic 2015.
 * <p>
 * Crea la lista dei nati nel giorno e la carica sul server wiki
 */
public class ListaGiornoNato extends ListaGiorno {

    /**
     * Costruttore
     *
     * @param giorno di cui creare la lista
     */
    public ListaGiornoNato(Giorno giorno) {
        super(giorno);
    }// fine del costruttore

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    @Override
    protected String getTagTitolo() {
        return "Nati ";
    }// fine del metodo


    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    protected void elaboraMappaBiografie() {
        ArrayList<Bio> listaNati = null;
        Giorno giorno = super.getGiorno();
        Anno anno;
        String annotxt;
        String didascalia;
        String didascaliaShort = CostBio.VUOTO;
        ArrayList<String> lista;

        if (giorno != null) {
            listaNati = giorno.bioNati();
        }// end of if cycle

        if (listaNati != null && listaNati.size() > 0) {
            for (Bio bio : listaNati) {
                annotxt = CostBio.VUOTO;
                anno = bio.getAnnoNatoPunta();
                if (anno != null) {
                    annotxt = anno.getTitolo();
                }// end of if cycle
                didascalia = bio.getDidascaliaGiornoNato();

                if (didascalia.contains(CostBio.TAG_SEPARATORE)) {
                    didascaliaShort = didascalia.substring(didascalia.indexOf(CostBio.TAG_SEPARATORE) + CostBio.TAG_SEPARATORE.length());
                } else {
                    didascaliaShort = didascalia;
                }// end of if/else cycle

                if (mappaBiografie.containsKey(annotxt)) {
                    lista = mappaBiografie.get(annotxt);
                    lista.add(didascaliaShort);
                } else {
                    lista = new ArrayList<>();
                    lista.add(didascaliaShort);
                    mappaBiografie.put(annotxt, lista);
                }// end of if/else cycle
            }// end of if cycle
        }// end of for cycle
        numPersone = listaNati.size();
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di nati per giorno";
    }// fine del metodo


}// fine della classe
