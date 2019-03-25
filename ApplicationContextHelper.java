package org.dms.DMS.util;

import org.springframework.context.ApplicationContext;

/**
 * Helper class to get access to a spring managed bean instance from any class
 */
public class ApplicationContextHelper implements IApplicationContextHelper {
    private ApplicationContext applicationContext;
    private static ApplicationContextHelper applicationContextHelper = new ApplicationContextHelper();
    private static int counter = 0;

    private ApplicationContextHelper() {
        //throw new AssertionError();
    }

    public static ApplicationContextHelper getInstance() {
        return applicationContextHelper;
    }

    public synchronized void setApplicationContext(ApplicationContext context) {
        if(counter == 0) {
            applicationContext = context;
            counter++;
        }
    }
    
    @Override
    public Object getManagedBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    @Override
    public <T> T getManagedBean(String beanId, Class<T> type) {
    	return applicationContext.getBean(beanId, type);
    }
}
