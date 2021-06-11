package edu.mrdrprof.app.aspects.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Alex Golub
 * @since 11-Jun-21, 3:51 PM
 */
//@Component
@Order(1)
public class MyFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {
    HttpServletResponse servletResponse = ((HttpServletResponse) response);
    servletResponse.setStatus(HttpServletResponse.SC_GONE);
  }
}
