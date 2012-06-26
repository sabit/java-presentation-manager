<%@ taglib uri="/WEB-INF/tld/pmfn.tld" prefix="pmfn" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pm" %>
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="index.jsp"><pmfn:message key="${pm.title}"/></a>
            <div class="nav-collapse">
                <c:if test="${not empty ctx.pmsession}">
                    <pmfn:menu pmsession="${ctx.pmsession}"/>
                    <script type="text/javascript">
                        $(".menu-button-bar").remove();
                        var m = $("#menu");
                        m.removeClass("jqueryslidemenu");
                        m.children("script").remove();
                        m.children("ul").addClass("nav");
                        m.children("ul").children("li").addClass("dropdown");
                        m.children("ul").children("li").each(function(){
                            if($(this).children().size()>1){
                                $(this).children("ul").addClass("dropdown-menu");
                                $(this).children("a")
                                .addClass("dropdown-toggle")
                                .attr('data-toggle', 'dropdown')
                                .append("<b class='caret'></b>");
                            }
                        });
                    </script>
                    <%@include file="/WEB-INF/jsp/user-menu.jsp" %>
                </c:if>
                <c:if test="${empty ctx.pmsession}">
                    <div class="pull-right login-box">
                        <form class="form-inline" action="${es.context_path}/login.do" method="POST">
                            <span class="message_container hide login-error"></span>
                            <input id="username" name="username" type="text" class="input-small" placeholder="<pmfn:message key='login.username' />">
                            <input id="password" name="password" type="password" class="input-small" placeholder="<pmfn:message key='login.password' />">
                            <button type="submit" class="btn"><pmfn:message key='login.sign.in' />&nbsp;<i class="icon-arrow-right"></i></button>
                        </form>
                    </div>
                </c:if>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>
<script type="text/javascript" charset="utf-8">
    PM_register(function(){
        $("#username").keypress(function(e){
            if (e.keyCode == '13') {
                e.preventDefault();
                $("#password").focus();
            }
        });
        $("#username").focus();
    });
</script>