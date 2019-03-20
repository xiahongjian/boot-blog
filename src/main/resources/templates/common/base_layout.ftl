<#macro layout>
    <#assign title='主题设置'>
    <#assign active='themes'>
    <#include "../admin/header.ftl">

    <div class="row">
        <#nested >
    </div>

    <#include "../admin/footer.ftl">
    <#include "../common/tale_comment.ftl">
    <script type="text/javascript">

        var tale = new $.tale();

        /**
         * 保存全局设置
         */
        function saveThemeOptions(formId) {
            var param = $('#' + formId).serialize();
            tale.post({
                url : '/admin/themes/setting',
                data: param,
                success: function (result) {
                    if(result && result.success){
                        tale.alertOk('设置保存成功');
                    } else {
                        tale.alertError(result.msg || '设置保存失败');
                    }
                }
            });
        }

        /**
         * 保存个性化设置
         */
        function saveIndiviSetting() {
            var param = $('#indivi-form').serialize();
            tale.post({
                url : '/admin/setting',
                data: param,
                success: function (result) {
                    if(result && result.success){
                        tale.alertOk('设置保存成功');
                    } else {
                        tale.alertError(result.msg || '设置保存失败');
                    }
                }
            });
        }
    </script>
    </body>
    </html>
</#macro>


#include('/admin/footer.html')
#include('/comm/tale_comment.html')
<script type="text/javascript">

    var tale = new $.tale();

    /**
     * 保存全局设置
     */
    function saveThemeOptions(formId) {
        var param = $('#' + formId).serialize();
        tale.post({
            url : '/admin/themes/setting',
            data: param,
            success: function (result) {
                if(result && result.success){
                    tale.alertOk('设置保存成功');
                } else {
                    tale.alertError(result.msg || '设置保存失败');
                }
            }
        });
    }

    /**
     * 保存个性化设置
     */
    function saveIndiviSetting() {
        var param = $('#indivi-form').serialize();
        tale.post({
            url : '/admin/setting',
            data: param,
            success: function (result) {
                if(result && result.success){
                    tale.alertOk('设置保存成功');
                } else {
                    tale.alertError(result.msg || '设置保存失败');
                }
            }
        });
    }
</script>
</body>
</html>