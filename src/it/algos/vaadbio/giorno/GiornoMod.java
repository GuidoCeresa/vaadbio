package it.algos.vaadbio.giorno;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.navigator.NavPlaceholder;

/**
 * Gestione (minimale) del modulo
 * Passa alla superclasse il nome e la classe specifica
 */
@SuppressWarnings("serial")
public class GiornoMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Giorno";


    public GiornoMod() {
        super(Giorno.class, MENU_ADDRESS);
    }// end of constructor

    /**
     * Create the MenuBar Item for this module
     * <p>
     * Invocato dal metodo AlgosUI.creaMenu()
     * PUO essere sovrascritto dalla sottoclasse
     *
     * @param menuBar     a cui agganciare il menuitem
     * @param placeholder in cui visualizzare il modulo
     * @return menuItem appena creato
     */
    @Override
    public MenuBar.MenuItem createMenuItem(MenuBar menuBar, NavPlaceholder placeholder) {
        return super.createMenuItem(menuBar, placeholder, FontAwesome.TASKS);
    }// end of method

}// end of class

