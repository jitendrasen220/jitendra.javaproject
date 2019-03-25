package org.dms.DMS.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ApplicationFilter implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		ThreadLocalUtility.addToThreadLocal(Constants.HTTP_REQUEST, request);
		ThreadLocalUtility.addToThreadLocal(Constants.HTTP_RESPONSE, response);
		if (filterConfig == null)
			return;
		chain.doFilter(request, response);

	}
}