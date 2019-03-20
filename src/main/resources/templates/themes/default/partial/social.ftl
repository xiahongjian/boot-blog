<div class="social-list">
    <#if !isBlank(siteOpt('social_weibo'))>
    <a class="social weibo" target="blank" href="http://weibo.com/${siteOpt('social_weibo')}">微博</a>
    </#if>

    <#if !isBlank(siteOpt('social_zhihu'))>
    <a class="social zhihu" target="blank"
       href="https://www.zhihu.com/people/${siteOpt('social_zhihu')}">知乎</a>
    </#if>

    <a class="social rss" target="blank" href="${request.contextPath}/feed">RSS</a>

    <#if !isBlank(siteOpt('social_github'))>
    <a class="social github" target="blank"
       href="https://github.com/${siteOpt('social_github')}">Github</a>
    </#if>

    <#if !isBlank(siteOpt('social_twitter'))>
    <a class="social twitter" target="blank" href="https://twitter.com/${siteOpt('social_twitter')}">Twitter</a>
    </#if>
</div>