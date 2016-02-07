package it.algos.vaadbio.bio;

import com.vaadin.data.Item;
import com.vaadin.ui.*;
import it.algos.webbase.web.field.CheckBoxField;
import it.algos.webbase.web.form.AFormLayout;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.module.ModulePop;

import javax.persistence.metamodel.Attribute;

/**
 * Created by gac on 26 nov 2015.
 * .
 */
public class BioForm extends ModuleForm {

    private static String LAR_CAMPO = "100px";
    private static String LAR_CAMPO2 = "187px";
    private static String LAR_CAMPO3 = "400px";
    private static String LAR_CAMPO4 = "800px";
    private static String LAR_WIN = "1200px";
    private static String LAR_TAB = "1000px";
    private static int NUM_COL = 25;
    private static int NUM_ROWS = 20;


    public BioForm(ModulePop module, Item item) {
        super(item, module);
//        doInit();
    }// end of constructor

    private void doInit() {
        setWidth(LAR_WIN);
    }// end of method

    /**
     * Create the UI component.
     * <p>
     * Retrieve the fields from the map and place them in the UI.
     * Implementazione di default nella superclasse.
     * I campi vengono allineati verticalmente.
     * Se si vuole aggiungere un campo, usare il metodo sovrascritto nella sottoclasse richiamando prima il metodo della superclasse.
     * Se si vuole un layout completamente differente, implementare il metodo sovrascritto SENZA richiamare il metodo della superclasse.
     */
    @Override
    protected Component createComponent() {
        TabSheet tabsheet = new TabSheet();
        tabsheet.setWidth(LAR_TAB);
        tabsheet.addTab(creaTabWiki(), "Wiki");
        tabsheet.addTab(creaTabTemplates(), "Templates");
        tabsheet.addTab(creaTabDidascalie(), "Didascalie");

        return tabsheet;
    }// end of method

    /**
     * Populate the map to bind item properties to fields.
     * <p>
     * Crea e aggiunge i campi.<br>
     * Implementazione di default nella superclasse.<br>
     * I campi vengono recuperati dal Modello.<br>
     * I campi vengono creti del tipo grafico previsto nella Entity.<br>
     * Se si vuole aggiungere un campo (solo nel form e non nel Modello),<br>
     * usare il metodo sovrascritto nella sottoclasse
     * invocando prima (o dopo) il metodo della superclasse.
     * Se si vuole un layout completamente diverso sovrascrivere
     * senza invocare il metodo della superclasse
     */
    @Override
    public void createFields() {
        TextArea field;
        super.createFields();
        Attribute[] attributes = {Bio_.tmplBioServer, Bio_.tmplBioStandard};
        field = new TextArea(Bio_.tmplBioServer.getName());
        field.setColumns(NUM_COL);
        field.setRows(NUM_ROWS);
        addField(attributes[0], field);
        field = new TextArea(Bio_.tmplBioStandard.getName());
        field.setColumns(NUM_COL);
        field.setRows(NUM_ROWS);
        addField(attributes[1], field);
    }// end of method

    private Component creaTabWiki() {
        Layout layout = new AFormLayout();
        Field field = null;

        field = getField(Bio_.pageid.getName());
        field.setWidth(LAR_CAMPO);
        field = getField(Bio_.title.getName());
        field.setWidth(LAR_CAMPO3);
        field = getField(Bio_.ultimaLettura.getName());
        field.setWidth(LAR_CAMPO2);
        field = getField(Bio_.ultimaElaborazione.getName());
        field.setWidth(LAR_CAMPO2);

        layout.addComponent(getField(Bio_.pageid));
        layout.addComponent(getField(Bio_.title));
        layout.addComponent(getField(Bio_.templateEsiste));
        layout.addComponent(getField(Bio_.templateValido));
        layout.addComponent(getField(Bio_.templatesUguali));
        layout.addComponent(getField(Bio_.ultimaLettura));
        layout.addComponent(getField(Bio_.ultimaElaborazione));

        return layout;
    }// end of method

    private Component creaTabTemplates() {
        CheckBoxField originalBox = null;
        CheckBoxField replicatedBox = null;
        boolean status;
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout layoutOr = new HorizontalLayout();
        layoutOr.setSpacing(true);

        layoutOr.addComponent(getField(Bio_.tmplBioServer));
        layoutOr.addComponent(getField(Bio_.tmplBioStandard));

        originalBox = (CheckBoxField) getField(Bio_.templatesUguali);
        status = originalBox.getValue();
        replicatedBox = new CheckBoxField(Bio_.templatesUguali.getName(), status);
        layout.addComponent(replicatedBox);
        layout.addComponent(layoutOr);

        return layout;
    }// end of method


    private Component creaTabDidascalie() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        Field field = null;

        field = getField(Bio_.didascaliaGiornoNato.getName());
        field.setWidth(LAR_CAMPO4);
        layout.addComponent(getField(Bio_.didascaliaGiornoNato));

        field = getField(Bio_.didascaliaGiornoMorto.getName());
        field.setWidth(LAR_CAMPO4);
        layout.addComponent(getField(Bio_.didascaliaGiornoMorto));

        field = getField(Bio_.didascaliaAnnoNato.getName());
        field.setWidth(LAR_CAMPO4);
        layout.addComponent(getField(Bio_.didascaliaAnnoNato));

        field = getField(Bio_.didascaliaAnnoMorto.getName());
        field.setWidth(LAR_CAMPO4);
        layout.addComponent(getField(Bio_.didascaliaAnnoMorto));

        field = getField(Bio_.didascaliaListe.getName());
        field.setWidth(LAR_CAMPO4);
        layout.addComponent(getField(Bio_.didascaliaListe));

        return layout;
    }// end of method


}// end of class
