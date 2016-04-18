package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gac on 20 gen 2016.
 * .
 */
public class StatNazionalita extends Statistiche {

    private static String KEY_MAP_PLURALE = "plurale";
    private static String KEY_MAP_VOCI = "voci";

    private ArrayList<HashMap> listaUsate;
    private ArrayList<String> listaNonUsate;

    /**
     * Costruttore completo
     */
    public StatNazionalita() {
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
        titoloPagina = "Nazionalità";
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
        String ref1 = "Le nazionalità sono quelle [[Discussioni progetto:Biografie/Nazionalità|'''convenzionalmente''' previste]] dalla comunità ed [[Modulo:Bio/Plurale_nazionalità|inserite nell' elenco]] utilizzato dal [[template:Bio|template Bio]]";
        String ref2 = "La '''differenza''' tra le voci della categoria e quelle utilizzate è dovuta allo specifico utilizzo del [[template:Bio|template Bio]] ed in particolare all'uso del parametro Categorie=NO";
        String ref3 = "Si tratta di nazionalità '''originariamente''' discusse ed [[Modulo:Bio/Plurale nazionalità|inserite nell'elenco]] che non sono mai state utilizzate o che sono state in un secondo tempo sostituite da altre denominazioni";
        ref1 = LibWiki.setRef(ref1);
        ref2 = LibWiki.setRef(ref2);
        ref3 = LibWiki.setRef(ref3);

        text += "==Nazionalità usate==";
        text += A_CAPO;
        text += "'''" + numUsate + "''' nazionalità " + ref1 + " '''effettivamente utilizzate''' nelle [[:Categoria:BioBot|'''voci biografiche''']]" + ref2 + " che usano il [[template:Bio|template Bio]].";
        text += A_CAPO;
        text += creaTableUsate();
        text += A_CAPO;
        text += A_CAPO;
        text += "==Nazionalità non usate==";
        text += A_CAPO;
        text += "'''" + numNonUsate + "''' nazionalità  presenti nell' [[Modulo:Bio/Plurale nazionalità|'''elenco del progetto Biografie''']] ma '''non utilizzate''' " + ref3 + " in nessuna '''voce biografica''' che usa il [[template:Bio|template Bio]].";
        text += A_CAPO;
        text += this.creaTableNonUsate();
        text += A_CAPO;

        return text;
    }// fine del metodo

    private void elaboraUsateNonUsate() {
        ArrayList<String> listaAllDistinctPlurali;
        listaUsate = new ArrayList<>();
        listaNonUsate = new ArrayList<>();
        ArrayList<Nazionalita> listaNazionalita;
        int numVoci = 0;
        HashMap mappa = null;

        listaAllDistinctPlurali = Nazionalita.findDistinctPlurale();
        for (String plurale : listaAllDistinctPlurali) {
            listaNazionalita = Nazionalita.findAllByPlurale(plurale);
            numVoci = 0;

            if (listaNazionalita != null && listaNazionalita.size() > 0) {
                for (Nazionalita nazionalita : listaNazionalita) {
                    numVoci += nazionalita.countBio();
                }// end of for cycle
            }// end of if cycle

            if (numVoci > 0) {
                mappa = new HashMap();
                mappa.put(KEY_MAP_PLURALE, plurale);
                mappa.put(KEY_MAP_VOCI, numVoci);
                listaUsate.add(mappa);
            } else {
                listaNonUsate.add(plurale);
            }// end of if/else cycle
        } // fine del ciclo for-each

    }// fine del metodo

