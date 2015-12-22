package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;

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
     * Costruisce una lista di biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    @Override
    protected void elaboraListaBiografie() {
        Giorno giorno = super.getGiorno();
//        Criteria criteria
//        def results
//
//        if (giorno) {
//            criteria = BioGrails.createCriteria()
//            results = criteria.list {
//                like("giornoMeseNascitaLink", giorno)
//                and {
//                    order("annoNascitaLink", "asc")
//                    order("cognome", "asc")
//                }
//            }
//            listaBiografie = results
//        }// fine del blocco if

        super.elaboraListaBiografie();
    }// fine del metodo

}// fine della classe
