<#assign active="log" title="系统日志" >
<#include "header.ftl" >
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">系统日志</h4>
    </div>
    <form>
        <div class="row">
            <div class="col-md-3">
                <div class="input-group">
                    <span class="input-group-addon">动作</span>
                    <select class="form-control" name="action">
                        <option value=""></option>
                        <#if actionsMap?? && actionsMap?size &gt; 0>
                            <#list actionsMap?keys as k>
                                <option value="${k}" <#if action?? && k == action>selected</#if>>${actionsMap[k]}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </div>
            <div class="col-md-3">
                <div class="input-group">
                    <span class="input-group-addon">访问者</span>
                    <input type="text" class="form-control" name="author" value="${author!}">
                </div>
            </div>
            <div class="col-md-3">
                <div class="input-group">
                    <span class="input-group-addon">IP</span>
                    <input type="text" class="form-control"  name="ip" value="${ip!}">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="input-daterange input-group date-picker">
                    <input type="text" class="input-small form-control" placeholder="开始时间" value="${from!}" name="from" />
                    <span class="input-group-addon">至</span>
                    <input type="text" class="input-small form-control" placeholder="结束时间" value="${to!}" name="to" />
                </div>
            </div>
        </div>
        <div class="row" style="padding-top: 10px; padding-bottom: 10px;">
            <div class="col-md-4">
                <div class="input-group">
                    <span class="input-group-addon">内容</span>
                    <input type="text" class="form-control" name="keyword" value="${keyword!}">
                </div>
            </div>
            <div class="col-md-1 pull-right">
                <button class="btn btn-md btn-primary" type="submit"><i class="fa fa-search"></i> 查询</button>
            </div>
        </div>
    </form>
    <div class="col-md-12">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <th width="10%">ID</th>
                <th width="8%">动作</th>
                <th>内容</th>
                <th width="8%">访问者</th>
                <th width="10%">IP</th>
                <th width="10%">区域</th>
                <th width="15%">时间</th>
            </tr>
            </thead>
            <tbody>
            <#if (logs.list)?? && logs.list?size &gt; 0 >
                <#list logs.list as log>
                    <tr cid="${log.id}">
                        <td>${log.id}</td>
                        <td><#if log.action??><#if actionsMap?? && actionsMap[log.action]??>${actionsMap[log.action]}<#else>${log.action!}</#if></#if></td>
                        <td>${log.data!}</td>
                        <td>${log.authorId!'-'}</td>
                        <td>${log.ip!'-'}</td>
                        <td>${log.region!'-'}</td>
                        <td>${log.created?string('yyyy-MM-dd HH:mm:ss')}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
        <@com.pageAdminNav pageInfo=logs params="action=${action!}&author=${author!}&ip=${ip!}&from=${from!}&to=${to!}&keyword=${keyword!}" />
    </div>
</div>

<#include "./footer.ftl" />
<@com.resource_loader type="data-picker" />
<script>
    $(function(){
        $('.date-picker').datepicker({
            format: "yyyy-mm-dd",
            language: 'zh-CN',
            todayBtn: true,
            todayHighlight: true,
            autoclose: true
        });
    });
</script>
</body>
</html>