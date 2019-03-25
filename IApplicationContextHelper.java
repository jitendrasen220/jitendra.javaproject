package org.dms.DMS.util;

public interface IApplicationContextHelper {

	Object getManagedBean(String beanId);
	public <T> T getManagedBean(String beanId, Class<T> type);

}
