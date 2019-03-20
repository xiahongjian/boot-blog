<#assign active="article" title="文章管理" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">文章管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th width="35%">文章标题</th>
                <th width="15%">发布时间</th>
                <th>浏览量</th>
                <th>所属分类</th>
                <th width="8%">发布状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#if (articles.list)?? && articles.list?size &gt; 0 >
            <#list articles.list as post>
            <tr cid="${post.id}">
                <td>
                    <a href="${request.contextPath}/admin/article/${post.id}">${post.title!}</a>
                </td>
                <td>${post.created?string('yyyy-MM-dd HH:mm:ss')}</td>
                <td>${post.hits!0}</td>
                <td>${post.categories!}</td>
                <td>
                    <#if post.status == 'publish'>
                    <span class="label label-success">已发布</span>
                    <#elseif post.status = 'draft'>
                    <span class="label label-default">草稿</span>
                    </#if>
                </td>
                <td>
                    <a href="${request.contextPath}/admin/article/${post.id}"
                       class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-edit"></i> <span>编辑</span></a>
                    <a href="javascript:void(0)" onclick="delPost(${post.id});"
                       class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-trash-o"></i> <span>删除</span></a>
                    <#if post.status == 'publish'>
                    <a class="btn btn-warning btn-sm waves-effect waves-light m-b-5" href="${request.contextPath}/article/<#if !isBlank(post.slug)>${post.slug}<#else>${post.id}</#if>"
                       target="_blank"><i
                            class="fa fa-rocket"></i> <span>预览</span></a>
                    </#if>
                </td>
            </tr>
            </#list>
            </#if>
            </tbody>
        </table>
        <@com.pageAdminNav pageInfo=articles />
    </div>
</div>

<#include "./footer.ftl" />

<script type="text/javascript">
    var tale = new $.tale();
    function delPost(cid) {
        tale.alertConfirm({
            title:'确定删除该文章吗?',
            then: function () {
               tale.post({
                   url : '${request.contextPath}/admin/article/delete',
                   data: {id: cid},
                   success: function (result) {
                       if(result && result.success){
                           tale.alertOkAndReload('文章删除成功');
                       } else {
                           tale.alertError(result.msg || '文章删除失败');
                       }
                   }
               });
           }
        });
    }
</script>

</body>
</html>