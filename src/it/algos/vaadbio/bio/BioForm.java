package it.algos.vaadbio.bio;

import com.vaadin.data.Item;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import it.algos.vaadbio.lib.LibBio;
import it.algos.webbase.web.component.AHorizontalLayout;
import it.algos.webbase.web.field.*;
import it.algos.webbase.web.field.TextField;
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
    private static String LAR_CAMPO4 = "900px";
    private static String LAR_WIN = "1200px";
    private static String LAR_TAB = "1200px";
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
        AHorizontalLayout layout = new AHorizontalLayout();
        layout.setMargin(true);

        TabSheet tabsheet = new TabSheet();
        tabsheet.setWidth(LAR_TAB);
        tabsheet.addTab(creaTabWiki(), "Wiki");
        tabsheet.addTab(creaTabTemplates(), "Templates");
        tabsheet.addTab(creaTabAnagraficiValidi(), "Anagrafici");
        tabsheet.addTab(creaTabCronoValidi(), "Crono");
        tabsheet.addTab(creaTabLocalitaValidi(), "Località");
        tabsheet.addTab(creaTabAttivitaValidi(), "Attività");
        tabsheet.addTab(creaTabDidascalie(), "Didascalie");

        layout.addComponent(tabsheet);
        return layout;
    }// end of method

