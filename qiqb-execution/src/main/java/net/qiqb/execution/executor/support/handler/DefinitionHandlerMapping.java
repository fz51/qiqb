package net.qiqb.execution.executor.support.handler;


/**
 * 通过类型
 */
/**
public class DefinitionHandlerMapping implements TypeHandlerMapping {


    private final List<Object> totalHandlers = new ArrayList<>();


    @Override
    public TypeHandlerChain getHandler(CommandWrapper commandWrapper, Class<?> handlerType) {
        Definition definition = commandWrapper.getCommandDefinition();
        String commandName = null;
        String domainName = null;
        if (definition instanceof CommandDefinition) {
            commandName = definition.getDefinitionName();
            final Definition domainDefinition = ((CommandDefinition) definition).getDomainDefinition();
            domainName = domainDefinition.getDefinitionName();
        } else if (definition instanceof DomainDefinition) {
            domainName = definition.getDefinitionName();
        }
        final HandlerGenericIndex genericIndex = AnnotationUtil.getAnnotation(handlerType, HandlerGenericIndex.class);
        int commandGenericIndex = -1;
        int domainGenericIndex = -1;
        if (genericIndex != null) {
            commandGenericIndex = genericIndex.command();
            domainGenericIndex = genericIndex.domain();
        }
        NameIndex commandNameIndex = null, domainNameIndex = null;
        if (commandGenericIndex != -1) {
            commandNameIndex = new NameIndex(commandGenericIndex, commandName);
        }
        if (domainGenericIndex != -1) {
            domainNameIndex = new NameIndex(domainGenericIndex, domainName);
        }
        List<Object> handlers = new ArrayList<>();
        for (Object h : totalHandlers) {
            if (isType(h, handlerType, commandNameIndex, domainNameIndex)) {
                handlers.add(h);
            }
        }
        if (handlers.isEmpty()) {
            return null;
        }
        return new TypeHandlerChain(handlers.toArray());
    }

    public Object getDefaultHandler(Definition definition, Class<?> handlerType) {
        return null;
    }

    public boolean isType(Object h, Class<?> handlerType, NameIndex commandNameIndex, NameIndex domainNameIndex) {
        final boolean assignableFrom = handlerType.isAssignableFrom(h.getClass());
        if (assignableFrom) {
            // 比较泛型是否匹配
            final ExecutingMapping executingMapping = AnnotationUtil.getAnnotation(handlerType, ExecutingMapping.class);
            if (executingMapping != null) {
                if (commandNameIndex == null) {
                    if (StrUtil.isNotEmpty(executingMapping.commandName())) {
                        return false;
                    }
                } else {
                    if (StrUtil.isEmpty(executingMapping.commandName())) {
                        return false;
                    }
                    if (!commandNameIndex.name.equals(executingMapping.commandName())) {
                        return false;
                    }
                }
                if (domainNameIndex == null) {
                    return !StrUtil.isNotEmpty(executingMapping.domainName());
                } else {
                    if (StrUtil.isEmpty(executingMapping.domainName())) {
                        return false;
                    }
                    return domainNameIndex.name.equals(executingMapping.domainName());
                }
            }
        }
        return false;
    }

    public void addHandlers(Collection<?> handler) {
        if (this.totalHandlers.contains(handler)) {
            return;
        }
        this.totalHandlers.addAll(handler);
    }


    public static class NameIndex {
        @Getter
        final int index;
        @Getter
        final String name;

        NameIndex(int index, String name) {
            this.name = name;
            this.index = index;
        }
    }
}
*/