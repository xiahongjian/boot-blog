<#assign active="log" title="系统日志" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">系统日志</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th width="8%">动作</th>
                <th>内容</th>
                <th width="8%">作者</th>
                <th width="10%">IP</th>
                <th width="15%">时间</th>
            </tr>
            </thead>
            <tbody>
            <#if (logs.list)?? && logs.list?size &gt; 0 >
                <#list logs.list as log>
                    <tr cid="${log.id}">
                        <td>${log.id}</td>
                        <td>${log.action!}</td>
                        <td>${log.data!}</td>
                        <td>${log.authorId!'-'}</td>
                        <td>${log.ip!'-'}</td>
                        <td>${log.created?string('yyyy-MM-dd HH:mm:ss')}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <@com.pageAdminNav pageInfo=logs />
    </div>
</div>

<#include "./footer.ftl" />

</body>
</html>