    /**
     * Corpo della tabella
     */
    private String creaTableUsate() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, listaTitoli());
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRigheUsate());
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo

    /**
     * Titoli della tabella
     */
    private ArrayList<String> listaTitoli() {
        ArrayList<String> titoli = new ArrayList<>();

        String ref1 = "Nelle liste le biografie sono suddivise per attività rilevanti della persona. Se il numero di voci di un paragrafo diventa rilevante, vengono create delle sottopagine specifiche di quella attività. Le sottopagine sono suddivise a loro volta in paragrafi alfabetici secondo l'iniziale del cognome.";
        String ref2 = "Le categorie possono avere sottocategorie e suddivisioni diversamente articolate e possono avere anche voci che hanno implementato la categoria stessa al di fuori del [[template:Bio|template Bio]].";

        titoli.add("lista " + LibWiki.setRef(ref1));
        titoli.add("categoria " + LibWiki.setRef(ref2));
        titoli.add("voci");

        return titoli;
    }// fine del metodo


    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRigheUsate() {
        ArrayList listaRighe = new ArrayList();

        for (HashMap mappa : listaUsate) {
            listaRighe.add(getRigaNazionalita(mappa));
        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo


    /**
     * Restituisce l'array della riga del parametro per le nazionalita
     * La mappa contiene:
     * -plurale dell'attività
     * -numero di voci che nel campo nazionalita usano tutti records di nazionalita che hanno quel plurale
     */
    private ArrayList getRigaNazionalita(HashMap mappa) {
        ArrayList riga = new ArrayList();
        String plurale = "";
        int numVoci = 0;
        ArrayList<Nazionalita> listaNazionalita;
        String tagCat = ":Categoria:";
        String tagListe = tagPath + "Nazionalità/";
        String pipe = "|";
        String lista;
        String categoria = "";
//        boolean usaDueColonne = Pref.getBool(LibBio.USA_DUE_COLONNE_STATISTICHE_NAZIONALITA, true);
        boolean usaDueColonne = true;

        if (mappa != null) {
            plurale = (String) mappa.get(KEY_MAP_PLURALE);
            numVoci = (int) mappa.get(KEY_MAP_VOCI);

            lista = tagListe + LibText.primaMaiuscola(plurale) + pipe + LibText.primaMinuscola(plurale);
            lista = LibWiki.setQuadre(lista);
            if (usaDueColonne) {
                categoria = tagCat + LibText.primaMinuscola(plurale) + pipe + plurale;
                categoria = LibWiki.setQuadre(categoria);
            }// fine del blocco if
        } else {
            lista = plurale;
        }// fine del blocco if-else

        //riga.add(getColore(mappa))
        riga.add(lista);
        if (usaDueColonne) {
            riga.add(categoria);
        }// fine del blocco if
//        riga.add("{{formatnum" + ":" + numVoci + "}}");
        riga.add(numVoci);

        return riga;
    } // fine della closure


    /**
     * Corpo della tabella
     */
    private String creaTableNonUsate() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, "Nazionalità non utilizzate");
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaNonUsate);
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo


    /**
     * Corpo del footer
     * Sovrascritto
     */
    @Override
    protected String elaboraFooterCorrelate() {
        String text = CostBio.VUOTO;

        text += A_CAPO;
        text += "==Voci correlate==";
        text += A_CAPO;
        text += "*[[" + DISCUSSIONI + tagPath + "Nazionalità]]";
        text += A_CAPO;
        text += "*[[" + tagPath + "Attività]]";
        text += A_CAPO;
        text += "*[[:Categoria:Bio nazionalità]]";
        text += A_CAPO;
        text += "*[[:Categoria:Bio attività]]";
        text += A_CAPO;
        text += "*[[Progetto:Biografie/Statistiche]]";
        text += A_CAPO;
        text += "*[https://it.wikipedia.org/w/index.php?title=Modulo:Bio/Plurale_nazionalità&action=edit Lista delle nazionalità nel modulo (protetto)]";
        text += A_CAPO;
        text += "*[https://it.wikipedia.org/w/index.php?title=Modulo:Bio/Plurale_attività&action=edit Lista delle attività nel modulo (protetto)]";

        return text;
    }// fine del metodo
//    }// fine del metodo

//    /**
//     * Numero AttNaz utilizzate
//     * Sovrascritto
//     */
//    protected int vociUsate() {
//        return
//    }// fine del metodo

}// end of class
