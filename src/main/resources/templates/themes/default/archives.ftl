<#include "./partial/header.ftl" >
<div class="main-content archive-page clearfix">
    <div class="categorys-item">
        <#list archives as archive>
        <div class="categorys-title">${archive.dateStr}</div>
        <div class="post-lists">
            <div class="post-lists-body">
                <#list archive.articles as article>
                <div class="post-list-item">
                    <div class="post-list-item-container">
                        <div class="item-label">
                            <div class="item-title">
                                <a href="${articleUrl(article)}">${article.title!}</a>
                            </div>
                            <div class="item-meta clearfix">
                                <div class="item-meta-date">发布于 ${article.created?string('yyyy-MM-dd')}</div>
                            </div>
                        </div>
                    </div>
                </div>
                </#list>
            </div>
        </div>
        </#list>
    </div>
</div>
<#include "./partial/footer.ftl" >