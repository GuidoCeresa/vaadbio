package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Cost;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 17 apr 2016.
 * Pagina di controllo del progetto Antroponimi
 * - Progetto:Antroponimi/Liste nomi: Elenco dei xxx nomi '''differenti'''  utilizzati nelle yyyy voci biografiche con occorrenze maggiori di zz
 */
public class StatNomiListe extends StatNomi {

    private static String TITOLO_PAGINA = "Liste nomi";

    private ArrayList<Nome> listaNomi;
    private LinkedHashMap<Nome, Integer> mappaNomi;
    private long inizio = System.currentTimeMillis();
    private int mod = 0;
    private String modTxt;

    /**
     * Costruttore vuoto
     */
    public StatNomiListe() {
        super();
    }// end of constructor

    /**
     * Costruttore completo
     */
    public StatNomiListe(LinkedHashMap<Nome, Integer> mappaNomi) {
        this.mappaNomi = mappaNomi;
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
        if (mappaNomi == null) {
            mappaNomi = Nome.findMappaTaglioListe();
        }// end of if cycle
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        int numNomi = mappaNomi.size();
        int numVoci = Bio.count();
        int taglioVoci = Pref.getInteger(CostBio.TAGLIO_NOMI_ELENCO);

        text += A_CAPO;
        text += "==Nomi==";
        text += A_CAPO;
        text += "Elenco dei ";
        text += LibWiki.setBold(LibNum.format(numNomi));
        text += " nomi '''differenti'''  utilizzati nelle ";
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
        Nome nome;
        String nomeText;
        int num;
        int taglioPagine = Pref.getInteger(CostBio.TAGLIO_NOMI_PAGINA);
        String numText;
        String tag = "Persone di nome ";
        ArrayList titoli = new ArrayList();
        titoli.add(LibWiki.setBold("Nome"));
        titoli.add(LibWiki.setBold("Voci"));

        for (Map.Entry mappa : mappaNomi.entrySet()) {

            nome = (Nome) mappa.getKey();
            nomeText = nome.getNome();
            num = (Integer) mappa.getValue();
            numText = LibNum.format(num);
            if (num >= taglioPagine) {
                nomeText = tag + nomeText + CostBio.PIPE + nomeText;
                nomeText = LibWiki.setQuadre(nomeText);
                nomeText = LibWiki.setBold(nomeText);
            }// end of if cycle

            listaRiga = new ArrayList();
            listaRiga.add(nomeText);
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
            text += LibWiki.setRigaQuadre(LibText.levaCoda(PATH_NOMI, "/"));
            text += LibWiki.setRigaQuadre(PATH_NOMI + "Nomi");
            text += LibWiki.setRigaQuadre(PATH_NOMI + "Nomi doppi");
            text += LibWiki.setRigaQuadre(PATH_NOMI + "Didascalie");
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
        text += LibWiki.setRigaCat("Liste di persone per nome| ");
        text += LibWiki.setRigaCat("Progetto Antroponimi|Nomi");

        return text;
    }// fine del metodo

}// end of class
