<#import "../common/macros.ftl" as com>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${title ! '博客后台'} - Tale</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <link rel="shortcut icon" href="/static/admin/images/favicon.png"/>
    <@com.resource_loader type="header" />
    <link href="/static/admin/css/style.min.css?v=v1.0" rel="stylesheet" type="text/css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <@com.resource_loader type="html5_response" />
    <![endif]-->
</head>
<body class="fixed-left">
<div id="wrapper">
    <div class="topbar">
        <div class="topbar-left">
            <div class="text-center p-t-10" style="margin: 0 auto;">
                <div class="pull-left" style="padding-left: 10px;">
                    <a href="/admin/index">
                        <img src="/static/admin/images/unicorn.png" width="50" height="50"/>
                    </a>
                </div>
                <div class="pull-left" style="padding-left: 10px;">
                    <span style="font-size: 28px; color: #2f353f; line-height: 50px;">Tale Blog</span>
                </div>
            </div>
        </div>
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="">
                    <div class="pull-left">
                        <button type="button" class="button-menu-mobile open-left">
                            <i class="fa fa-bars"></i>
                        </button>
                        <span class="clearfix"></span>
                    </div>

                    <ul class="nav navbar-nav navbar-right pull-right">
                        <li class="dropdown">
                            <a href="index.html" class="dropdown-toggle profile" data-toggle="dropdown"
                               aria-expanded="true"><img src="${request.contextPath}/static/admin/images/unicorn.png" alt="user-img" class="img-circle"> </a>
                            <ul class="dropdown-menu">
                                <li><a href="${site_url!}" target="_blank"><i class="fa fa-eye" aria-hidden="true"></i> 查看网站</a></li>
                                <li><a href="/${request.contextPath}admin/profile"><i class="fa fa-sun-o"></i> 个人设置</a></li>
                                <!--<li><a href="/admin/reload"><i class="fa fa-sun"></i> 重启系统</a></li>-->
                                <li><a href="${request.contextPath}/logout"><i class="fa fa-sign-out"></i> 注销</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="left side-menu">
        <div class="sidebar-inner slimscrollleft">
            <div id="sidebar-menu">
                <ul>
                    <li <#if active == 'home'>class="active"</#if>>
                        <a href="${request.contextPath}/admin/index" class="waves-effect <#if (active=='home')> active </#if>"><i class="fa fa-dashboard" aria-hidden="true"></i><span> 仪表盘 </span></a>
                    </li>
                    <li <#if active == 'publish'> class="active"</#if>>
                        <a href="${request.contextPath}/admin/article/publish" class="waves-effect <#if (active=='publish')> active </#if>"><i class="fa fa-pencil-square-o" aria-hidden="true"></i><span> 发布文章 </span></a>
                    </li>
                    <li <#if active=='article'> class="active" </#if>>
                        <a href="${request.contextPath}/admin/article" class="waves-effect <#if (active=='article')> active </#if>"><i class="fa fa-list" aria-hidden="true"></i><span> 文章管理 </span></a>
                    </li>

                    <li <#if active=='page'> class="active" </#if>>
                        <a href="${request.contextPath}/admin/page" class="waves-effect <#if (active=='page')>active</#if>"><i class="fa fa-file-text" aria-hidden="true"></i><span> 页面管理 </span></a>
                    </li>

                    <li <#if (active=='attach')>class="active"</#if>>
                        <a href="${request.contextPath}/admin/attach" class="waves-effect <#if (active=='attach')>active</#if>"><i class="fa fa-cloud-upload" aria-hidden="true"></i><span> 文件管理 </span></a>
                    </li>

                    <li class="has_sub">
                        <a href="javascript:void(0)" class="waves-effect <#if (has_sub?? && has_sub=='other')> active subdrop </#if>"><i class="fa fa-cubes"></i><span> 其他管理 </span><span class="pull-right"><i class="fa fa-plus"></i></span></a>
                        <ul class="list-unstyled">
                            <li <#if (active=='comment')>class="active"</#if>>
                                <a href="${request.contextPath}/admin/comment" class="waves-effect <#if (active=='comment')>active</#if>"><i class="fa fa-comments" aria-hidden="true"></i><span> 评论管理 </span></a>
                            </li>
                            <li <#if (active=='category')>class="active"</#if>>
                                <a href="${request.contextPath}/admin/category" class="waves-effect <#if (active=='category')>active</#if>"><i class="fa fa-tags" aria-hidden="true"></i><span> 分类/标签 </span></a>
                            </li>
                            <li <#if (active=='template')>class="active"</#if>>
                                <a href="${request.contextPath}/admin/template" class="waves-effect <#if (active=='template')>active</#if>"><i class="fa fa-hashtag"></i><span> 编辑模板 </span></a>
                            </li>
                        </ul>
                    </li>

                    <li <#if (active=='themes')>class="active"</#if>>
                        <a href="${request.contextPath}/admin/themes" class="waves-effect <#if (active=='themes')>active</#if>"><i class="fa fa-diamond" aria-hidden="true"></i><span> 主题设置 </span></a>
                    </li>

                    <li <#if (active=='setting')>class="active"</#if>>
                        <a href="${request.contextPath}/admin/setting" class="waves-effect <#if (active=='setting')>active</#if>"><i class="fa fa-gear" aria-hidden="true"></i><span> 系统设置 </span></a>
                    </li>

                    <li <#if (active=='log')>class="active"</#if>>
                        <a href="${request.contextPath}/admin/log" class="waves-effect <#if (active=='log')>active</#if>"><i class="fa fa-filter" aria-hidden="true"></i><span> 系统日志 </span></a>
                    </li>
                    <#if plugin_menus??>
                    <#list plugin_menus as item>
                    <li <#if (active==item.slug)>class="active"</#if>>
                        <a href="${request.contextPath}/admin/plugins/${item.slug}" class="waves-effect <#if (active==item.slug)>active</#if>"><i class="${item.icon!'fa fa-gear'}" aria-hidden="true"></i><span> ${item.name} </span></a>
                    </li>
                    </#list>
                    </#if>
                </ul>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="content-page">
        <div class="content">
            <div class="container">