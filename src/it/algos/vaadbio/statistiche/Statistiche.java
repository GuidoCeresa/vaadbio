package it.algos.vaadbio.statistiche;

import it.algos.vaad.wiki.Api;
import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.lib.LibTime;

import java.util.Date;

/**
 * Created by gac on 14 gen 2016.
 * .
 */
public abstract class Statistiche {

    protected static String PATH = "Progetto:Biografie/";
    protected static String PAGINA_PROVA = "Utente:Biobot/2";
    protected String titoloPagina;

    protected boolean usaHeadInclude; // vero per Sintesi
    protected boolean usaHeadTemplateAvviso; // uso del template StatBio
    protected String tagHeadTemplateAvviso; // template 'StatBio'
    protected boolean usaHeadRitorno; // prima del template di avviso


    // campi di una mappa per la table
    protected static final String KEY_MAPPA_TITOLI = "mappaTableListaTitoli";
    protected static final String KEY_MAPPA_TITOLI_SCURI = "mappaTableTitoliScuri";
    protected static final String KEY_MAPPA_LISTA = "mappaTableListaRighe";
    protected static final String KEY_MAPPA_CAPTION = "mappaTableCaption";
    protected static final String KEY_MAPPA_SORTABLE = "mappaTableSortable";
    protected static final String KEY_MAPPA_LISTA_SORTABLE = "mappaTableListaSortable";
    protected static final String KEY_MAPPA_FONT_SIZE = "mappaTableFontSize";
    protected static final String KEY_MAPPA_COLOR_TITOLI = "mappaTableColorTitoli";
    protected static final String KEY_MAPPA_COLOR_RIGHE = "mappaTableColorRighe";
    protected static final String KEY_MAPPA_BACKGROUND_TITOLI = "mappaTableBackgroundTitoli";
    protected static final String KEY_MAPPA_BACKGROUND_RIGHE = "mappaTableBackgroundRighe";
    protected static final String KEY_MAPPA_NUMERI_FORMATTATI = "mappaTableNumeriFormattati";
    protected static final String KEY_MAPPA_NUMERAZIONE_PROGRESSIVA = "mappaTableNumerazioneProgressiva";

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
        elaboraParametri();
        elaboraPagina();
    }// end of method

    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    protected void elaboraParametri() {
        // head
        usaHeadInclude = true; //--tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
        usaHeadRitorno = false; //--normalmente false. Sovrascrivibile da preferenze
        usaHeadTemplateAvviso = true; //--normalmente true. Sovrascrivibile nelle sottoclassi
        tagHeadTemplateAvviso = "StatBio"; //--Sovrascrivibile da preferenze

        // body

        // footer
    }// fine del metodo


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

        // Posiziona il ritorno
        text += elaboraRitorno();

        // Posizione il template di avviso
        text += elaboraTemplateAvviso();

        // Ritorno ed avviso vanno (eventualmente) protetti con 'include'
        text = elaboraInclude(text);

        // valore di ritorno
        return text;
    }// fine del metodo


    /**
     * Corpo della pagina
     * Sovrascritto
     */
    protected String elaboraBody() {
        return CostBio.VUOTO;
    }// fine del metodo


    /**
     * Piede della pagina
     * <p>
     * Aggiungere (di solito) inizialmente la chiamata al metodo elaboraFooterSpazioIniziale <br>
     * Sovrascritto
     */
    protected String elaboraFooter() {
        return "<noinclude>[[Categoria:Progetto Biografie|{{PAGENAME}}]]</noinclude>";
    }// fine del metodo


    /**
     * Costruisce il ritorno alla pagina 'madre'
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) l'utilizzo e la formulazione <br>
     */
    private String elaboraRitorno() {
        String text = CostBio.VUOTO;

        if (usaHeadRitorno) {
            if (!titoloPagina.equals(CostBio.VUOTO)) {
                text += "Torna a|" + titoloPagina;
                text = LibWiki.setGraffe(text);
            }// fine del blocco if
        }// fine del blocco if

        return text;
    }// fine del metodo


    /**
     * Costruisce il template di avviso
     * <p>
     * Non sovrascrivibile <br>
     * Parametrizzato (nelle sottoclassi) il nome del template da usare <br>
     */
    private String elaboraTemplateAvviso() {
        String text = CostBio.VUOTO;
        String dataCorrente = LibTime.getGioMeseAnnoLungo(new Date());

        if (usaHeadTemplateAvviso) {
            text += tagHeadTemplateAvviso;
            text += "|data=";
            text += dataCorrente.trim();
            text = LibWiki.setGraffe(text);
        }// end of if cycle

        return text;
    }// fine del metodo

    /**
     * Incorpora il testo iniziale nel tag 'include'
     * <p>
     * Non sovrascrivibile <br>
     * Tipicamente sempre true. Si attiva solo se c'è del testo (iniziale) da includere
     */
    private String elaboraInclude(String testoIn) {
        String testoOut = testoIn;

        if (usaHeadInclude) {
            testoOut = LibBio.setNoInclude(testoIn);
        }// fine del blocco if

        return testoOut;
    }// fine del metodo

}// end of class