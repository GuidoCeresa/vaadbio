package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.giorno.Giorno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.web.lib.LibNum;

import java.math.BigDecimal;
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
        usaHeadToc = false; //--tipicamente sempre true.
        usaFooterCorrelate = true;
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
        text += "Statistiche dei nati e morti per ogni giorno dell'anno" + LibWiki.setRef(ref1) + ". Vengono prese in considerazione '''solo''' le voci biografiche che hanno valori '''validi e certi''' dei giorni di nascita e morte della persona.";
        text += A_CAPO;
        text += creaTable();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo della tabella
     */
    private String creaTable() {
        HashMap<String, Object> mappa = new HashMap<>();

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
        titoli.add("nati nel giorno " + LibWiki.setRef(ref1));
        titoli.add("morti nel giorno" + LibWiki.setRef(ref2));
        titoli.add("% nati giorno/anno ");
        titoli.add("% morti giorno/anno ");

        return titoli;
    }// fine del metodo

    /**
     * Formattazione a destra di ogni singola colonna
     */
    private ArrayList<Boolean> listaColonneDestra() {
        ArrayList<Boolean> colonneDestra = new ArrayList<>();

        colonneDestra.add(true);
        colonneDestra.add(true);
        colonneDestra.add(true);
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
        int natiTotali = Giorno.countBioNatiAll();
        int mortiTotali = Giorno.countBioMortiAll();

        for (Giorno giorno : giorni) {
            listaRighe.add(getRigaGiorno(giorno, natiTotali, mortiTotali));
        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo

    /**
     * Restituisce l'array della riga del parametro per le nazionalita
     */
    private ArrayList getRigaGiorno(Giorno giorno, int natiTotali, int mortiTotali) {
        ArrayList riga = new ArrayList();
        String nome = giorno.getTitolo();
        String titoloNati = giorno.getTitoloListaNati();
        String titoloMorti = giorno.getTitoloListaMorti();
        int nati = giorno.countBioNati();
        int morti = giorno.countBioMorti();
        BigDecimal natiPerCento = new BigDecimal(nati * 100).divide(new BigDecimal(natiTotali), 2, 2);
        BigDecimal mortiPerCento = new BigDecimal(morti * 100).divide(new BigDecimal(mortiTotali), 2, 2);

        riga.add(LibWiki.setQuadre(nome));
        riga.add(LibWiki.setQuadre(titoloNati + "|" + LibNum.format(nati)));
        riga.add(LibWiki.setQuadre(titoloMorti + "|" + LibNum.format(morti)));
        riga.add(natiPerCento.toString() + " %");
        riga.add(mortiPerCento.toString() + " %");

        return riga;
    }// fine del metodo

}// end of class
