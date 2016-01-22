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
//        int numRecords = BioGrails.count()
//        String numVoci = LibTesto.formatNum(numRecords)
//        numVoci = LibWiki.setBold(numVoci)
        int numUsate = Nazionalita.countDistinctPlurale();
//        int numUsate2= "        String query=\"select distinct nazionalita.plurale from Nazionalita nazionalita\";\n";
//        int numNonUsate = vociNonUsate()
//        String nomeAttNazMinuscolo = LibTesto.primaMinuscola(nomeAttNaz)
//        String modulo = TITOLO + SPAZIO + nomeAttNazMinuscolo
        String ref1 = "Le nazionalità sono quelle [[Discussioni progetto:Biografie/Nazionalità|'''convenzionalmente''' previste]] dalla comunità ed [[Modulo:Bio/Plurale_nazionalità|inserite nell' elenco]] utilizzato dal [[template:Bio|template Bio]]";
        String ref2 = "La '''differenza''' tra le voci della categoria e quelle utilizzate è dovuta allo specifico utilizzo del [[template:Bio|template Bio]] ed in particolare all'uso del parametro Categorie=NO";
        String ref3 = "Si tratta di $nomeAttNazMinuscolo '''originariamente''' discusse ed inserite nell'[[$modulo|elenco]] che non sono mai state utilizzate o che sono state in un secondo tempo sostituite da altre denominazioni";
        ref1 = LibWiki.setRef(ref1);
        ref2 = LibWiki.setRef(ref2);
        ref3 = LibWiki.setRef(ref3);

        text += "==Nazionalità usate==";
        text += A_CAPO;
        text += "'''" + numUsate + "''' nazionalità " + ref1 + " '''effettivamente utilizzate''' nelle [[:Categoria:BioBot|voci biografiche]] che usano il [[template:Bio|template Bio]].";
        text += A_CAPO;
        text += elaboraTableUsate();
        text += A_CAPO;
        text += A_CAPO;
        text += "==Nazionalità non usate==";
        text += A_CAPO;
        text += "'''$numNonUsate''' $nomeAttNazMinuscolo presenti nel [[$modulo|modulo]] ma '''non utilizzate''' $ref3 in nessuna voce biografica";
//        text += A_CAPO;
//        text += this.creaTabellaNonUsate()
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo della tabella
     */
    private String elaboraTableUsate() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, "lista,categoria,voci");
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRigheUsate());
//        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

        return LibWiki.creaTable(mappa);
    }// fine del metodo

    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRigheUsate() {
        ArrayList listaRighe = new ArrayList();
        ArrayList<String> listaAllDistinctPlurali;

        listaAllDistinctPlurali = Nazionalita.findDistinctPlurale();
        for (int k = 0; k < 10; k++) {
            listaRighe.add(getRigaNazionalita(listaAllDistinctPlurali.get(k)));
        }// end of for cycle


//        for (String plurale : listaAllDistinctPlurali) {
//            listaRighe.add(getRigaNazionalita(plurale));
//        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo


    /**
     * Restituisce l'array delle riga del parametro per le nazionalita
     * La mappa contiene:
     * -plurale dell'attività
     * -numero di voci che nel campo nazionalita usano tutti records di nazionalita che hanno quel plurale
     */
    private ArrayList getRigaNazionalita(String plurale) {
        ArrayList riga = new ArrayList();
        ArrayList<Nazionalita> listaNazionalita;
        int numVoci = 0;
        String tagCat = ":Categoria:";
        String tagListe = PATH + "Nazionalità/";
        String pipe = "|";
        String lista;
        String categoria = "";
//        boolean usaDueColonne = Pref.getBool(LibBio.USA_DUE_COLONNE_STATISTICHE_NAZIONALITA, true);
        boolean usaDueColonne = true;

        if (plurale != null && !plurale.equals(CostBio.VUOTO)) {
            listaNazionalita = Nazionalita.findAllByPlurale(plurale);
            if (listaNazionalita != null && listaNazionalita.size() > 0) {
                for (Nazionalita nazionalita : listaNazionalita) {
                    numVoci += nazionalita.countBio();
                }// end of for cycle
            }// end of if cycle

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
     * Corpo del footer
     * Sovrascritto
     */
    @Override
    protected String elaboraFooterCorrelate() {
        String text = CostBio.VUOTO;

        text += A_CAPO;
        text += "==Voci correlate==";
        text += A_CAPO;
        text += "*[[" + DISCUSSIONI_PATH + "Nazionalità]]";
        text += A_CAPO;
        text += "*[[" + PATH + "Attività]]";
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
