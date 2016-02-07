package it.algos.vaadbio.nome;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Nome.class)
public class Nome_ extends BaseEntity_ {
    public static volatile SingularAttribute<Nome, String> nome;
    public static volatile SingularAttribute<Nome, Boolean> principale;
    public static volatile SingularAttribute<Nome, Boolean> nomeDoppio;
    public static volatile SingularAttribute<Nome, Nome> riferimento;

}// end of entity class
