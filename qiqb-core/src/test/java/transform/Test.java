package transform;

import net.qiqb.core.diff.DiffClassifyCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        List<UserRoleDo> oldVal = new ArrayList<>();
        oldVal.add(new UserRoleDo("u_1", "r_1","用户1角色名称1"));
        oldVal.add(new UserRoleDo("u_1", "r_2","用户1角色名称2"));
        oldVal.add(new UserRoleDo("u_2", "r_1","用户2角色名称1"));
        oldVal.add(new UserRoleDo("u_3", "r_3","用户2角色名称3"));

        List<UserRoleDo> newVal = new ArrayList<>();
        newVal.add(new UserRoleDo("u_1", "r_1","用户1角色名称"));
        // 用户1新增3，删除了2
        newVal.add(new UserRoleDo("u_1", "r_3","用户1角色名称2"));
        newVal.add(new UserRoleDo("u_2", "r_1","用户2角色名称1：改变了名称"));
        //oldVal.add(new UserRoleDo("u_3", "r_3","用户2角色名称3"));
        newVal.add(new UserRoleDo("u_4", "r_4","用户4角色名称4"));

        DiffClassifyCollection<UserRoleDo> c = new DiffClassifyCollection<>(oldVal, newVal, userRoleDo -> userRoleDo.getUserId()+"@@"+userRoleDo.getRoleId());

        final Collection<UserRoleDo> additionalFromLeft = c.getAdditionalFromLeft();
        final Collection<UserRoleDo> deletedFromLeft = c.getDeletedFromLeft();
        final Map<UserRoleDo, UserRoleDo> modifiedFromLeft1 = c.getModifiedFromLeft();

        final Collection<UserRoleDo> additionalFromRight = c.getAdditionalFromRight();
        final Collection<UserRoleDo> deletedFromRight = c.getDeletedFromRight();
        final Map<UserRoleDo, UserRoleDo> modifiedFromRight = c.getModifiedFromRight();
        System.out.println("eeee");
    }
}
