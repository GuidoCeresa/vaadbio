package it.algos.vaadbio;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import it.algos.webbase.web.ui.AlgosUI;

/**
 * Crea l'interfaccia utente (User Interface) iniziale dell'applicazione
 * Qui viene regolato il theme base per tutta l'applicazione
 * <p>
 * Layout standard composto da:
 * Top      - una barra composita di menu e login
 * Body     - un placeholder per il portale della tavola/modulo in uso
 * Footer   - un striscia per eventuali informazioni (Algo, copyright, ecc)
 * <p>
 * Se le applicazioni specifiche vogliono una UI completamente differente,
 * possono sovrascrivere il metodo startUI() della superclasse
 */
@Theme("valo")
//@Theme("algos")
public class VaadbioUI extends AlgosUI {

    /**
     * Initializes this UI. This method is intended to be overridden by subclasses to build the view and configure
     * non-component functionality. Performing the initialization in a constructor is not suggested as the state of the
     * UI is not properly set up when the constructor is invoked.
     * <p>
     * The {@link VaadinRequest} can be used to get information about the request that caused this UI to be created.
     * </p>
     * Se viene sovrascritto dalla sottoclasse, deve (DEVE) richiamare anche il metodo della superclasse
     * di norma dopo (DOPO) aver effettuato alcune regolazioni <br>
     * Nella sottoclasse specifica viene eventualmente regolato il nome del modulo di partenza <br>
     *
     * @param request the Vaadin request that caused this UI to be created
     */
    @Override
    protected void init(VaadinRequest request) {
//        AlgosUI.DEBUG_GUI = true;
//        menuAddressModuloPartenza = "Bolla";
        super.init(request);
    }// end of method


    /**
     * Aggiunge i moduli specifici dell'applicazione (oltre a LogMod, VersMod, PrefMod)
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse per aggiungere i moduli alla menubar dell'applicazione <br>
     * Chiama il metodo  addModulo(...) della superclasse per ogni modulo previsto nella barra menu
     */
    @Override
    protected void addModuli() {
        // this.addModulo(new BollaMod());
    }// end of method

}//end of UI class

