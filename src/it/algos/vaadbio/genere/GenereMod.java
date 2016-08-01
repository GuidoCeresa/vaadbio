package it.algos.vaadbio.genere;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import it.algos.vaadbio.ciclo.CicloDownload;
import it.algos.vaadbio.lib.CostBio;
import it.algos.vaadbio.professione.ProfessioneService;
import it.algos.vaadbio.professione.Professione_;
import it.algos.webbase.domain.pref.Pref;
import it.algos.webbase.web.dialog.ConfirmDialog;
import it.algos.webbase.web.module.ModulePop;
import javax.persistence.metamodel.Attribute;

/**
 * Gestione (minimale) del modulo specifico
 */
@SuppressWarnings("serial")
public class GenereMod extends ModulePop {

    // indirizzo interno del modulo (serve nei menu)
    public static String MENU_ADDRESS = "Plurali";

    /**
     * Costruttore senza parametri
     * <p/>
     * Invoca la superclasse passando i parametri:
     * (obbligatorio) la Entity specifica
     * (facoltativo) etichetta del menu (se manca usa il nome della Entity)
     * (facoltativo) icona del menu (se manca usa un'icona standard)
     */
    public GenereMod() {
        super(Genere.class, MENU_ADDRESS, FontAwesome.GEAR);
    }// end of constructor

    /**
     * Crea i campi visibili nella lista (table)
     * <p/
     * Come default spazzola tutti i campi della Entity <br>
     * Può essere sovrascritto (facoltativo) nelle sottoclassi specifiche <br>
     * Serve anche per l'ordine con cui vengono presentati i campi nella lista <br>
     */
    protected Attribute<?, ?>[] creaFieldsList() {
        return new Attribute[]{
                Genere_.singolare,
                Genere_.plurale,
                Genere_.sesso
        };
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
        addCommandDownload(menuItem);
    }// end of method

    /**
     * Comando bottone/item Download
     *
     * @param menuItem a cui agganciare il bottone/item
     */
    private void addCommandDownload(MenuBar.MenuItem menuItem) {
        menuItem.addItem("Download", FontAwesome.COG, new MenuBar.Command() {
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                boolean usaDialoghi = Pref.getBoolean(CostBio.USA_DIALOGHI_CONFERMA, true);
                if (usaDialoghi) {
                    String newMsg;
                    newMsg = "Esegue un ciclo (<b><span style=\"color:green\">update</span></b>) di controllo del plurale attività per genere</br>";
                    ConfirmDialog dialog = new ConfirmDialog(CostBio.MSG, newMsg,
                            new ConfirmDialog.Listener() {
                                @Override
                                public void onClose(ConfirmDialog dialog, boolean confirmed) {
                                    if (confirmed) {
                                        esegueDownload();
                                    }// end of if cycle
                                }// end of inner method
                            });// end of anonymous inner class
                    UI ui = getUI();
                    if (ui != null) {
                        dialog.show(ui);
                    } else {
                        Notification.show("Avviso", "Devi prima entrare nel modulo Bio per eseguire questo comando", Notification.Type.WARNING_MESSAGE);
                    }// end of if/else cycle
                } else {
                    new CicloDownload();
                }// fine del blocco if-else
            }// end of method
        });// end of anonymous class
    }// end of method


    /**
     * Esegue il download
     */
    public void esegueDownload() {
        GenereService.download();
    }// end of method

}// end of class

