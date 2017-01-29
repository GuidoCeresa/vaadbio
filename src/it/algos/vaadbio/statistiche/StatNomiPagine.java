package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.nome.Nome;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibNum;
import it.algos.webbase.web.lib.LibText;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gac on 17 apr 2016.
 * Pagina di controllo del progetto Antroponimi
 * - Progetto:Antroponimi/Nomi: Elenco dei xxx nomi che hanno più di yy ricorrenze nelle voci biografiche
 */
public class StatNomiPagine extends StatNomi {

    private static String TITOLO_PAGINA = "Nomi";
    private long inizio = System.currentTimeMillis();
    private int mod = 0;
    private String modTxt;


    /**
     * Costruttore vuoto
     */
    public StatNomiPagine() {
        super();
    }// end of constructor

    /**
     * Costruttore completo
     */
    public StatNomiPagine(LinkedHashMap<String, Integer> mappaNomi) {
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
            mappaNomi = Nome.findMappaTaglioPagina();
        }// end of if cycle
    }// fine del metodo

    /**
     * Corpo della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraBody() {
        String text = CostBio.VUOTO;
        int numVoci = mappaNomi.size();
        int taglioPagine = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA);

        text += A_CAPO;
        text += "==Nomi==";
        text += A_CAPO;
        text += "Elenco dei ";
        text += LibWiki.setBold(LibNum.format(numVoci));
        text += " nomi che raggiungono o superano le ";
        text += LibWiki.setBold(taglioPagine);
        text += " ricorrenze nelle voci biografiche";
        text += A_CAPO;
        text += creaElenco();
        text += A_CAPO;

        return text;
    }// fine del metodo

    /**
     * Corpo con elenco delle pagine
     */
    private String creaElenco() {
        String text = CostBio.VUOTO;
        String riga ;
        String nomeText;
        int num;
        String numText;
        String tag = "Persone di nome ";

        for (Map.Entry<String, Integer> mappa : mappaNomi.entrySet()) {
            nomeText = mappa.getKey();
            num =  mappa.getValue();
            numText = LibNum.format(num);
            numText = LibWiki.setBold(numText);
            numText = "(" + numText + ")";

            riga = tag + nomeText + CostBio.PIPE + nomeText;
            riga = LibWiki.setQuadre(riga);
            riga += CostBio.SPAZIO;
            riga += numText;
            riga = "*" + riga + CostBio.A_CAPO;
            text += riga;
        }// end of for cycle

        return LibWiki.listaDueColonne(text);
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
            text += LibWiki.setRigaQuadre(PATH_ANTRO + "Nomi doppi");
            text += LibWiki.setRigaQuadre(PATH_ANTRO + "Liste nomi");
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
        text += LibWiki.setRigaCat("Liste di persone per nome| ");
        text += LibWiki.setRigaCat("Progetto Antroponimi|Nomi");

        return text;
    }// fine del metodo

}// end of class
