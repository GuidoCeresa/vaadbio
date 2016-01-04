package it.algos.vaadbio.bio;

import com.vaadin.data.Container;
import it.algos.webbase.web.entity.BaseEntity_;
import it.algos.webbase.web.entity.EM;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.table.ATable;
import org.vaadin.addons.lazyquerycontainer.LazyEntityContainer;

import javax.persistence.metamodel.Attribute;

/**
 * Created by gac on 25 nov 2015.
 * .
 */
public class BioTable extends ATable {

    public BioTable(ModulePop modulo) {
        super(modulo);
        this.setColumnHeader(Bio_.templateEsiste.getName(), "IS");
        this.setColumnHeader(Bio_.templateValido.getName(), "OK");
        this.setColumnHeader(Bio_.templatesUguali.getName(), "==");

    }// end of constructor

    /**
     * Creates the container
     * <p/>
     *
     * @return the container
     */
    protected Container createContainer() {
        LazyEntityContainer entityContainer = new LazyEntityContainer<Bio>(EM.createEntityManager(), Bio.class, 100, BaseEntity_.id.getName(), true, true, true);
        return entityContainer;
    }// end of method

}// end of class
