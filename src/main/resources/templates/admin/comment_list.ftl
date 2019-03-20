<#assign active="comments" has_sub="other" title="评论管理" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">评论管理</h4>
    </div>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th>评论内容</th>
                <th>评论人</th>
                <th>评论时间</th>
                <th>评论人邮箱</th>
                <th>评论人网址</th>
                <th>评论状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <#list comments.list as comment>
            <tr cid="${comment.id}">
                <td>
                    <a href="${siteUrl('/article/')}${comment.cid}#comments" target="_blank">${brief((comment.content!''), 10)}</a>
                </td>
                <td>${comment.author!}</td>
                <td>${comment.created?str('yyyy-MM-dd HH:mm:ss')}</td>
                <td>${comment.mail}</td>
                <td><a href="${comment.url!}" target="_blank">${comment.url!}</a></td>
                <td>
                    <#if comment.status == 'approved'>
                    <span class="label label-success">审核通过</span>
                    <#elseif comment.status == 'not_audit'>
                    <span class="label label-default">未审核</span>
                    </#if>
                </td>
                <td>
                    <a href="javascript:void(0)" onclick="reply('${comment.id}');" class="btn btn-primary btn-sm waves-effect waves-light m-b-5"><i class="fa fa-edit"></i> <span>回复</span></a>
                    <#if (comment.status == 'not_audit')>
                    <a href="javascript:void(0)" onclick="updateStatus('${comment.id}', 'approved');" class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i
                            class="fa fa-trash-o"></i> <span>通过</span></a>
                    </#if>
                    <a href="javascript:void(0)" onclick="delComment(${comment.id});" class="btn btn-danger btn-sm waves-effect waves-light m-b-5"><i class="fa fa-trash-o"></i> <span>删除</span></a>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
        <@com.pageAdminNav pageInfo=comments />
    </div>
</div>

<#include "./footer.ftl" >

<script type="text/javascript">

    var tale = new $.tale();
    function reply(coid) {
        swal({
            title: "回复评论",
            text: "请输入你要回复的内容:",
            input: 'text',
            showCancelButton: true,
            confirmButtonText: '回复',
            cancelButtonText: '取消',
            showLoaderOnConfirm: true,
            preConfirm: function (comment) {
                return new Promise(function (resolve, reject) {
                    tale.post({
                        url : '${request.contextPath}/admin/comments',
                        data: {coid: coid, content: comment},
                        success: function (result) {
                            if(result && result.success){
                                tale.alertOk('已回复');
                            } else {
                                tale.alertError(result.msg || '回复失败');
                            }
                        }
                    });
                })
            },
            allowOutsideClick: false
        });
    }

    function delComment(coid) {
        tale.alertConfirm({
            title:'确定删除该评论吗?',
            then: function () {
                tale.post({
                    url : '${request.contextPath}/admin/comments/delete',
                    data: {coid: coid},
                    success: function (result) {
                        if(result && result.success){
                            tale.alertOkAndReload('评论删除成功');
                        } else {
                            tale.alertError(result.msg || '评论删除失败');
                        }
                    }
                });
            }
        });
    }

    function updateStatus(coid, status) {
        tale.post({
            url : '${request.contextPath}/admin/comments/status',
            data: {coid: coid, status: status},
            success: function (result) {
                if(result && result.success){
                    tale.alertOkAndReload('评论状态设置成功');
                } else {
                    tale.alertError(result.msg || '评论设置失败');
                }
            }
        });
    }
</script>

</body>
</html>