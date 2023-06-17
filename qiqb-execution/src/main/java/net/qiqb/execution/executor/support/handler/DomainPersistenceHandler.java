package net.qiqb.execution.executor.support.handler;

import net.qiqb.execution.executor.CommandWrapper;
import net.qiqb.execution.executor.DomainValueSnapshot;
import net.qiqb.execution.executor.DomainWrapper;
import net.qiqb.execution.executor.config.DomainPersistence;
import net.qiqb.execution.executor.config.PersistenceWrapper;

import java.util.Optional;

public class DomainPersistenceHandler implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof DomainPersistence<?>;
    }

    @Override
    public void handle(CommandWrapper command, Object handler) {
        if (handler instanceof DomainPersistence) {
            Object left = null;
            Object domain = command.getDomain();
            if (domain != null) {
                final Optional<DomainValueSnapshot> initDomain = ((DomainWrapper) domain).getDomainValueSnapshot(command)
                        .stream().filter(i -> i.getPoint().equals(DomainValueSnapshot.INIT_POINT)).findFirst();
                if (initDomain.isPresent()) {
                    left = initDomain.get().getValue();
                }
                domain = ((DomainWrapper) domain).getObject();
                //
            }
            ((DomainPersistence<Object>) handler).persisted(new PersistenceWrapper<>(left, domain));
            // 领域事件持久话

        }
    }
}
