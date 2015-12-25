package it.algos.vaadbio.liste;

import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.entity.EM;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

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
        String giornoTxt = giorno.getTitolo();
        String queryTxt = CostBio.VUOTO;

        queryTxt += "select bio.didascaliaGiornoNato from Bio bio where bio.giornoMeseNascita='";
        queryTxt += giornoTxt;
        queryTxt += "' order by bio.annoNatoPunta.ordinamento,bio.cognome";
        List vettore;
        EntityManager manager = EM.createEntityManager();
        Query query = manager.createQuery(queryTxt);

        vettore = query.getResultList();
        if (vettore != null) {
            listaBiografie = new ArrayList<String>(vettore);
        }// end of if cycle
        manager.close();

        super.elaboraListaBiografie();
    }// fine del metodo

}// fine della classe
