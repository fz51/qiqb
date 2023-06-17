package transform;

import lombok.Getter;
import lombok.Setter;
import org.javers.core.metamodel.annotation.Id;

@Getter
public class UserRoleDo {

    private final String userId;

    private final String roleId;

    @Setter
    private String roleName;

    public UserRoleDo(String userId, String roleId, String roleName) {
        this.userId = userId;
        this.roleId = roleId;
        this.roleName = roleName;
    }
}
