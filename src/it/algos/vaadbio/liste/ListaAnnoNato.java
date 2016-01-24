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
 * Crea la lista dei nati nell'anno e la carica sul server wiki
 */
public class ListaAnnoNato extends ListaAnno {

    /**
     * Costruttore
     *
     * @param anno di cui creare la lista
     */
    public ListaAnnoNato(Anno anno) {
        super(anno);
    }// fine del costruttore

    /**
     * Recupera il tag specifico nati/morti
     * Sovrascritto
     */
    @Override
    protected String getTagTitolo() {
        return "Nati ";
    }// fine del metodo

//    /**
//     * Costruisce la query specifica per la ricerca della lista biografica
//     * Sovrascritto
//     */
//    @Override
//    protected String getQueryCrono() {
//        String queryTxt = CostBio.VUOTO;
//        Anno anno = super.getAnno();
//        long idKey;
//
//        if (anno != null) {
//            idKey = anno.getId();
//            queryTxt = "select bio.giornoNatoPunta.nome,bio.didascaliaAnnoNato,bio.giornoNatoPunta.bisestile from Bio bio where bio.annoNatoPunta.id=" + idKey + " order by bio.giornoNatoPunta.bisestile,bio.cognome";
//        }// end of if cycle
//
//        return queryTxt;
//    }// fine del metodo


    /**
     * Costruisce una mappa di biografie che hanno una valore valido per il link specifico
     */
    protected void elaboraMappaBiografie() {
        ArrayList<Bio> listaNati = null;
        Anno anno = super.getAnno();
        Giorno giorno;
        String giornoTxt;
        String didascalia;
        String didascaliaShort = CostBio.VUOTO;
        ArrayList<String> lista;

        if (anno != null) {
            listaNati = anno.bioNati();
        }// end of if cycle

        if (listaNati != null && listaNati.size() > 0) {
            mappaBiografie = new LinkedHashMap<String, ArrayList<String>>();
            for (Bio bio : listaNati) {
                giornoTxt = CostBio.VUOTO;
                giorno = bio.getGiornoNatoPunta();
                if (giorno != null) {
                    giornoTxt = giorno.getNome();
                }// end of if cycle
                didascalia = bio.getDidascaliaAnnoNato();

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
        numPersone = listaNati.size();
    }// fine del metodo

    /**
     * Categoria specifica da inserire a piede pagina
     * <p>
     * Sovrascritto
     */
    @Override
    protected String getTestoCategoria() {
        return "Liste di nati nell'anno";
    }// fine del metodo

}// fine della classe
