package net.qiqb.execution.executor.config;

public class PersistenceWrapper<D> {
    /**
     * 初始化后得到的数据
     */
    private final D initDomainObject;

    /**
     * 业务执行后得到的领域对象
     */
    private final D completedDomainObject;


    public PersistenceWrapper(D initDomainObject, D completedDomainObject) {
        this.initDomainObject = initDomainObject;
        this.completedDomainObject = completedDomainObject;
    }

    /**
     * 获取当前的领域对象
     *
     * @return
     */
    public D get() {
        return completedDomainObject;
    }

    /**
     * 获取初始化领域对象
     *
     * @return
     */
    public D getInit() {
        return initDomainObject;
    }
}
