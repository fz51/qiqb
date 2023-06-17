package net.qiqb.auth;

public class AuthUserHolder {

    private static ThreadLocal<AuthUserHolder> holder = new ThreadLocal<>();

    private AuthUser authUser;


    public static synchronized AuthUserHolder get() {
        if (holder.get() == null) {
            holder.set(new AuthUserHolder());
        }
        return holder.get();
    }

    public static void reset(AuthUser authUser) {
        if (get() != null) {
            get().clear();
        }
        get().authUser = authUser;
    }


    /**
     * 获取当前用户
     *
     * @return 当前认证用户。
     */
    public AuthUser getCurrent() {
        return get().authUser;
    }

    public void clear() {
        this.authUser = null;
    }
}
