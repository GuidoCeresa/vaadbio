package it.algos.vaadbio.anno;

import it.algos.vaadbio.secolo.Secolo;
import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Anno.class)
public class Anno_ extends BaseEntity_ {
    public static volatile SingularAttribute<Anno, String> titolo;
    public static volatile SingularAttribute<Anno, Integer> ordinamento;
    public static volatile SingularAttribute<Anno, Secolo> secolo;
}// end of entity class
