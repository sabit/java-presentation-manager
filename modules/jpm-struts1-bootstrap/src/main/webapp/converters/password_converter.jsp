<%@include file="../inc/tag-libs.jsp" %>
<script type="text/javascript" src="${es.context_path}/js/pwdwidget.js"></script>
<div class='pwdwidgetdiv' id='thepwddiv${param.f}'></div>
<div id="d_${param.f}" class="control-group">
    <input type="password" value="" id="r_${param.f}" name="r_${param.f}" onkeyup="validatePassword('${entity.id}', '${param.f}');" />
    <span class="hide help-inline">${pmfn:message('pm.converter.password_converter.repeat')}</span>
</div>
<script  type="text/javascript" >
    var pwdwidget = new PasswordWidget('thepwddiv${param.f}','f_${param.f}');
    pwdwidget.txtShow = '${pmfn:message('pm.converter.password_converter.show')}';
    pwdwidget.txtMask = '${pmfn:message('pm.converter.password_converter.mask')}';
    pwdwidget.txtGenerate = '${pmfn:message('pm.converter.password_converter.generate')}';
    pwdwidget.txtWeak='${pmfn:message('pm.converter.password_converter.weak')}';
    pwdwidget.txtMedium='${pmfn:message('pm.converter.password_converter.medium')}';
    pwdwidget.txtGood='${pmfn:message('pm.converter.password_converter.good')}';
    pwdwidget.MakePWDWidget();
    $('#thepwddiv${param.f}').keyup(function(){
        validatePassword('${entity.id}', '${param.f}');
    });
</script>
<noscript><input type='password' id='f_${param.f}' name='f_${param.f}' /></noscript>
