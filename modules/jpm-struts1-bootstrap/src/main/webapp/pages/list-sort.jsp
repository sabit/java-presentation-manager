<%@include file="../inc/tag-libs.jsp" %>
<script type="text/javascript">
    PM_register(function(){
        $('#operationsort').remove();
        $("#col_${ctx.entityContainer.list.sort.fieldId}")
        .append("<img alt='' src=\"${es.templatePath}img/arrow-${(ctx.entityContainer.list.sort.desc)?'down':'up'}.gif\" />");
        //<c:forEach var="field" items="${pmfn:displayedFields(entity, 'sort')}">
        $("#col_${field.id}").addClass('sortable').click(function(){
            $("input[name='order']").val('${field.id}');
            $("input[name='desc']").val(($("input[name='desc']").val()=='true')?'false':'true');
            $("#listform").submit();
        });
        //</c:forEach>
    });
</script>