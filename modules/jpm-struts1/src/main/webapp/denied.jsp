<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="inc/tag-libs.jsp" %>
<pm:page title="titles.index" loading="true">
    <div class="content">
        <h2 class="title"><pmfn:message key="access.denied" /></h2>
        <a href="javascript:history.back()"><pmfn:message key="errors.back" /></a><br/>
        <img src="${es.templatePath}/images/denied.png" alt="" />
    </div>
</pm:page>
