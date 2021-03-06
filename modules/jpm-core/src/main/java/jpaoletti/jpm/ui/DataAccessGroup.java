package jpaoletti.jpm.ui;

import java.util.List;
import jpaoletti.jpm.core.*;
import jpaoletti.jpm.security.core.*;

public class DataAccessGroup extends AbstractDataAccess {

    @Override
    public void delete(PMContext ctx, Object object) throws PMException {
        try {
            getConnector(ctx).removeGroup((PMSecurityUserGroup) object);
        } catch (PMSecurityException ex) {
            ctx.getPresentationManager().error(ex);
        }
    }

    @Override
    public Object refresh(PMContext ctx, Object o) throws PMException {
        if (o != null) {
            PMSecurityUserGroup instance = (PMSecurityUserGroup) o;
            return getItem(ctx, "", instance.getName());
        } else {
            return null;
        }
    }

    @Override
    public Object getItem(PMContext ctx, String property, String value) throws PMException {
        try {
            return getConnector(ctx).getGroup(value);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
        }
        return null;
    }

    @Override
    public List<?> list(PMContext ctx, EntityFilter filter, ListFilter lfilter, ListSort sort, Integer from, Integer count) throws PMException {
        try {
            List<PMSecurityUserGroup> list = getConnector(ctx).getGroups();
            Integer f = (from == null) ? 0 : from;
            Integer t = (count == null) ? list.size() : (from + count > list.size() ? list.size() : from + count);
            return list.subList(f, t);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
            return null;
        }
    }

    @Override
    public void update(PMContext ctx, Object instance) throws PMException {
        try {
            getConnector(ctx).updateGroup((PMSecurityUserGroup) instance);
        } catch (PMSecurityException e) {
            ctx.getPresentationManager().error(e);
        }
    }

    @Override
    public void add(PMContext ctx, Object instance) throws PMException {
        try {
            getConnector(ctx).addGroup((PMSecurityUserGroup) instance);
        } catch (Exception e) {
            ctx.getPresentationManager().error(e);
        }
    }

    private PMSecurityConnector getConnector(PMContext ctx) {
        return PresentationManager.getPm().getSecurityConnector(ctx);
    }

    @Override
    public Long count(PMContext ctx) throws PMException {
        return new Long(list(ctx, null, null, null, null, null).size());
    }

    @Override
    public EntityFilter createFilter(PMContext ctx) throws PMException {
        return new EntityFilter();
    }
}
