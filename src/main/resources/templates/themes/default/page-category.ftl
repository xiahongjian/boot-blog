<#assign title=keyword >
<#include "./partial/header.ftl" >
<div class="main-content common-page clearfix">
    <div class="categorys-item">
        <div class="common-title">
            ${type}：${keyword!}
        </div>
        <#if posts.list?size == 0>
        <div>
            <p>抱歉，还没有相关文章.</p>
        </div>
        <#else>
        <div class="post-lists">
            <div class="post-lists-body">
                <#list posts.list as article>
                <div class="post-list-item">
                    <div class="post-list-item-container ">
                        <div class="item-label ">
                            <div class="item-title">
                                <a href="${articleUrl(article)}">${article.title!}</a>
                            </div>
                            <div class="item-meta clearfix">
                                <div class="item-meta-ico ${showIcon(article.id)}"
                                     style="background: url(${theme('img/bg-ico.png')}) no-repeat;background-size: 40px auto;"></div>
                                <div class="item-meta-date">发布于 ${article.created?string('yyyy-MM-dd')}</div>
                            </div>
                        </div>
                    </div>
                </div>
                </#list>
            </div>
        </div>
        </#if>
    </div>
</div>
<#include "./partial/footer.ftl">