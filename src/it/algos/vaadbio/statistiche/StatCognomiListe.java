package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.cognome.Cognome;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 05 nov 2016.
 * Pagina di controllo del progetto Antroponimi
 * - Progetto:Antroponimi/Liste cognomi: Elenco dei xxx cognomi '''differenti'''  utilizzati nelle yyyy voci biografiche con occorrenze maggiori di zz
 */
public class StatCognomiListe extends StatCognomi {

    private static String TITOLO_PAGINA = "Liste cognomi";

    private ArrayList<Cognome> listaCognomi;
    private LinkedHashMap<Cognome, Integer> mappaCognomi;
    private long inizio = System.currentTimeMillis();
    private int mod = 0;
    private String modTxt;


    /**
     * Costruttore vuoto
     */
    public StatCognomiListe() {
        super();
    }// end of constructor

    /**
     * Costruttore completo
     */
    public StatCognomiListe(LinkedHashMap<Cognome, Integer> mappaCognomi) {
        this.mappaCognomi = mappaCognomi;
        doInit();
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
        titoloPagina = TITOLO_PAGINA;
    }// fine del metodo

    /**
     * Costruisce una mappa di valori varii
     * Sovrascritto
     */
    @Override
    protected void elaboraMappaBiografie() {
        if (mappaCognomi == null) {
            mappaCognomi = Cognome.findMappaTaglioListe();
        }// end of if cycle
    }// fine del metodo


    /**
     * Corpo della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        int numCognomi = mappaCognomi.size();
        int numVoci = Bio.count();
        int taglioVoci = Pref.getInt(CostBio.TAGLIO_NOMI_ELENCO);

        text += A_CAPO;
        text += "==Nomi==";
        text += A_CAPO;
        text += "Elenco dei ";
        text += LibWiki.setBold(LibNum.format(numCognomi));
        text += " cognomi '''differenti'''  utilizzati nelle ";
        text += LibWiki.setBold(LibNum.format(numVoci));
        text += " voci biografiche con occorrenze maggiori di ";
        text += LibWiki.setBold(taglioVoci);
        text += A_CAPO;
        text += creaElenco();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo con elenco delle pagine
     */
    private String creaElenco() {
        String testoTabella = CostBio.VUOTO;
        String riga = CostBio.VUOTO;
        ArrayList listaPagine = new ArrayList();
        ArrayList listaRiga;
        HashMap mappaTavola = new HashMap();
        Cognome cognome;
        String cognomeText;
        int num;
        int taglioPagine = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA);
        String numText;
        String tag = "Persone di cognome ";
        ArrayList titoli = new ArrayList();
        titoli.add(LibWiki.setBold("Cognome"));
        titoli.add(LibWiki.setBold("Voci"));

        for (Map.Entry mappa : mappaCognomi.entrySet()) {

            cognome = (Cognome) mappa.getKey();
            cognomeText = cognome.getCognome();
            num = (Integer) mappa.getValue();
            numText = LibNum.format(num);
            if (num >= taglioPagine) {
                cognomeText = tag + cognomeText + CostBio.PIPE + cognomeText;
                cognomeText = LibWiki.setQuadre(cognomeText);
                cognomeText = LibWiki.setBold(cognomeText);
            }// end of if cycle

            listaRiga = new ArrayList();
            listaRiga.add(cognomeText);
            listaRiga.add(num);
            listaPagine.add(listaRiga);

        }// end of for cycle
        mappaTavola.put(Cost.KEY_MAPPA_SORTABLE_BOOLEAN, true);
        mappaTavola.put(Cost.KEY_MAPPA_TITOLI, titoli);
        mappaTavola.put(Cost.KEY_MAPPA_RIGHE_LISTA, listaPagine);
        testoTabella = LibWiki.creaTable(mappaTavola);

        return testoTabella;
    }// fine del metodo

    /**
     * Corpo del footer
     * Sovrascritto
     */
    @Override
    protected String elaboraFooterCorrelate() {
        String text = CostBio.VUOTO;

        if (usaFooterCorrelate) {
            text += "==Voci correlate==";
            text += A_CAPO;
            text += LibWiki.setRigaQuadre(LibText.levaCoda(PATH_ANTRO, "/"));
            text += LibWiki.setRigaQuadre(PATH_ANTRO + "Cognomi");
            text += LibWiki.setRigaQuadre(PATH_ANTRO + "Didascalie");
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Categorie del footer
     * Sovrascritto
     */
    @Override
    protected String elaboraFooterCategorie() {
        String text = CostBio.VUOTO;

        text += A_CAPO;
        text += LibWiki.setRigaCat("Liste di persone per cognome| ");
        text += LibWiki.setRigaCat("Progetto Antroponimi|Cognomi");

        return text;
    }// fine del metodo

}// end of class
