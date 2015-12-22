package it.algos.vaadbio.liste;

import it.algos.vaad.wiki.LibWiki;
import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.webbase.domain.pref.Pref;

import java.util.ArrayList;

/**
 * Created by gac on 21 dic 2015.
 * .
 */
public abstract class ListaBio {

    public static String PAGINA_PROVA = "Utente:Biobot/2";
    protected static String TAG_INDICE = "__FORCETOC__";
    protected static String TAG_NO_INDICE = "__NOTOC__";

    protected String titoloPagina;
    protected ArrayList<Bio> listaBiografie;
    protected int numPersone = 0;

    /**
     * Costruttore
     */
    public ListaBio() {
        super();
    }// fine del costruttore

    protected void doInit() {
//        numDidascalie = 0
//        elaboraParametri()
        elaboraTitolo();

        elaboraListaBiografie();
        elaboraPagina();
    }// fine del metodo


    /**
     * Titolo della pagina da creare/caricare su wikipedia
     * Sovrascritto
     */
    protected void elaboraTitolo() {
    }// fine del metodo


    /**
     * Costruisce una lista di biografie che hanno una valore valido per il link specifico
     * Sovrascritto
     */
    protected void elaboraListaBiografie() {
        if (listaBiografie != null) {
            numPersone = listaBiografie.size();
        }// fine del blocco if
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
//        EditBio paginaModificata
//        Risultato risultato

        if (numPersone > 0) {
            //header
            testo += this.elaboraHead();

            //body
            //a capo, ma senza senza righe di separazione
            testo += CostBio.A_CAPO;
            testo += this.elaboraBody();

            //footer
            //di fila nella stessa riga, senza ritorno a capo (se inizia con <include>)
            testo += this.elaboraFooter();
        }// fine del blocco if

        //registra la pagina
        if (!testo.equals(CostBio.VUOTO)) {
            testo = testo.trim();
            if (debug) {
                testo = LibWiki.setBold(titoloPagina) + CostBio.A_CAPO + testo;
//                paginaModificata = new EditBio(PAGINA_PROVA, testo, summary);
//                registrata = paginaModificata.registrata;
            } else {
//                paginaModificata = new EditBio(titoloPagina, testo, summary);
//                registrata = paginaModificata.registrata;
            }// fine del blocco if-else
        }// fine del blocco if

    }// fine del metodo

    /**
     * Costruisce il testo iniziale della pagina (header)
     * <p>
     * Non sovrascrivibile <br>
     * Posiziona il TOC <br>
     * Posiziona il ritorno (eventuale) <br>
     * Posizione il template di avviso <br>
     * Posiziona l'incipit della pagina (eventuale) <br>
     * Ritorno ed avviso vanno (eventualmente) protetti con 'include' <br>
     * Ogni blocco esce trimmato (per l'inizio) e con un solo ritorno a capo per fine riga. <br>
     * Eventuali spazi gestiti da chi usa il metodo <br>
     */
    private String elaboraHead() {
        String testo = CostBio.VUOTO;

        return testo;
    }// fine del metodo


    /**
     * Corpo della pagina
     * Decide se c'è la doppia colonna
     * Controlla eventuali template di rinvio
     * Sovrascritto
     */
    protected String elaboraBody() {
        String testo = CostBio.VUOTO;

//        boolean usaColonne = usaDoppiaColonna
//        int maxRigheColonne = Pref.getInt(LibBio.MAX_RIGHE_COLONNE)
//        testo = elaboraBodyDidascalie()
//
//        if (usaColonne && (numPersone > maxRigheColonne)) {
//            testo = WikiLib.listaDueColonne(testo.trim())
//        }// fine del blocco if
//
//        testo = elaboraTemplate(testo)

        return testo;
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

    protected String getTitoloPagina() {
        return titoloPagina;
    }// end of getter method

    protected ArrayList<Bio> getListaBiografie() {
        return listaBiografie;
    }// end of getter method

    protected int getNumPersone() {
        return numPersone;
    }// end of getter

}// fine della classe
