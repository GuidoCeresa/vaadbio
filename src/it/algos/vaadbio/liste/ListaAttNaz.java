package it.algos.vaadbio.liste;

import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.domain.pref.Pref;

/**
 * Created by gac on 04/02/17.
 * .
 */
public abstract class ListaAttNaz extends ListaBio {

    final static String PROGETTO_BIOGRAFIE_ATTIVITÀ = "Progetto:Biografie/Attività/";
    final static String PROGETTO_BIOGRAFIE_NAZIONALITÀ = "Progetto:Biografie/Nazionalità/";


    /**
     * Costruttore senza parametri
     */
    ListaAttNaz() {
    }// fine del costruttore


    /**
     * Costruttore completo
     *
     * @param oggetto
     */
    ListaAttNaz(Object oggetto) {
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
        tagHeadTemplateProgetto = "biografie";

        // body
        usaSuddivisioneParagrafi = true;
        usaBodyRigheMultiple = false;
        usaBodyDoppiaColonna = false;
        usaSottopagine = true;
        usaTitoloParagrafoConLink = true;
        usaTaglioVociPagina = false;
        maxVociPagina = Pref.getInt(CostBio.TAGLIO_NOMI_PAGINA, 50);

        // footer
        usaFooterPortale = true;
        if (Pref.getBool(CostBio.USA_DEBUG, false)) {
            usaFooterCategorie = false;
        } else {
            usaFooterCategorie = true;
        }// end of if/else cycle
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


}// end of class
