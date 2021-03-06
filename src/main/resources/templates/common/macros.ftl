<#macro pageNav pageInfo prevText nextText prefix>
    <ol class="page-navigator">
        <#if pageInfo.hasPreviousPage>
            <li class="prev"><a href="/${prefix}/${pageInfo.prevPage}">${prevText}</a></li>
        </#if>

        <#list pageInfo.navigatepageNums as index>
            <li <#if index == pageInfo.pageNum>class="current"</#if>><a href="/page/${index}">${index}</a></li>
        </#list>

        <#if pageInfo.hasNextPage>
            <li class="next"><a href="/${prefix}/${pageInfo.nextPage}">${nextText}</a></li>
        </#if>
    </ol>
</#macro>

<#macro pageAdminNav pageInfo params=''>
    <div class="clearfix"></div>
    <ul class="pagination m-b-5 pull-right">
        <#if pageInfo.hasPreviousPage>
            <li>
                <a href="?page=${pageInfo.prePage}<#if params?length &gt; 0>&${params}</#if>" aria-label="Previous"><i class="fa fa-angle-left"></i>&nbsp;上一页</a>
            </li>
        </#if>

        <#list pageInfo.navigatepageNums as nav>
            <li <#if nav == pageInfo.pageNum> class="active"</#if>><a href="?page=${nav}<#if params?length &gt; 0>&${params}</#if>">${nav}</a></li>
        </#list>

        <#if pageInfo.hasNextPage>
            <li><a href="?page=${pageInfo.nextPage}<#if params?length &gt; 0>&${params}</#if>" aria-label="Next">下一页&nbsp;<i class="fa fa-angle-right"></i></a></li>
        </#if>
        <li><span>共${pageInfo.pages}页</span></li>
    </ul>
</#macro>

