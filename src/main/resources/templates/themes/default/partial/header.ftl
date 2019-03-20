<#import "../../../common/macros.ftl" as com>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-transform"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta name="keywords" content="${siteOpt('site_keywords')!}"/>
    <meta name="description" content="${siteOpt('site_description')!}"/>
    <link rel="shortcut icon" href="${theme('/img/favicon.png')}"/>
    <link rel="apple-touch-icon" href="${theme('/img/apple-touch-icon.png')}"/>
    <title>${siteOpt('site_title')!} - ${title!}</title>
    <link href="${theme('/css/xcode.min.css')}" rel="stylesheet">
    <link href="${theme('/css/style.min.css')}" rel="stylesheet">
    <@com.resource_loader type="jquery" />

    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body <#if (is_post?? && !is_post)>class="bg-grey" </#if> gtools_scp_screen_capture_injected="true">
<!--[if lt IE 8]>
<div class="browsehappy" role="dialog">
    当前网页 <strong>不支持</strong> 你正在使用的浏览器. 为了正常的访问, 请 <a href="http://browsehappy.com/" target="_blank">升级你的浏览器</a>。
</div>
<![endif]-->
<header id="header" class="header bg-white">
    <div class="navbar-container">
        <a href="${siteUrl()}" class="navbar-logo">
            <img src="${theme('/img/logo.png')}" alt="${siteOpt('site_title')}"/>
        </a>
        <div class="navbar-menu">
            <#--
            <a href="${siteUrl('/archives')}">归档</a>
            <a href="${siteUrl('/links')}">友链</a>
            <a href="${siteUrl('/about')}">关于</a>
            -->
        </div>
        <div class="navbar-search" onclick="">
            <span class="icon-search"></span>
            <form role="search" onsubmit="return false;">
                <span class="search-box">
                    <input type="text" id="search-inp" class="input" placeholder="搜索..." maxlength="30"
                           autocomplete="off">
                </span>
            </form>
        </div>
        <div class="navbar-mobile-menu" onclick="">
            <span class="icon-menu cross"><span class="middle"></span></span>
            <ul>
                <li><a href="${siteUrl('/archives')}">归档</a></li>
                <li><a href="${siteUrl('/links')}">友链</a></li>
                <li><a href="${siteUrl('/about')}">关于</a></li>
            </ul>
        </div>
    </div>
</header>