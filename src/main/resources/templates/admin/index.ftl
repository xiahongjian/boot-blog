<#assign active = "home" title = "管理中心" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">Tale仪表盘</h4>
    </div>

    <div class="row">
        <div class="col-sm-6 col-lg-3">
            <div class="mini-stat clearfix bx-shadow bg-info">
                <span class="mini-stat-icon"><i class="fa fa-quote-right" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    发表了<span class="counter">${statistics.articles}</span>篇文章
                </div>
            </div>
        </div>
        <div class="col-sm-6 col-lg-3">
            <div class="mini-stat clearfix bg-purple bx-shadow">
                <span class="mini-stat-icon"><i class="fa fa-comments-o" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    收到了<span class="counter">${statistics.comments}</span>条留言
                </div>
            </div>
        </div>

        <div class="col-sm-6 col-lg-3">
            <div class="mini-stat clearfix bg-success bx-shadow">
                <span class="mini-stat-icon"><i class="fa fa-cloud-upload" aria-hidden="true"></i></span>
                <div class="mini-stat-info text-right">
                    上传了<span class="counter">${statistics.attaches}</span>个附件
                </div>
            </div>
        </div>

    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">最新文章</h4>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <#list articles as article>
                        <li class="list-group-item">
                            <span class="badge badge-primary"
                                  title="${article.commentsNum!0}条评论">${article.commentsNum!0}</span>
                            <a target="_blank" href="${request.contextPath}/article/${article.id}">${article.title}</a>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">最新留言</h4>
                </div>
                <div class="panel-body">
                    <#if comments.list?size == 0>
                    <div class="alert alert-warning">
                        还没有收到留言.
                    </div>
                    <#else>
                    <ul class="list-group">
                        <#list comments.list as comment>
                        <li class="list-group-item">
                            <#if comment.url??>
                            <a href="${comment.url!}" target="_blank">${comment.author!}</a>
                            <#else>
                            ${comment.author!}
                            </#if>
                            于${comment.created?string("yyyy-MM-dd")} ：<a href="${request.contextPath}/article/${comment.cid}#comments" target="_blank">${comment.content}</a>
                        </li>
                        </#list>
                    </ul>
                    </#if>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">系统日志</h4>
                </div>
                <div class="panel-body">
                    <ul class="list-group">
                        <#list logs as log>
                        <li class="list-group-item">
                            <span>${log.created?string("yyyy-MM-dd HH:mm:ss")} => <#if actionsMap?? && actionsMap[log.action]??>${actionsMap[log.action]}<#else>${log.action!}</#if></span>
                        </li>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "./footer.ftl" >
</body>
</html>