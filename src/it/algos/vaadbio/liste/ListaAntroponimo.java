package it.algos.vaadbio.liste;

import it.algos.vaadbio.bio.Bio;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Created by gac on 31 ott 2016.
 * <p>
 * Crea la lista antroponima
 * <p>
 * Crea la lista dei nomi e la carica sul server wiki
 * Crea la lista dei cognomi e la carica sul server wiki
 */
public abstract class ListaAntroponimo extends ListaBio {

    /**
     * Costruttore senza parametri
     */
    protected ListaAntroponimo() {
    }// fine del costruttore

    /**
     * Costruttore completo
     */
    public ListaAntroponimo(Object oggetto) {
        super(oggetto);
    }// fine del costruttore


    /**
     * Regola alcuni (eventuali) parametri specifici della sottoclasse
     * <p>
     * Nelle sottoclassi va SEMPRE richiamata la superclasse PRIMA di regolare localmente le variabili <br>
     * Sovrascritto
     */
    @Override
    protected void elaboraParametri() {
        super.elaboraParametri();

        // head
        usaHeadTocIndice = true;
        usaHeadIncipit = true;
        tagHeadTemplateProgetto = "antroponimi";

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = true;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 100);

        // footer
        usaFooterPortale = Pref.getBool(CostBio.USA_FOOTER_PORTALE_NOMI, true);
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            usaFooterCategorie = false;
        } else {
            usaFooterCategorie = Pref.getBool(CostBio.USA_FOOTER_CATEGORIE_NOMI, true);
        }// end of if/else cycle
    }// fine del metodo


    /**
     * Costruisce la chiave del paragrafo
     * Sovrascritto
     */
    @Override
    protected String getChiave(Bio bio) {
        return LibBio.getChiavePerAttivita(bio, tagParagrafoNullo);
    }// fine del metodo


    /**
     * Piede della pagina
     * Sovrascritto
     */
    @Override
    protected String elaboraFooter() {
        String text = CostBio.VUOTO;
        boolean usaInclude = usaFooterPortale || usaFooterCategorie;

        if (usaFooterPortale) {
            text += CostBio.A_CAPO;
            text += "{{Portale|antroponimi}}";
        }// end of if cycle

        if (usaFooterCategorie) {
            text += CostBio.A_CAPO;
            text += elaboraCategorie();
        }// end of if cycle

        if (usaInclude) {
            text = CostBio.A_CAPO + LibBio.setNoIncludeMultiRiga(text);
        }// end of if cycle

        return text;
    }// fine del metodo


    /**
     * Categorie al piede della pagina
     * Sovrascritto
     */
    protected String elaboraCategorie() {
        return "";
    }// fine del metodo

    /**
     * Controlla che la modifica sia sostanziale
     * Se il flag è false, registra sempre
     * Se il flag è vero, controlla la differenza del testo
     * Sovrascritto
     */
    @Override
    protected boolean checkPossoRegistrare(String titolo, String testo) {
        if (Pref.getBool(CostBio.USA_REGISTRA_SEMPRE_PERSONA, false)) {
            return true;
        } else {
            return LibBio.checkModificaSostanziale(titolo, testo, tagHeadTemplateAvviso, "}}");
        }// end of if/else cycle
    }// fine del metodo


}// fine della classe
