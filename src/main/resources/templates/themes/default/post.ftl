<#assign title=article.title keywords=article.tags is_post=true>
<#include "./partial/header.ftl" >
<article class="main-content page-page" itemscope itemtype="http://schema.org/Article">
    <div class="post-header">
        <h1 class="post-title" itemprop="name headline">
            <a href="${articleUrl(article)}">${article.title}</a>
        </h1>
        <div class="post-data">
            <time datetime="${article.created?string('yyyy-MM-dd')}" itemprop="datePublished">发布于 ${article.created?string('yyyy-MM-dd')}</time>
            / ${showCategories(article.categories)} / <a href="#comments"><#if !(article.commentsNum??) || article.commentsNum ==0>没有评论<#else>${article.commentsNum} 条评论</#if></a> /
            ${article.hits!0}浏览
        </div>
    </div>
    <div id="post-content" class="post-content" itemprop="articleBody">
        <p class="post-tags">${showTags(article.tags)}</p>
        ${showContent(article.content)}
        <p class="post-info">
            本文由 <a href="">${username(article.authorId)!}</a> 创作，采用 <a href="https://creativecommons.org/licenses/by/4.0/" target="_blank"
                                               rel="external nofollow">知识共享署名4.0</a> 国际许可协议进行许可<br>本站文章除注明转载/出处外，均为本站原创或翻译，转载前请务必署名<br>最后编辑时间为:
            ${article.modified?string('yyyy/MM/dd HH:mm')}
        </p>
    </div>
</article>
<div id="post-bottom-bar" class="post-bottom-bar">
    <div class="bottom-bar-inner">
        <div class="bottom-bar-items social-share left">
            <span class="bottom-bar-item">Share : </span>
            <span class="bottom-bar-item bottom-bar-facebook"><a href="https://www.facebook.com/sharer/sharer.php?u=${articleUrl(article)}" target="_blank" title="${article.title}" rel="nofollow">facebook</a></span>
            <span class="bottom-bar-item bottom-bar-twitter"><a href="https://twitter.com/intent/tweet?url=${articleUrl(article)}&text=${article.title}" target="_blank" title="${article.title}" rel="nofollow">Twitter</a></span>
            <span class="bottom-bar-item bottom-bar-weibo"><a href="http://service.weibo.com/share/share.php?url=${articleUrl(article)}&amp;title=${article.title}" target="_blank" title="${article.title}" rel="nofollow">Weibo</a></span>
            <span class="bottom-bar-item bottom-bar-qrcode"><a href="//pan.baidu.com/share/qrcode?w=300&amp;h=300&amp;url=${articleUrl(article)}" target="_blank" rel="nofollow">QRcode</a></span>
        </div>
        <div class="bottom-bar-items right">
            <span class="bottom-bar-item">${prevArticle(article, '→')!}</span>
            <span class="bottom-bar-item">${nextArticle(article, '←')!}</span>
            <span class="bottom-bar-item"><a href="#footer">↓</a></span>
            <span class="bottom-bar-item"><a href="#">↑</a></span>
        </div>
    </div>
</div>
<#include "./partial/comments.ftl" >
<#include "./partial/footer.ftl" >