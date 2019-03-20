<#assign active="page", title="页面管理" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">文章管理</h4>
    </div>
    <div class="col-md-12">
        <div class="pull-right">
            <a href="${request.contextPath}/admin/page/new" class="btn btn-success waves-effect waves-light m-b-5">添加新页面</a>
        </div>
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>页面名称</th>
                <th>页面路径</th>
                <th width="20%">发布时间</th>
                <th width="12%">发布状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list pages as post>
            <tr cid="${(post.id)!}">
                <td>${(post.title)!}</td>
                <td>${(post.slug)!}</td>
                <td>${post.created?string("yyyy-MM-dd HH:mm:ss")}</td>
                <td>
                    <#if post.status == "publish">
                    已发布
                    <#elseif post.status == "draft">
                    草稿
                    </#if>
                </td>
                <td>
                    <a href="${request.contextPath}/admin/page/${post.id}" class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-edit"></i> <span>编辑</span></a>
                    <a href="javascript:void(0)" onclick="delPost(${post.id});"
                       class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-trash-o"></i> <span>删除</span></a>
                    <a class="btn btn-warning btn-sm waves-effect waves-light m-b-5" href="${request.contextPath}/${post.slug}"
                       target="_blank"><i
                            class="fa fa-rocket"></i> <span>预览</span></a>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>

<#include "./footer.ftl" >

<script type="text/javascript">
    var tale = new $.tale();
    function delPost(cid) {
        tale.alertConfirm({
            title:'确定删除这个页面吗?',
            then: function () {
                tale.post({
                    url : '${request.contextPath}/admin/page/delete',
                    data: {id: cid},
                    success: function (result) {
                        if(result && result.success){
                            tale.alertOkAndReload('页面删除成功');
                        } else {
                            tale.alertError(result.msg || '页面删除失败');
                        }
                    }
                });
            }
        });
    }
</script>
</body>
</html>