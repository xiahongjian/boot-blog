<#import "../common/macros.ftl" as com>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="shortcut icon" href="/static/admin/images/favicon.png"/>
    <title>Tale - 用户登录</title>

    <@com.resource_loader type='login_head' />
    <link href="/static/admin/css/style.min.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        body,html {
            background: url("${request.contextPath}/static/admin/images/bg/${random(1, 6)}.png") no-repeat;
            background-size: cover;
        }
        .panel-shadow {
            -moz-box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
            -webkit-box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
            box-shadow: 0px 0px 10px 0px rgba(39, 49, 65, 0.1);
        }
        .bg-overlay {
            -moz-border-radius: 6px 6px 0 0;
            -webkit-border-radius: 6px 6px 0 0;
            background-color: rgba(47, 51, 62, 0.3);
            border-radius: 6px 6px 0 0;
            height: 100%;
            left: 0;
            position: absolute;
            top: 0;
            width: 100%;
        }
        .input-border {b1.png
            font-size: 14px;
            width: 100%;
            height: 40px;
            border-radius: 0;
            border: none;
            border-bottom: 1px solid #dadada;
        }

        .bg-img > h3 {
            text-shadow: 0px 2px 3px #555;
            color: #cac9c8;
        }
    </style>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <@com.resource_loader type='html5_respond' />
    <![endif]-->

</head>
<body>

<div style="margin: 0 auto; padding-bottom: 0%; padding-top: 7.5%; width: 380px;">
    <div class="panel panel-color panel-danger panel-pages panel-shadow">
        <div class="panel-heading bg-img">
            <div class="bg-overlay"></div>
            <h3 class="text-center m-t-10"> Login Tale</h3>
        </div>

        <div class="panel-body">
            <form class="form-horizontal m-t-20" method="post" id="loginForm" onsubmit="return checkForm()">

                <div class="form-group">
                    <div class="col-xs-12">
                        <input class=" input-lg input-border" name="username" type="text" required=""
                               placeholder="请输入账号 :)">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <input class=" input-lg input-border" name="password" type="password" required=""
                               placeholder="请输入密码">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <div class="checkbox checkbox-danger">
                            <input id="checkbox-signup" name="remeberMe" type="checkbox">
                            <label for="checkbox-signup">记住我</label>
                        </div>
                    </div>
                </div>

                <div class="form-group text-center m-t-40">
                    <div class="col-xs-12">
                        <button class="btn btn-danger btn-lg btn-rounded btn-block w-lg waves-effect waves-light" style="box-shadow: 0px 0px 4px #868282;" type="submit">登&nbsp;录
                        </button>
                    </div>
                </div>
            </form>
        </div>

    </div>
</div>
<!-- Main  -->
<@com.resource_loader type='login_foot' />
<script src="/static/admin/js/base.js"></script>
<script type="text/javascript">
    var tale = new $.tale();
    function checkForm() {
        tale.post({
            url: '${request.contextPath}/admin/login',
            data: $("#loginForm").serialize(),
            success: function (result) {
                if (result && result.success) {
                    window.location.href = '${request.contextPath}/admin/index';
                } else {
                    tale.alertError(result.msg || '登录失败');
                }
            }
        });
        return false;
    }
</script>
</body>
</html>