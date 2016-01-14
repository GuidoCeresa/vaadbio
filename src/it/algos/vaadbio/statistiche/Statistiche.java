package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Created by gac on 14 gen 2016.
 * .
 */
public abstract class Statistiche {

    protected static String PATH = "Progetto:Biografie/";
    protected static String PAGINA_PROVA = "Utente:Biobot/2";
    protected String titoloPagina;


    /**
     * Costruttore completo
     */
    public Statistiche() {
        super();
        doInit();
    }// end of constructor


    /**
     */
    public void doInit() {
        elaboraPagina();
    }// end of method

    /**
     * Elaborazione principale della pagina
     * <p>
     * Costruisce head <br>
     * Costruisce body <br>
     * Costruisce footer <br>
     * Ogni blocco esce trimmato (inizio e fine) <br>
     * Gli spazi (righe) di separazione vanno aggiunti qui <br>
     * Registra la pagina <br>
     */
    protected void elaboraPagina() {
        boolean debug = Pref.getBool(CostBio.USA_DEBUG, false);
        String summary = LibWiki.getSummary();
        String testo = CostBio.VUOTO;
        String titolo;

        //header
        testo += this.elaboraHead();

        //body
        //a capo, ma senza senza righe di separazione
        testo += CostBio.A_CAPO;
        testo += this.elaboraBody();

        //footer
        //di fila nella stessa riga, senza ritorno a capo (se inizia con <include>)
        testo += this.elaboraFooter();

        //registra la pagina
        if (!testo.equals(CostBio.VUOTO)) {
            testo = testo.trim();

            if (debug) {
                titolo = PAGINA_PROVA;
            } else {
                titolo = titoloPagina;
            }// fine del blocco if-else

            Api.scriveVoce(titolo, testo, summary);

        }// fine del blocco if

    }// fine del metodo


    /**
     * Costruisce il testo iniziale della pagina (header)
     * <p>
     * Non sovrascrivibile <br>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     * Eventuali spazi gestiti da chi usa il metodo <br>
     */
    private String elaboraHead() {
        String text = CostBio.VUOTO;

        // valore di ritorno
        return text;
    }// fine del metodo


    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        String text = CostBio.VUOTO;

        return text;
    }// fine del metodo


    /**
     * Piede della pagina
     * <p>
     * Aggiungere (di solito) inizialmente la chiamata al metodo elaboraFooterSpazioIniziale <br>
     * Sovrascritto
     */
    protected String elaboraFooter() {
        return CostBio.VUOTO;
    }// fine del metodo

}// end of class
