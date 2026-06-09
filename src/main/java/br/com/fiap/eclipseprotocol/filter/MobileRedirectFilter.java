package br.com.fiap.eclipseprotocol.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MobileRedirectFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String mobileRedirect = request.getParameter("mobile_redirect");
        if (mobileRedirect != null && !mobileRedirect.isBlank()
                && request.getRequestURI().contains("/oauth2/authorization/github")) {
            request.getSession().setAttribute("mobile_redirect", mobileRedirect);
        }

        filterChain.doFilter(request, response);
    }
}