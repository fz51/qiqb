package net.qiqb.core;

/**
 * 系统描述
 */
public class SystemDescription {
    /**
     * 项目名称
     */
    private final String name;

    /**
     * 部署方式
     */
    private final DeploymentMode deployment;

    private static SystemDescription projectInfo;

    private static boolean init = false;

    public SystemDescription(String name, DeploymentMode deployment) {
        this.name = name;

        this.deployment = deployment;
    }

    public static void set(SystemDescription description) {
        if (init) {
            throw new IllegalStateException("不能设置两次项目信息");
        }
        SystemDescription.projectInfo = description;
        SystemDescription.init = true;
    }

    public static String getName() {
        return SystemDescription.projectInfo.name;
    }

    public static DeploymentMode getDeployment() {
        return SystemDescription.projectInfo.deployment;
    }

    public static boolean isSingle() {
        return DeploymentMode.single.equals(getDeployment());
    }
}
