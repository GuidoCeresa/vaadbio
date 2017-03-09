package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.attivita.Attivita;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nazionalita.Nazionalita;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;

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
    private static String KEY_MAP_VOCI_ATT_1 = "vociAttUno";
    private static String KEY_MAP_VOCI_ATT_2 = "vociAttDue";
    private static String KEY_MAP_VOCI_ATT_3 = "vociAttTre";

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
        int numVoci = Bio.count();
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
        text += "'''" + numUsate + "''' attività " + ref1 + " '''effettivamente utilizzate''' nelle  '''[[:Categoria:BioBot|" + LibNum.format(numVoci) + "]]''' " + ref2 + " voci biografiche che usano il [[template:Bio|template Bio]].";
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
        int numVociAttUno = 0;
        int numVociAttDue = 0;
        int numVociAttTre = 0;
        HashMap mappa = null;

        listaAllDistinctPlurali = Attivita.findDistinctPlurale();
        for (String plurale : listaAllDistinctPlurali) {
            listaAttività = Attivita.findAllByPlurale(plurale);
            numVociAttUno = 0;
            numVociAttDue = 0;
            numVociAttTre = 0;

            if (listaAttività != null && listaAttività.size() > 0) {
                for (Attivita attivita : listaAttività) {
                    numVociAttUno += attivita.countBioAttUno();
                    numVociAttDue += attivita.countBioAttDue();
                    numVociAttTre += attivita.countBioAttTre();
                }// end of for cycle
            }// end of if cycle

            if (numVociAttUno + numVociAttDue + numVociAttTre > 0) {
                mappa = new HashMap();
                mappa.put(KEY_MAP_PLURALE, plurale);
                mappa.put(KEY_MAP_VOCI_ATT_1, numVociAttUno);
                mappa.put(KEY_MAP_VOCI_ATT_2, numVociAttDue);
                mappa.put(KEY_MAP_VOCI_ATT_3, numVociAttTre);
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
        titoli.add("1° att");
        titoli.add("2° att");
        titoli.add("3° att");
        titoli.add("totale");

        return titoli;
    }// fine del metodo

    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRigheUsate() {
        ArrayList listaRighe = new ArrayList();

        for (HashMap mappa : listaUsate) {
            listaRighe.add(getRigaAttivita(mappa));
        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo

    /**
     * Restituisce l'array della riga del parametro per le attivita
     * La mappa contiene:
     * -plurale dell'attività
     * -numero di voci che nel campo attivita usano tutti records di attivita che hanno quel plurale
     */
    private ArrayList getRigaAttivita(HashMap mappa) {
        ArrayList riga = new ArrayList();
        String plurale = "";
        int numVociAttUno = 0;
        int numVociAttDue = 0;
        int numVociAttTre = 0;
        ArrayList<Attivita> listaAttivita;
        String tagCat = ":Categoria:";
        String tagListe = tagPath + "Attività/";
        String pipe = "|";
        String lista;
        String categoria = "";

        if (mappa != null) {
            plurale = (String) mappa.get(KEY_MAP_PLURALE);
            numVociAttUno = (int) mappa.get(KEY_MAP_VOCI_ATT_1);
            numVociAttDue = (int) mappa.get(KEY_MAP_VOCI_ATT_2);
            numVociAttTre = (int) mappa.get(KEY_MAP_VOCI_ATT_3);

            lista = tagListe + LibText.primaMaiuscola(plurale) + pipe + LibText.primaMinuscola(plurale);
            lista = LibWiki.setQuadre(lista);
            categoria = tagCat + LibText.primaMinuscola(plurale) + pipe + plurale;
            categoria = LibWiki.setQuadre(categoria);
        } else {
            lista = plurale;
        }// fine del blocco if-else

        //riga.add(getColore(mappa))
        riga.add(lista);
        riga.add(categoria);
        riga.add(numVociAttUno);
        riga.add(numVociAttDue);
        riga.add(numVociAttTre);
        riga.add(numVociAttUno + numVociAttDue + numVociAttTre);

        return riga;
    } // fine della closure


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
