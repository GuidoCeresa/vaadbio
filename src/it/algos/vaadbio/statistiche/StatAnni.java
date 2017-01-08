package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by gac on 25 gen 2016.
 * .
 */
public class StatAnni extends Statistiche {

    private int soglia;

    /**
     * Costruttore completo
     */
    public StatAnni() {
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
        titoloPagina = "Anni";

        usaHeadToc = false; //--tipicamente sempre true.
        usaFooterCorrelate = true;

        soglia = Pref.getInt(CostBio.NUM_VOCI_ANNI, 300);
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        String ref1 = "Potenzialmente dal [[1000 a.C.]] al [[{{CURRENTYEAR}}]]";

        text += "==Anni==";
        text += A_CAPO;
        text += "Statistiche dei nati e morti per ogni anno ";
        text += LibWiki.setRef(ref1);
        text += ". Vengono prese in considerazione '''solo''' le voci biografiche che hanno valori '''validi e certi''' degli anni di nascita e morte della persona.";
        text += " Sono riportati gli anni che hanno un numero di nati o morti maggiore di '''" + soglia + "'''";
        text += A_CAPO;
        text += creaTable();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo della tabella
     */
    protected String creaTable() {
        HashMap<String, Object> mappa = new HashMap<>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, listaTitoli());
        mappa.put(Cost.KEY_MAPPA_DESTRA_LISTA, listaColonneDestra());
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRighe());
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, false);

        return LibWiki.creaTable(mappa);
    }// fine del metodo

    /**
     * Titoli della tabella
     */
    private ArrayList<String> listaTitoli() {
        ArrayList<String> titoli = new ArrayList<>();

        String ref1 = "Il [[template:Bio|template Bio]] della voce biografica deve avere un valore valido al parametro '''annoNascita'''.";
        String ref2 = "Il [[template:Bio|template Bio]] della voce biografica deve avere un valore valido al parametro '''annoMorte'''.";

        titoli.add("anno");
        titoli.add("nati nell'anno " + LibWiki.setRef(ref1));
        titoli.add("morti nell'anno " + LibWiki.setRef(ref2));

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

        return colonneDestra;
    }// fine del metodo

    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRighe() {
        ArrayList listaRighe = new ArrayList();
        HashMap<Anno, ArrayList> mappa = new HashMap<>();
        ArrayList<Integer> listaVoci;
        Vector vettoreNati = Anno.getMappaNati();
        Vector vettoreMorti = Anno.getMappaMorti();
        Object[] obj;
        Anno anno = null;
        long natiLong = 0;
        long mortiLong = 0;
        int nati = 0;
        int morti = 0;

        //--devo unire i due vettori
        //--prima costruisco una mappa col primo
        //--poi aggiungo l'altro
        for (int k = 0; k < vettoreNati.size(); k++) {
            obj = (Object[]) vettoreNati.get(k);
            anno = (Anno) obj[0];
            natiLong = (long) obj[1];

            listaVoci = new ArrayList<>();
            listaVoci.add((int) natiLong);
            mappa.put(anno, listaVoci);
        }// endof for cycle

        for (int k = 0; k < vettoreMorti.size(); k++) {
            obj = (Object[]) vettoreMorti.get(k);
            anno = (Anno) obj[0];
            natiLong = (long) obj[1];

            if (mappa.containsKey(anno)) {
                listaVoci = (ArrayList) mappa.get(anno);
                listaVoci.add((int) natiLong);
            } else {
                listaVoci = new ArrayList<>();
                listaVoci.add((int) 0);
                listaVoci.add((int) natiLong);
                mappa.put(anno, listaVoci);
            }// end of if/else cycle
        }// endof for cycle

        for (HashMap.Entry entry : mappa.entrySet()) {
            anno = (Anno) entry.getKey();
            listaVoci = (ArrayList<Integer>) entry.getValue();
            nati = listaVoci.get(0);
            if (listaVoci.size() > 1) {
                morti = listaVoci.get(1);
            } else {
                morti = 0;
            }// end of if/else cycle
            if (nati > soglia || morti > soglia) {
                listaRighe.add(getRigaAnno(anno, nati, morti));
            }// end of if cycle

        }// end of for cycle

        return listaRighe;
    }// fine del metodo

    /**
     * Restituisce l'array della riga del parametro per le nazionalita
     */
    private ArrayList getRigaAnno(Anno anno, int nati, int morti) {
        ArrayList riga = new ArrayList();
        String nome = anno.getTitolo();
        String titoloNati = anno.getTitoloListaNati();
        String titoloMorti = anno.getTitoloListaMorti();

        riga.add(LibWiki.setQuadre(nome));
        if (nati > 0) {
            riga.add(LibWiki.setQuadre(titoloNati + "|" + LibNum.format(nati)));
        } else {
            riga.add("&nbsp;");
        }// end of if/else cycle
        if (morti > 0) {
            riga.add(LibWiki.setQuadre(titoloMorti + "|" + LibNum.format(morti)));
        } else {
            riga.add("&nbsp;");
        }// end of if/else cycle

        return riga;
    }// fine del metodo

}// end of class
