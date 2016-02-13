package it.algos.vaadbio.mese;


import com.vaadin.server.FontAwesome;
import it.algos.webbase.web.module.ModulePop;
import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo specifico
 */
@SuppressWarnings("serial")
public class MeseMod extends ModulePop {

    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public MeseMod() {
        super(Mese.class, FontAwesome.GEAR);
    }// end of constructor

}// end of class

