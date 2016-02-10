package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 25 gen 2016.
 * .
 */
public class StatGiorni extends Statistiche {
    /**
     * Costruttore completo
     */
    public StatGiorni() {
        super();
    }// end of constructor


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();
        titoloPagina = "Giorni";
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        String ref1 = "Previsto il [[29 febbraio]] per gli [[Anno bisestile|anni bisestili]]";

        text += "==Giorni==";
        text += A_CAPO;
        text += "Statistiche dei nati e morti per ogni giorno dell'anno" + LibWiki.setRef(ref1) + ". Vengono prese in considerazione tutte e solo le voci biografiche che hanno valori validi e certi dei giorni di nascita e morte della persona.";
        text += A_CAPO;
        text += creaTable();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo della tabella
     */
    private String creaTable() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, listaTitoli());
        mappa.put(Cost.KEY_MAPPA_DESTRA_LISTA, listaColonneDestra());
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRighe());
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo

    /**
     * Titoli della tabella
     */
    private ArrayList<String> listaTitoli() {
        ArrayList<String> titoli = new ArrayList<>();

        String ref1 = "Il [[template:Bio|template Bio]] della voce biografica deve avere un valore valido al parametro '''giornoMeseNascita'''.";
        String ref2 = "Il [[template:Bio|template Bio]] della voce biografica deve avere un valore valido al parametro '''giornoMeseMorte'''.";

        titoli.add("giorno");
        titoli.add("nati " + LibWiki.setRef(ref1));
        titoli.add("morti " + LibWiki.setRef(ref2));
        titoli.add("% nati/anno ");
        titoli.add("% morti/anno ");

        return titoli;
    }// fine del metodo

    /**
     * Formattazione a destra di ogni singola colonna
     */
    private ArrayList<Boolean> listaColonneDestra() {
        ArrayList<Boolean> colonneDestra = new ArrayList<>();

        colonneDestra.add(true);
        colonneDestra.add(false);
        colonneDestra.add(false);
        colonneDestra.add(true);
        colonneDestra.add(true);

        return colonneDestra;
    }// fine del metodo

    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRighe() {
        ArrayList listaRighe = new ArrayList();
        ArrayList<Giorno> giorni = Giorno.findAll();

        for (Giorno giorno : giorni) {
            listaRighe.add(getRigaGiorno(giorno));
        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo

    /**
     * Restituisce l'array della riga del parametro per le nazionalita
     */
    private ArrayList getRigaGiorno(Giorno giorno) {
        ArrayList riga = new ArrayList();
        String nome = giorno.getTitolo();
        int nati = giorno.countBioNati();
        int morti = giorno.countBioMorti();

        riga.add(nome);
        riga.add(nati);
        riga.add(morti);
        riga.add("alfa");
        riga.add("beta");

        return riga;
    }// fine del metodo

}// end of class
