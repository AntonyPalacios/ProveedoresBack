package com.fitbank.common;

import java.util.List;

public interface DetailField {

	public Object getValue();

	public void setValue(Object value);

	public String getAlias();

	public void setAlias(String alias);

	public String getName();

	public void setName(String name);

	public List<Dependence> getDependencies();

}
