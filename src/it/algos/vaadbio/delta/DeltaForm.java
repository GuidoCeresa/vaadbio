package it.algos.vaadbio.delta;

import com.vaadin.data.Item;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import it.algos.webbase.web.form.ModuleForm;
import it.algos.webbase.web.lib.LibText;
import it.algos.webbase.web.module.ModulePop;

import javax.persistence.metamodel.Attribute;

/**
 * Created by gac on 11 feb 2016.
 * .
 */
public class DeltaForm extends ModuleForm {


    private static String LAR_CAMPO = "400px";
    private static int NUM_COL = 50;
    private static int NUM_ROWS = 3;


    public DeltaForm(Item item, ModulePop module) {
        super(item, module);
    }// end of constructor

    /**
     * Attributes used in this form
     * Di default prende dal modulo
     * Può essere sovrascritto se c'è un Form specifico
     *
     * @return a list containing all the attributes used in this form
     */
    protected Attribute<?, ?>[] getAttributesList() {
        return new Attribute[]{Delta_.valido};
    }// end of method

    /**
     * Create and add a field for each property declared for this form
     * <p>
     * Crea e aggiunge i campi.<br>
     * Implementazione di default nella superclasse.<br>
     * I campi vengono recuperati dal Modello (di default) <br>
     * I campi vengono creti del tipo grafico previsto nella Entity.<br>
     * Se si vuole aggiungere un campo (solo nel form e non nel Modello),<br>
     * usare il metodo sovrascritto nella sottoclasse
     * invocando prima (o dopo) il metodo della superclasse.
     * Se si vuole un layout completamente diverso sovrascrivere
     * senza invocare il metodo della superclasse
     */
    @Override
    public void createFields() {
        TextArea fieldArea;
        TextField fieldText;
        Attribute[] attributes = {Delta_.sorgente, Delta_.differenza};

        //--inserisce il primo campo in alto
        fieldArea = new TextArea(LibText.primaMaiuscola(Delta_.sorgente.getName()));
        fieldArea.setColumns(NUM_COL);
        fieldArea.setRows(NUM_ROWS);
        addField(attributes[0], fieldArea);

        //--aggiunge i campi (ce n'è uno solo) previsti nel form e costruiti del tipo standard
        super.createFields();
        //--modifica la sola larghezza del campo testo già costruito
        fieldText = (TextField) getField(Delta_.valido.getName());
        fieldText.setWidth(LAR_CAMPO);

        //--aggiunge l'ultimo campo
        fieldArea = new TextArea(LibText.primaMaiuscola(Delta_.differenza.getName()));
        fieldArea.setColumns(NUM_COL);
        fieldArea.setRows(NUM_ROWS);
        addField(attributes[1], fieldArea);
    }// end of method


}// end of class
