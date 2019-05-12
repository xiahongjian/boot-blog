<#assign active='publish', title='保存文章' >
<#include "header.ftl" >
<link href="${request.contextPath}${request.contextPath}/static/admin/plugins/tagsinput/jquery.tagsinput.css" rel="stylesheet">
<link href="${request.contextPath}${request.contextPath}/static/admin/plugins/toggles/toggles.css" rel="stylesheet">
<@com.resource_loader type="article_edit_head" />
<style rel="stylesheet">
    #tags_tagsinput {
        background-color: #fafafa;
        border: 1px solid #eeeeee;
    }

    #tags_addTag input {
        width: 100%;
    }

    #tags_addTag {
        margin-top: -5px;
    }

    .mditor .editor{
        font-size: 14px;
        font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    }
    .mditor .backdrop, .mditor .textarea, .mditor .viewer{
        font-size: 14px;
    }
    .markdown-body{
        font-size: 14px;
    }
    .note-toolbar {
        text-align: center;
    }

    .note-editor.note-frame {
        border: none;
    }

    .note-editor .note-toolbar {
        background-color: #f5f5f5;
        padding-bottom: 10px;
    }

    .note-toolbar .note-btn-group {
        margin: 0;
    }

    .note-toolbar .note-btn {
        border: none;
    }

    #articleForm #dropzone {
        min-height: 200px;
        background-color: #dbdde0;
        line-height:200px;
        margin-bottom: 10px;
    }
    #articleForm .dropzone {
        border: 1px dashed #8662c6;
        border-radius: 5px;
        background: white;
    }
    #articleForm .dropzone .dz-message {
        font-weight: 400;
    }
    #articleForm .dropzone .dz-message .note {
        font-size: 0.8em;
        font-weight: 200;
        display: block;
        margin-top: 1.4rem;
    }
</style>
<div class="row">
    <div class="col-sm-12">
        <h4 class="page-title">
            <#if content??>
                编辑文章
            <#else>
                发布文章
            </#if>
        </h4>
    </div>
    <div class="col-md-12">

        <input type="hidden" id="attach_url" value="${attach_url!}" />

        <form id="articleForm">
            <input type="hidden" name="categories" id="categories"/>
            <input type="hidden" name="id" value="${(content.id)!}" id="cid"/>
            <input type="hidden" name="status" value="${(content.status)!'draft'}" id="status"/>
            <input type="hidden" name="allowComment" value="${((content.allowComment)!true)?c}" id="allowComment"/>
            <input type="hidden" name="allowPing" value="${((content.allowPing)!true)?c}" id="allowPing"/>
            <input type="hidden" name="allowFeed" value="${((content.allowFeed)!true)?c}" id="allowFeed"/>
            <input type="hidden" name="content" id="content-editor"/>
            <input type="hidden" name="fmtType" id="fmtType" value="${(content.fmtType)!'markdown'}"/>

            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input class="form-control" placeholder="请输入文章标题（必须）" name="title" required
                       value="${(content.title)!}"/>
            </div>

            <div class="form-group col-md-6" style="padding: 0 0 0 10px;">
                <input class="form-control" placeholder="自定义访问路径，如：my-first-article  默认为文章id" name="slug"
                       value="${(content.slug)!}"/>
            </div>

            <div class="form-group col-md-6" style="padding: 0 10px 0 0;">
                <input name="tags" id="tags" type="text" class="form-control" placeholder="请填写文章标签"
                       value="${(content.tags)!}"/>
            </div>

            <div class="form-group col-md-6">
                <select id="multiple-sel" class="select2 form-control" multiple="multiple" data-placeholder="请选择分类...">
                    <#if !(categories??) || categories?size == 0>
                    <option value="默认分类" selected>默认分类</option>
                    <#else>
                    <#list categories as c>
                    <option value="${c.name}" <#if (content.categories)?? && content.categories?contains(c.name)>selected</#if>>
                        ${c.name}
                    </option>
                    </#list>
                    </#if>
                </select>
            </div>
            <div class="clearfix"></div>

            <div class="form-group col-xs-12">
                <div class="pull-right">
                    <a id="switch-btn" href="javascript:void(0)" class="btn btn-purple btn-sm waves-effect waves-light switch-editor">
                        <#if content?? && content.fmtType == 'html'> 切换为Markdown编辑器 <#else> 切换为富文本编辑器 </#if></a>
                </div>
            </div>

            <div id="md-container" class="form-group col-md-12">
                <textarea id="md-editor" class="<#if content?? && content.fmtType != 'html'> hide </#if>">${(content.content)!}</textarea>
            </div>
            <div id="html-container" class="form-group col-md-12">
                <div class="summernote">
                    <#if content?? && content.fmtType == 'html'>
                    ${content.content ! ''}
                    </#if>
                </div>
            </div>

            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">开启评论</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success allow-${((content.allowComment)!true)?c}"
                         onclick="allow_comment(this);" on="${((content.allowComment)!true)?c}"></div>
                </div>
            </div>

            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">允许Ping</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success allow-${((content.allowPing)!true)?c}"
                         onclick="allow_ping(this);" on="${((content.allowPing)!true)?c}"></div>
                </div>
            </div>

            <div class="form-group col-md-3 col-sm-4">
                <label class="col-sm-4">允许订阅</label>
                <div class="col-sm-8">
                    <div class="toggle toggle-success allow-${((content.allowFeed)!true)?c}"
                         onclick="allow_feed(this);" on="${((content.allowFeed)!true)?c}"></div>
                </div>
            </div>

            <div class="form-group col-md-3">
                <label class="col-sm-5">添加缩略图</label>
                <div class="col-sm-7">
                    <div id="thumb-toggle" class="toggle toggle-success" on="false" thumb_url="${(content.thumbImg)!}" onclick="add_thumbimg(this);"></div>
                </div>
            </div>

            <div id="dropzone-container" class="form-group col-md-12 hide">
                <div class="dropzone dropzone-previews" id="dropzone">
                    <div class="dz-message">
                        <p>可以为你的文章添加一张缩略图 ;)</p>
                    </div>
                </div>
                <input type="hidden" name="thumbImg" id="thumbImg"/>
            </div>

            <div class="clearfix"></div>

            <div class="text-right">
                <a class="btn btn-default waves-effect waves-light" href="${request.contextPath}/admin/article">返回列表</a>
                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="subArticle('publish');">
                    保存文章
                </button>
                <button type="button" class="btn btn-warning waves-effect waves-light" onclick="subArticle('draft');">
                    存为草稿
                </button>
            </div>
        </form>
    </div>
</div>
<#include "./footer.ftl" >

<script src="${request.contextPath}/static/admin/plugins/tagsinput/jquery.tagsinput.min.js"></script>
<script src="${request.contextPath}/static/admin/plugins/jquery-multi-select/jquery.quicksearch.js"></script>
<@com.resource_loader type="article_edit_foot" />
<script src="${request.contextPath}/static/admin/js/article.js" type="text/javascript"></script>
</body>
</html>