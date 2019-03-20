<#if article??>
<div id="${article.id}" class="comment-container">
    <div id="comments" class="clearfix">
        <#if (article.allowComment?? && article.allowComment)>
        <span class="response"><#if login_user??>Hi，<a href="${login_user.home_url!}" data-no-instant>${login_user.username!}</a>
            如果你想 <a href="${request.contextPath}/logout" title="注销" data-no-instant>注销</a> ? </#if></span>

        <form method="post" id="comment-form" class="comment-form" onsubmit="return TaleComment.subComment();">
            <input type="hidden" name="coid" id="coid"/>
            <input type="hidden" name="cid" id="cid" value="${article.id}"/>
            <input name="author" maxlength="12" id="author" class="form-control input-control clearfix"
                   placeholder="姓名 (*)" value="${login_user.username!}" required/>
            <input type="email" name="mail" id="mail" class="form-control input-control clearfix" placeholder="邮箱 (*)"
                   value="${login_user.email!}" required/>
            <input type="url" name="url" id="url" class="form-control input-control clearfix" placeholder="网址 (http://)"
                   value="${login_user.homeUrl!}"/>
            <textarea name="content" id="textarea" class="form-control" placeholder="基佬，留下你的评论." required minlength="5" maxlength="2000"></textarea>
            <button class="submit" id="misubmit">提交</button>
        </form>

        <#else>
        <span class="response">评论已关闭.</span>
        </#if>

        <#if commentsPage.list?? && commentsPage.list?size &gt; 0>
        <ol class="comment-list">
            <#list commentsPage.list as comment>
            <li id="li-comment-${comment.id}" class="comment-body comment-parent comment-odd">
                <div id="comment-${comment.id}">
                    <div class="comment-view" onclick="">
                        <div class="comment-header">
                            <img class="avatar" src="${request.contextPath}/static/admin/images/unicorn.png?s=80&r=G&d=" title="${comment.author}"
                                 width="80" height="80">
                            <span class="comment-author">
                                <a href="${comment.url}" target="_blank" rel="external nofollow">${comment.author}</a>
                            </span>
                        </div>
                        <div class="comment-content">
                            <span class="comment-author-at"></span>
                            <p>${showContent(comment.content)}</p>
                        </div>
                        <div class="comment-meta">
                            <time class="comment-time">${comment.created?string('yyyy-MM-dd')}</time>
                            <span class="comment-reply">
                                <a rel="nofollow" onclick="TaleComment.reply('${comment.id}');">回复</a>
                            </span>
                        </div>
                    </div>
                </div>
                <#if (comment.level > 0)>
                <div class="comment-children">
                    <ol class="comment-list">
                        <#list comment.children as child>
                        <li id="li-comment-${child.id}"
                            class="comment-body comment-child comment-level-odd comment-odd">
                            <div id="comment-${child.id}">
                                <div class="comment-view">
                                    <div class="comment-header">
                                        <img class="avatar" src="${request.contextPath}/static/admin/images/unicorn.png?s=80&r=G&d=" title="${child.author}" width="80" height="80">
                                        <span class="comment-author comment-by-author">
                                            <a href="${child.url}" target="_blank" rel="external nofollow">${child.author}</a>
                                        </span>
                                    </div>
                                    <div class="comment-content">
                                        <span class="comment-author-at">
                                            <#if child.parent?? && child.parent != 0>
                                            <a href="#comment-${child.parent}">@${child.author}</a>
                                            </#if>
                                        </span>
                                        <p>${showContent(child.content)}</p>
                                    </div>
                                    <div class="comment-meta">
                                        <time class="comment-time">${child.created?string('yyyy-MM-dd')}</time>
                                        <span class="comment-reply">
                                            <a rel="nofollow" onclick="TaleComment.reply('${child.id}');">回复</a>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </li>
                        </#list>
                    </ol>
                </div>
                </#if>
            </li>
            </#list>
        </ol>
        <div class="lists-navigator clearfix">
            <ol class="page-navigator">
                <#if commentsPage.hasPreviousPage>
                <li class="prev"><a href="?cp=${commentsPage.prevPage}#comments">←</a></li>
                </#if>
                <#list commentsPage.navigatepageNums as navIndex>
                <li <#if commentsPage.pageNum == navIndex > class="current" </#if>><a href="?cp=${navIndex}#comments">${navIndex}</a></li>
                </#list>
                <#if commentsPage.hasNextPage>
                <li class="next"><a href="?cp=${commentsPage.nextPage}#comments">→</a></li>
                </#if>
            </ol>

        </div>
        </#if>

    </div>
</div>
</#if>
<#include "../../../common/tale_comment.ftl" >