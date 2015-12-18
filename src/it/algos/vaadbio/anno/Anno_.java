package it.algos.vaadbio.anno;

import it.algos.webbase.web.entity.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Anno.class)
public class Anno_ extends BaseEntity_ {
	public static volatile SingularAttribute<Anno, String> nome;
	public static volatile SingularAttribute<Anno, String> mese;
	public static volatile SingularAttribute<Anno, Integer> progressivo;
}// end of entity class
