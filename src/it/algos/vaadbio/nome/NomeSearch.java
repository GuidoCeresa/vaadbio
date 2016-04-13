package it.algos.vaadbio.nome;

import com.vaadin.data.Container;
import it.algos.vaadbio.bio.Bio_;
import it.algos.webbase.web.module.ModulePop;
import it.algos.webbase.web.search.SearchManager;

import java.util.ArrayList;

/**
 * Created by gac on 11 apr 2016.
 * .
 */
@SuppressWarnings("serial")
public class NomeSearch extends SearchManager {

    public NomeSearch(ModulePop module) {
        super(module);
    }// end of constructor

    @Override
    public ArrayList<Container.Filter> createFilters() {
        ArrayList<Container.Filter> filters = new ArrayList<Container.Filter>();
        filters.add(createStringFilter(Nome_.nome, SearchType.MATCHES));
        return filters;
    }// end of method

}// end of class
