<#import "./common/macros.ftl" as com>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="shortcut icon" href="/static/admin/images/favicon.png">
    <title>Tale - 博客安装</title>
    <link href="${request.contextPath}/static/admin/plugins/jquery-steps/1.1.0/jquery.steps.css" rel="stylesheet" type="text/css">

    <@com.resource_loader type="install_head" />
    <link href="${request.contextPath}/static/admin/css/style.min.css" rel="stylesheet" type="text/css">
    <style>
        body,html {
            background: url("${request.contextPath}/static/admin/images/bg/${random(1, 6)}.png") no-repeat;
            background-size: cover;
        }
        .home-text {
            -webkit-box-sizing: content-box;
            -moz-box-sizing: content-box;
            box-sizing: content-box;
            cursor: pointer;
            border: none;
            font: normal 36px/normal "Passero One", Helvetica, sans-serif;
            color: rgba(255,255,255,1);
            text-align: center;
            -o-text-overflow: clip;
            text-overflow: clip;
            text-shadow: 0 1px 0 rgb(204,204,204) , 0 2px 0 rgb(201,201,201) , 0 3px 0 rgb(187,187,187) , 0 4px 0 rgb(185,185,185) , 0 5px 0 rgb(170,170,170) , 0 6px 1px rgba(0,0,0,0.0980392) , 0 0 5px rgba(0,0,0,0.0980392) , 0 1px 3px rgba(0,0,0,0.298039) , 0 3px 5px rgba(0,0,0,0.2) , 0 5px 10px rgba(0,0,0,0.247059) , 0 10px 10px rgba(0,0,0,0.2) , 0 20px 20px rgba(0,0,0,0.14902) ;
            -webkit-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
            -moz-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
            -o-transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
            transition: all 300ms cubic-bezier(0.42, 0, 0.58, 1);
        }
        .home-text:hover {
            color: rgba(106,107,106,1);
            text-shadow: 0 1px 0 rgba(255,255,255,1) , 0 2px 0 rgba(255,255,255,1) , 0 3px 0 rgba(255,255,255,1) , 0 4px 0 rgba(255,255,255,1) , 0 5px 0 rgba(255,255,255,1) , 0 6px 1px rgba(0,0,0,0.0980392) , 0 0 5px rgba(0,0,0,0.0980392) , 0 1px 3px rgba(0,0,0,0.298039) , 0 3px 5px rgba(0,0,0,0.2) , 0 -5px 10px rgba(0,0,0,0.247059) , 0 -7px 10px rgba(0,0,0,0.2) , 0 -15px 20px rgba(0,0,0,0.14902) ;
            -webkit-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1) 10ms;
            -moz-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1) 10ms;
            -o-transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1) 10ms;
            transition: all 200ms cubic-bezier(0.42, 0, 0.58, 1) 10ms;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <@com.resource_loader type="html5_respond" />
    <![endif]-->
</head>
<body>

<div class="container" style="max-width: 680px;">
    <section class="home bg-dark" style="margin-top: 20px; margin-bottom: 20px;">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 text-center">
                    <div class="home-wrapper">
                        <h1 class="home-text"><span class="rotate">开始使用 Tale 写博客吧 :)</span></h1>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <#if !isInstalled>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Tale安装向导</h3>
                </div>
                <div class="panel-body">
                    <form id="wizard-validation-form">
                        <div>
                            <h3>网站设置</h3>
                            <section>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">* 网站名称</label>
                                    <div class="col-md-8">
                                        <input class="form-control" name="site_title" type="text" placeholder="请输入网站名称"
                                               required aria-required="true" maxlength="100"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">* 网站地址</label>
                                    <div class="col-md-8">
                                        <input class="form-control" id="site_url" name="site_url" type="text" placeholder="请输入网站地址，未绑定域名则输入http://ip:port"
                                               required aria-required="true"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">* 管理员账号</label>
                                    <div class="col-md-8">
                                        <input class="form-control" name="admin_user" type="text" placeholder="请输入管理员账号"
                                               required aria-required="true" maxlength="50"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">&nbsp;&nbsp;管理员邮箱</label>
                                    <div class="col-md-8">
                                        <input class="form-control" name="admin_email" type="email"
                                               placeholder="请输入管理员邮箱"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">* 管理员密码</label>
                                    <div class="col-md-8">
                                        <input id="password1" name="admin_pwd" type="password" class="form-control"
                                               required aria-required="true" rangelength="[6,14]" placeholder="请输入6-14位管理员密码"/>
                                    </div>
                                </div>
                                <div class="form-group clearfix">
                                    <label class="col-md-3 control-label">* 确认管理员密码</label>
                                    <div class="col-md-8">
                                        <input id="password2" type="password" class="form-control" equalTo="#password1"
                                               placeholder="请确认管理员密码"/>
                                    </div>
                                </div>
                            </section>
                            <h3>安装完成</h3>
                            <section>
                                <div class="form-group clearfix">
                                    <div class="col-lg-12 text-center">
                                        <h3>恭喜你，安装完成！</h3>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </form>
                </div>  <!-- End panel-body -->
            </div> <!-- End panel -->

        </div> <!-- end col -->

    </div> <!-- End row -->
    <#else>
    你已经安装过完成了，如需重新安装请提前做好备份，并删除根目录下的
    <mark>install.lock</mark>
    文件。
    </#if>
</div>
<@com.resource_loader type="install_foot" />
<script src="/static/admin/js/base.js"></script>
<script src="/static/admin/js/install.js"></script>
</body>
</html>