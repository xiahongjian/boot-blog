<#assign home_page=1 is_post=false>
<#include "./partial/header.ftl" >
<div class="main-content index-page clearfix">
    <div class="post-lists">
        <div class="post-lists-body">
            <#list articles(12).list as article>
            <div class="post-list-item">
                <div class="post-list-item-container">
                    <div class="item-thumb bg-deepgrey" style="background-image:url(${showThumb(article)});"></div>
                    <a href="${articleUrl(article)}">
                        <div class="item-desc">
                            <p>${brief(article.content, 75, true)}</p>
                        </div>
                    </a>
                    <div class="item-slant reverse-slant bg-deepgrey"></div>
                    <div class="item-slant"></div>
                    <div class="item-label">
                        <div class="item-title"><a href="${articleUrl(article)}">${article.title!}</a>
                        </div>
                        <div class="item-meta clearfix">
                            <div class="item-meta-ico ${showIcon(article.id)}"
                                 style="background: url(${theme('img/bg-ico.png')}) no-repeat;background-size: 40px auto;"></div>
                            <div class="item-meta-cat">
                                ${showCategories(article.categories!)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </#list>
        </div>
    </div>
    <div class="lists-navigator clearfix">
        <@com.pageNav pageInfo=articles(12) prevText='←' nextText='→' prefix='page' />
    </div>
</div>
<#include "./partial/footer.ftl" >