//    /**
//     * Populate the map to bind item properties to fields.
//     * <p>
//     * Crea e aggiunge i campi.<br>
//     * Implementazione di default nella superclasse.<br>
//     * I campi vengono recuperati dal Modello.<br>
//     * I campi vengono creti del tipo grafico previsto nella Entity.<br>
//     * Se si vuole aggiungere un campo (solo nel form e non nel Modello),<br>
//     * usare il metodo sovrascritto nella sottoclasse
//     * invocando prima (o dopo) il metodo della superclasse.
//     * Se si vuole un layout completamente diverso sovrascrivere
//     * senza invocare il metodo della superclasse
//     */
//    @Override
//    public void createFields() {
//        Field field = null;
//        super.createFields();
////        Attribute[] attributes = {Bio_.tmplBioServer, Bio_.tmplBioStandard};
////        field = new TextArea(Bio_.tmplBioServer.getName());
////        field.setColumns(NUM_COL);
////        field.setRows(NUM_ROWS);
////        addField(attributes[0], field);
////        field = new TextArea(Bio_.tmplBioStandard.getName());
////        field.setColumns(NUM_COL);
////        field.setRows(NUM_ROWS);
////        addField(attributes[1], field);
//
//    }// end of method


    private Component creaTabWiki() {
        Layout layout = new AFormLayout();

        layout.addComponent(getField(Bio_.pageid));
        layout.addComponent(getField(Bio_.title));
        layout.addComponent(getField(Bio_.nome));
        layout.addComponent(getField(Bio_.cognome));
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
        layout.setMargin(true);
        layout.setSpacing(true);
        HorizontalLayout layoutOr = new HorizontalLayout();
        layoutOr.setSpacing(true);

        layoutOr.addComponent(getField(Bio_.tmplBioServer));
        layoutOr.addComponent(LibBio.spazio(4));
        layoutOr.addComponent(getField(Bio_.tmplBioStandard));

        originalBox = (CheckBoxField) getField(Bio_.templatesUguali);
        status = originalBox.getValue();

        replicatedBox = new CheckBoxField(Bio_.templatesUguali.getName(), status);
        layout.addComponent(replicatedBox);
        layout.addComponent(layoutOr);

        return layout;
    }// end of method


    private Component creaTabAnagraficiValidi() {
        Layout layoutSin = new AFormLayout();
        Layout layoutDex = new AFormLayout();
        Field field;

        field = new TextField("Nome", getField(Bio_.nome).getValue().toString());
        layoutSin.addComponent(field);
        field = new TextField("Cognome", getField(Bio_.cognome).getValue().toString());
        layoutSin.addComponent(field);
        field = new TextField("Sesso", getField(Bio_.sesso).getValue().toString());
        layoutSin.addComponent(field);

        layoutDex.addComponent(getField(Bio_.nomeValido));
        layoutDex.addComponent(getField(Bio_.cognomeValido));
        layoutDex.addComponent(getField(Bio_.sessoValido));

        return new AHorizontalLayout(layoutSin, LibBio.spazio(12), layoutDex);
    }// end of method


    private Component creaTabCronoValidi() {
        VerticalLayout layout = new VerticalLayout();
        AHorizontalLayout layoutH;

        Layout layoutSin = new AFormLayout();
        Layout layoutDex = new AFormLayout();
        Layout layoutPunta = new AFormLayout();
        Layout layoutFooter = new AFormLayout();

        layoutSin.addComponent(getField(Bio_.giornoMeseNascita));
        layoutSin.addComponent(getField(Bio_.annoNascita));
        layoutSin.addComponent(getField(Bio_.giornoMeseMorte));
        layoutSin.addComponent(getField(Bio_.annoMorte));

        layoutDex.addComponent(getField(Bio_.giornoMeseNascitaValido));
        layoutDex.addComponent(getField(Bio_.annoNascitaValido));
        layoutDex.addComponent(getField(Bio_.giornoMeseMorteValido));
        layoutDex.addComponent(getField(Bio_.annoMorteValido));

        layoutPunta.addComponent(getField(Bio_.giornoNatoPunta));
        layoutPunta.addComponent(getField(Bio_.annoNatoPunta));
        layoutPunta.addComponent(getField(Bio_.giornoMortoPunta));
        layoutPunta.addComponent(getField(Bio_.annoMortoPunta));

        layoutFooter.addComponent(getField(Bio_.noteNascita));
        layoutFooter.addComponent(getField(Bio_.noteMorte));

        layoutH = new AHorizontalLayout();
        layoutH.setSpacing(true);
        layoutH.addComponent(layoutSin);
        layoutH.addComponent(LibBio.spazio(4));
        layoutH.addComponent(layoutDex);
        layoutH.addComponent(LibBio.spazio(4));
        layoutH.addComponent(layoutPunta);

        layout.addComponent(layoutH);
        layout.addComponent(layoutFooter);

        return layout;
    }// end of method

    private Component creaTabLocalitaValidi() {
        VerticalLayout layout = new VerticalLayout();
        AHorizontalLayout layoutH;

        Layout layoutSin = new AFormLayout();
        Layout layoutDex = new AFormLayout();
        Layout layoutFooter = new AFormLayout();

        layoutSin.addComponent(getField(Bio_.luogoNascita));
        layoutSin.addComponent(getField(Bio_.luogoNascitaLink));
        layoutSin.addComponent(getField(Bio_.luogoMorte));
        layoutSin.addComponent(getField(Bio_.luogoMorteLink));

        layoutDex.addComponent(getField(Bio_.luogoNascitaValido));
        layoutDex.addComponent(getField(Bio_.luogoNascitaLinkValido));
        layoutDex.addComponent(getField(Bio_.luogoMorteValido));
        layoutDex.addComponent(getField(Bio_.luogoMorteLinkValido));

        layoutFooter.addComponent(getField(Bio_.luogoNascitaAlt));
        layoutFooter.addComponent(getField(Bio_.luogoMorteAlt));

        layoutH = new AHorizontalLayout();
        layoutH.setSpacing(true);
        layoutH.addComponent(layoutSin);
        layoutH.addComponent(LibBio.spazio(12));
        layoutH.addComponent(layoutDex);

        layout.addComponent(layoutH);
        layout.addComponent(layoutFooter);

        return layout;
    }// end of method

    private Component creaTabAttivitaValidi() {
        VerticalLayout layout = new VerticalLayout();
        AHorizontalLayout layoutH;

        Layout layoutSin = new AFormLayout();
        Layout layoutDex = new AFormLayout();
        Layout layoutPunta = new AFormLayout();
        Layout layoutFooter = new AFormLayout();

        layoutSin.addComponent(getField(Bio_.attivita));
        layoutSin.addComponent(getField(Bio_.attivita2));
        layoutSin.addComponent(getField(Bio_.attivita3));
        layoutSin.addComponent(getField(Bio_.nazionalita));

        layoutDex.addComponent(getField(Bio_.attivitaValida));
        layoutDex.addComponent(getField(Bio_.attivita2Valida));
        layoutDex.addComponent(getField(Bio_.attivita3Valida));
        layoutDex.addComponent(getField(Bio_.nazionalitaValida));

        layoutPunta.addComponent(getField(Bio_.attivitaPunta));
        layoutPunta.addComponent(getField(Bio_.attivita2Punta));
        layoutPunta.addComponent(getField(Bio_.attivita3Punta));
        layoutPunta.addComponent(getField(Bio_.nazionalitaPunta));

        layoutFooter.addComponent(getField(Bio_.attivitaAltre));

        layoutH = new AHorizontalLayout();
        layoutH.setSpacing(true);
        layoutH.addComponent(layoutSin);
        layoutH.addComponent(LibBio.spazio(4));
        layoutH.addComponent(layoutDex);
        layoutH.addComponent(LibBio.spazio(4));
        layoutH.addComponent(layoutPunta);

        layout.addComponent(layoutH);
        layout.addComponent(layoutFooter);

        return layout;
    }// end of method


    private Component creaTabDidascalie() {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(getField(Bio_.didascaliaGiornoNato));
        layout.addComponent(getField(Bio_.didascaliaGiornoMorto));
        layout.addComponent(getField(Bio_.didascaliaAnnoNato));
        layout.addComponent(getField(Bio_.didascaliaAnnoMorto));
        layout.addComponent(getField(Bio_.didascaliaListe));

        return layout;
    }// end of method


}// end of class