<#macro resource_loader type cdn=true>
    <#if cdn>
        <#if type == "install_head">
        <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == "install_foot">
        <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
        <script src="//cdn.bootcss.com/jquery-validate/1.15.1/jquery.validate.min.js"></script>
        <script src="//cdn.bootcss.com/jquery-validate/1.15.1/localization/messages_zh.min.js"></script>
        <script src="//cdn.bootcss.com/jquery-steps/1.1.0/jquery.steps.min.js"></script>
        <#elseif type == "html5_respond">
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <#elseif type == "article_edit_head">
        <link href="//cdn.bootcss.com/multi-select/0.9.12/css/multi-select.min.css" rel="stylesheet"/>
        <link href="//cdn.bootcss.com/select2/3.4.8/select2.min.css" rel="stylesheet"/>
        <link href="${request.contextPath}/static/admin/plugins/mditor/css/mditor.min.css" rel="stylesheet">
        <link href="//cdn.bootcss.com/summernote/0.8.2/summernote.css" rel="stylesheet">
        <link href="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet">
        <#elseif type == "article_edit_foot">
        <script src="${request.contextPath}/static/admin/plugins/mditor/js/mditor.min.js"></script>
        <script src="//cdn.bootcss.com/wysihtml5/0.3.0/wysihtml5.min.js"></script>
        <script src="//cdn.bootcss.com/summernote/0.8.2/summernote.min.js"></script>
        <script src="//cdn.bootcss.com/summernote/0.8.2/lang/summernote-zh-CN.min.js"></script>
        <script src="//cdn.bootcss.com/multi-select/0.9.12/js/jquery.multi-select.min.js"></script>
        <script src="//cdn.bootcss.com/select2/3.4.8/select2.min.js"></script>
        <script src="//cdn.bootcss.com/jquery-toggles/2.0.4/toggles.min.js"></script>
        <script src="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.js"></script>
        <script src="//cdn.bootcss.com/to-markdown/3.1.0/to-markdown.min.js"></script>
        <#elseif type == "attach_head">
        <link href="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet">
        <#elseif type == "attach_foot">
        <script src="//cdn.bootcss.com/dropzone/4.3.0/min/dropzone.min.js"></script>
        <script src="//cdn.bootcss.com/clipboard.js/1.6.0/clipboard.min.js"></script>
        <#elseif type == "profile_foot">
        <script src="//cdn.bootcss.com/jquery-validate/1.15.1/jquery.validate.min.js"></script>
        <script src="//cdn.bootcss.com/jquery-validate/1.15.1/localization/messages_zh.min.js"></script>
        <#elseif type == "toggles">
        <script src="//cdn.bootcss.com/jquery-toggles/2.0.4/toggles.min.js"></script>
        <#elseif type == "login_head">
        <link href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == "login_foot">
        <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
        <#elseif type == "header">
        <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="//cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == "footer">
        <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="//cdn.bootcss.com/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
        <#elseif type == "jquery">
        <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
        <#elseif type == 'data-picker'>
        <script src="${request.contextPath}/static/admin/plugins/form-datepicker/js/bootstrap-datepicker.js"></script>
        <script src="${request.contextPath}/static/admin/plugins/form-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
        </#if>

    <#else>
        <#if type == 'install_head'>
            <link href="${request.contextPath}/static/admin/plugins/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <link href="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == 'install_foot'>
            <script src="${request.contextPath}/static/admin/plugins/jquery/3.2.1/jquery.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/bootstrap/3.3.7/js/bootstrap.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/jquery-validate/1.15.1/jquery.validate.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/jquery-validate/1.15.1/localization/messages_zh.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/jquery-steps/1.1.0/jquery.steps.min.js"></script>
        <#elseif type == 'html5_respond'>
            <script src="${request.contextPath}/static/admin/plugins/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/respond.js/1.3.0/respond.min.js"></script>
        <#elseif type == 'article_edit_head'>
            <link href="${request.contextPath}/static/admin/plugins/multi-select/0.9.12/css/multi-select.min.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/select2/3.4.8/select2.min.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/mditor/css/mditor.min.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/summernote/0.8.2/summernote.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet">
        <#elseif type == 'article_edit_foot'>
            <script src="${request.contextPath}/static/admin/plugins/mditor/js/mditor.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/wysihtml5/0.3.0/wysihtml5.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/summernote/0.8.2/summernote.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/summernote/0.8.2/lang/summernote-zh-CN.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/multi-select/0.9.12/js/jquery.multi-select.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/select2/3.4.8/select2.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/jquery-toggles/2.0.4/toggles.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/dropzone/4.3.0/min/dropzone.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/to-markdown/3.1.0/to-markdown.min.js"></script>
        <#elseif type == 'attach_head'>
            <link href="${request.contextPath}/static/admin/plugins/dropzone/4.3.0/min/dropzone.min.css" rel="stylesheet">
        <#elseif type == 'attach_foot'>
            <script src="${request.contextPath}/static/admin/plugins/dropzone/4.3.0/min/dropzone.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/clipboard.js/1.6.0/clipboard.min.js"></script>
        <#elseif type == 'profile_foot'>
            <script src="${request.contextPath}/static/admin/plugins/jquery-validate/1.15.1/jquery.validate.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/jquery-validate/1.15.1/localization/messages_zh.min.js"></script>
        <#elseif type == 'toggles'>
            <script src="${request.contextPath}/static/admin/plugins/jquery-toggles/2.0.4/toggles.min.js"></script>
        <#elseif type == 'login_head'>
            <link href="${request.contextPath}/static/admin/plugins/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
            <link href="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == 'login_foot'>
            <script src="${request.contextPath}/static/admin/plugins/jquery/3.2.1/jquery.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
        <#elseif type == 'header'>
            <link href="${request.contextPath}/static/admin/plugins/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <link href="${request.contextPath}/static/admin/plugins/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
            <link href="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.css" rel="stylesheet">
        <#elseif type == 'footer'>
            <script src="${request.contextPath}/static/admin/plugins/jquery/3.2.1/jquery.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/bootstrap/3.3.7/js/bootstrap.min.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/limonte-sweetalert2/6.4.1/sweetalert2.min.js"></script>
        <#elseif type == 'jquery'>
            <script src="${request.contextPath}/static/admin/plugins/jquery/3.2.1/jquery.min.js"></script>
        <#elseif type == 'data-picker'>
            <script src="${request.contextPath}/static/admin/plugins/form-datepicker/js/bootstrap-datepicker.js"></script>
            <script src="${request.contextPath}/static/admin/plugins/form-datepicker/js/locales/bootstrap-datepicker.zh-CN.js"></script>
        </#if>
    </#if>
</#macro>