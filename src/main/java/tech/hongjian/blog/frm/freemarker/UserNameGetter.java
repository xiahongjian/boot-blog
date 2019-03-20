package tech.hongjian.blog.frm.freemarker;

import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import tech.hongjian.blog.db.entity.User;
import tech.hongjian.blog.frm.annotation.FMMethod;
import tech.hongjian.blog.service.UserService;

import java.util.List;

@FMMethod("username")
public class UserNameGetter extends FMMethodBase {
    @Autowired
    private UserService userService;

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments == null || !arguments.isEmpty()) {
            return null;
        }
        int uid = toInt(arguments.get(0));
        User user = userService.findById(uid);
        return user == null ? null : user.getUsername();
    }
}
