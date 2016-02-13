package it.algos.vaadbio.delta;


import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.module.ModulePop;

import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo specifico
 */
@SuppressWarnings("serial")
public class DeltaMod extends ModulePop {

    /**
     * Costruttore senza parametri
     * <p>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public DeltaMod() {
        super(Delta.class, FontAwesome.GEAR);
    }// end of constructor


    /**
     * Crea i campi visibili nella lista (table)
     * <p>
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella lista <br>
     */
    protected Attribute<?, ?>[] creaFieldsList() {
        return new Attribute[]{Delta_.valido, Delta_.sorgente, Delta_.differenza};
    }// end of method


    /**
     * Returns the form used to edit an item. <br>
     * The concrete subclass must override for a specific Form.
     *
     * @param item singola istanza della classe
     * @return the Form
     */
    public ModuleForm createForm(Item item) {
        return (new DeltaForm(item, this));
    }// end of method


    /**
     * Crea i sottomenu specifici del modulo
     * <p>
     * Invocato dal metodo AlgosUI.addModulo()
     * Sovrascritto dalla sottoclasse
     *
     * @param menuItem principale del modulo
     */
    @Override
    public void addSottoMenu(MenuBar.MenuItem menuItem) {
        addCommandTitolo(menuItem);
        addCommandNome(menuItem);
        addCommandCognome(menuItem);
    }// end of method

    /**
     * Comando bottone/item per le differenze di Title
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandTitolo(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Titolo", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                DeltaService.titolo();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item per le differenze di Nome
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandNome(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Nome", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                DeltaService.nome();
            }// end of method
        });// end of anonymous class
    }// end of method

    /**
     * Comando bottone/item per le differenze di Cognome
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandCognome(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Cognome", FontAwesome.BEER, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                DeltaService.cognome();
            }// end of method
        });// end of anonymous class
    }// end of method

}// end of class

