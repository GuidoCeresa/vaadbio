package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.anno.Anno;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;
import java.util.HashMap;

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
        text += ". Vengono prese in considerazione tutte e solo le voci biografiche che hanno valori validi e certi degli anni di nascita e morte della persona.";
        text += " Sono riportati gli anni che hanno un numero di nati o morti maggiore di " + soglia;
        text += A_CAPO;
        text += creaTable();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo della tabella
     */
    protected String creaTable() {
        HashMap<String, Object> mappa = new HashMap<String, Object>();

        mappa.put(Cost.KEY_MAPPA_TITOLI, listaTitoli());
        mappa.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaRighe());
        mappa.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);

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
        titoli.add("nati " + LibWiki.setRef(ref1));
        titoli.add("morti " + LibWiki.setRef(ref2));

        return titoli;
    }// fine del metodo

    /**
     * Singole righe della tabella
     * Sovrascritto
     */
    private ArrayList listaRighe() {
        ArrayList listaRighe = new ArrayList();
        ArrayList<Anno> anni = Anno.findAll();
        int delta = 2780;
        Anno annoTmp = null;
        int nati = 0;
        int morti = 0;

//        for (int k = 0; k < 10; k++) {
//            annoTmp = anni.get(k + delta);
//            nati = annoTmp.countBioNati();
//            morti = annoTmp.countBioMorti();
//            if (nati > soglia || morti > soglia) {
//                listaRighe.add(getRigaAnno(annoTmp, nati, morti));
//            }// end of if cycle
//        }// end of for cycle

        for (Anno anno : anni) {
            nati = anno.countBioNati();
            morti = anno.countBioMorti();
            if (nati > soglia || morti > soglia) {
                listaRighe.add(getRigaAnno(anno, nati, morti));
            }// end of if cycle
        } // fine del ciclo for-each

        return listaRighe;
    }// fine del metodo

    /**
     * Restituisce l'array della riga del parametro per le nazionalita
     */
    private ArrayList getRigaAnno(Anno anno, int nati, int morti) {
        ArrayList riga = new ArrayList();
        String nome = anno.getNome();

        riga.add(nome);
        riga.add(nati);
        riga.add(morti);

        return riga;
    }// fine del metodo

}// end of class
