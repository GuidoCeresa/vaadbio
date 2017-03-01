package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.Nazionalita;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by gac on 10/01/17.
 * Pagine di controllo del progetto Biografie
 * Crea 1 pagina:
 * - Progetto:Biografie/Attivita: Elenco delle xxx attivita che hanno più di yyy ricorrenze nelle voci biografiche
 */
public class StatAttivita extends Statistiche {

    private static String KEY_MAP_PLURALE = "plurale";
    private static String KEY_MAP_VOCI = "voci";

    private ArrayList<HashMap> listaUsate;
    private ArrayList<String> listaNonUsate;


//    private ArrayList<Attivita> listaAttivita;
//    private LinkedHashMap<Attivita, ArrayList> mappaAttivita;
//    private long inizio = System.currentTimeMillis();
//    private int mod = 0;
//    private String modTxt;


    /**
     * Costruttore completo
     */
    public StatAttivita() {
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
        titoloPagina = "Attività";
        usaFooterCorrelate = true;
    }// fine del metodo



    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        this.elaboraUsateNonUsate();

        int numUsate = listaUsate.size();
        int numNonUsate = listaNonUsate.size();
        String ref1 = "Le attività sono quelle [[Discussioni progetto:Biografie/Attività|'''convenzionalmente''' previste]] dalla comunità ed [[Modulo:Bio/Plurale nazionalità|inserite nell' elenco]] utilizzato dal [[template:Bio|template Bio]]";
        String ref2 = "La '''differenza''' tra le voci della categoria e quelle utilizzate è dovuta allo specifico utilizzo del [[template:Bio|template Bio]] ed in particolare all'uso del parametro Categorie=NO";
        String ref3 = "Si tratta di attività '''originariamente''' discusse ed [[Modulo:Bio/Plurale attività|inserite nell'elenco]] che non sono mai state utilizzate o che sono state in un secondo tempo sostituite da altre denominazioni";
        ref1 = LibWiki.setRef(ref1);
        ref2 = LibWiki.setRef(ref2);
        ref3 = LibWiki.setRef(ref3);

        text += "==Attività usate==";
        text += A_CAPO;
        text += "'''" + numUsate + "''' attività " + ref1 + " '''effettivamente utilizzate''' nelle [[:Categoria:BioBot|'''voci biografiche''']]" + ref2 + " che usano il [[template:Bio|template Bio]].";
        text += A_CAPO;
        text += creaTableUsate();
        text += A_CAPO;
        text += A_CAPO;
        text += "==Attività non usate==";
        text += A_CAPO;
        text += "'''" + numNonUsate + "''' attività  presenti nell' [[Modulo:Bio/Plurale attività|'''elenco del progetto Biografie''']] ma '''non utilizzate''' " + ref3 + " in nessuna '''voce biografica''' che usa il [[template:Bio|template Bio]].";
        text += A_CAPO;
        text += this.creaTableNonUsate();
        text += A_CAPO;

        return text;
    }// fine del metodo

    private void elaboraUsateNonUsate() {
        ArrayList<String> listaAllDistinctPlurali;
        listaUsate = new ArrayList<>();
        listaNonUsate = new ArrayList<>();
        ArrayList<Attivita> listaAttività;
        int numVoci = 0;
        HashMap mappa = null;

//        listaAllDistinctPlurali = Attivita.findDistinctPlurale();
//        for (String plurale : listaAllDistinctPlurali) {
//            listaAttività = Attivita.findAllByPlurale(plurale);
//            numVoci = 0;
//
//            if (listaAttività != null && listaAttività.size() > 0) {
//                for (Attivita attivita : listaAttività) {
//                    numVoci += attivita.countBio();
//                }// end of for cycle
//            }// end of if cycle
//
//            if (numVoci > 0) {
//                mappa = new HashMap();
//                mappa.put(KEY_MAP_PLURALE, plurale);
//                mappa.put(KEY_MAP_VOCI, numVoci);
//                listaUsate.add(mappa);
//            } else {
//                listaNonUsate.add(plurale);
//            }// end of if/else cycle
//        } // fine del ciclo for-each

    }// fine del metodo


    /**
     * Corpo della tabella
     */
    private String creaTableUsate() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

//        mappa.put(Cost.KEY_MAPPA_TITOLI, listaTitoli());
//        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRigheUsate());
//        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo


    /**
     * Corpo della tabella
     */
    private String creaTableNonUsate() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, "Attività non utilizzate");
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaNonUsate);
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo

}// end of class
