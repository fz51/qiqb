package net.qiqb.auth;

/**
 * 统一定义一个认证用户。
 * 用户空间和用户id 标识全局唯一。
 */
public class AuthUser {
    /**
     * 佚名
     */
    public static final AuthUser ANONYMOUS = new AuthUser("NON:10:佚名");

    private static final String splitStr = ":";

    /**
     * 用户空间
     */
    private final String userspace;

    /**
     * 用户id
     */
    private final String id;


    /**
     * 用户名称
     */
    private String name;

    public AuthUser(String authUserStr) {
        final String[] strings = authUserStr.split(splitStr);
        this.userspace = strings[0];
        this.id = strings[1];
        if (strings.length > 2) {
            this.name = strings[2];
        }
    }

    public AuthUser(String userspace, String id) {
        this.userspace = userspace;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String toString() {
        return this.userspace + splitStr + this.id + splitStr + this.name;
    }

    public String getUserspace() {
        return this.userspace;
    }
}
