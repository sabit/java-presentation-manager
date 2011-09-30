package jpaoletti.jpm;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jpaoletti.jpm.core.PMCoreConstants;
import jpaoletti.jpm.core.PMSession;
import jpaoletti.jpm.core.PresentationManager;
import jpaoletti.jpm.core.operations.OperationCommandSupport;
import jpaoletti.jpm.struts.PMEntitySupport;
import jpaoletti.jpm.struts.PMStrutsConstants;
import jpaoletti.jpm.struts.PMStrutsContext;

public class GeneralFilter implements Filter, PMCoreConstants, PMStrutsConstants {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        req.setCharacterEncoding("UTF-8");
        /**
         * Optimization to avoid unused connections to data source.
         */
        if (isIgnored(req.getRequestURI())) {
            chain.doFilter(request, response);
        } else {
            final PMStrutsContext ctx = initJPMContext(req, response);
            try {
                chain.doFilter(request, response);
            } catch (ServletException e) {
                error(ctx, e);
                throw e;
            } catch (Exception e) {
                error(ctx, e);
            } finally {
                try {
                    ctx.getPersistenceManager().finish(ctx);
                } catch (Exception e) {
                    error(ctx, e);
                }
            }
        }
    }

    protected void error(PMStrutsContext ctx, Exception e) {
        if (ctx.getPresentationManager() != null) {
            ctx.getPresentationManager().error(e);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    protected boolean isIgnored(String s) {
        if (PresentationManager.getPm() != null) {
            final String[] ignoredExtensions = PresentationManager.getPm().getCfg().getProperty("struts-ignored-extensions", "css,gif,png,jpg,js").split(",");
            for (String ext : ignoredExtensions) {
                if (s.endsWith("." + ext.trim())) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    protected PMStrutsContext initJPMContext(HttpServletRequest request, ServletResponse response) {
        final PresentationManager pm = PresentationManager.getPm();
        request.setAttribute("pm", pm);
        if (pm == null) {
            return null;
        }
        final PMEntitySupport o = (PMEntitySupport) request.getSession().getAttribute(ENTITY_SUPPORT);
        if (o == null) {
            PMEntitySupport es = PMEntitySupport.getInstance();
            es.setContext_path(request.getContextPath());
            request.getSession().setAttribute(ENTITY_SUPPORT, es);
        }
        final PMSession pmsession = PMEntitySupport.getPMSession(request);
        final String sessionId = (pmsession != null) ? pmsession.getId() : "";
        final PMStrutsContext ctx = new PMStrutsContext(sessionId);
        request.setAttribute("ctx", ctx);
        ctx.setRequest(request);
        ctx.setResponse((HttpServletResponse) response);
        ctx.getRequest().setAttribute(PM_CONTEXT, ctx);

        for (Object object : request.getParameterMap().entrySet()) {
            Map.Entry entry = (Map.Entry) object;
            ctx.put("param_" + entry.getKey(), entry.getValue());
        }

        final Object pmid = ctx.getParameter("pmid");
        ctx.put(OperationCommandSupport.PM_ID, pmid);
        ctx.getRequest().setAttribute("pmid", pmid);

        final Object item = ctx.getParameter("item");
        ctx.put(OperationCommandSupport.PM_ITEM, item);
        ctx.getRequest().setAttribute("item", item);
        ctx.getPersistenceManager(); // deprecated. Used to back compat
        return ctx;
    }
}