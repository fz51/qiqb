package net.qiqb.execution.executor;

import net.qiqb.execution.executor.config.ExecutorInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ExecutorInterceptorChain {


    private final List<ExecutorInterceptor> interceptorList = new ArrayList<>();

    private int interceptorIndex = -1;


    public ExecutorInterceptorChain(ExecutorInterceptor... interceptors) {
        if (interceptors != null) {
            this.interceptorList.addAll(Arrays.asList(interceptors));

        }

    }

    public void addInterceptor(ExecutorInterceptor interceptor) {
        this.interceptorList.add(interceptor);
    }


    public void addInterceptor(int index, ExecutorInterceptor interceptor) {
        this.interceptorList.add(index, interceptor);
    }

    public void addInterceptors(ExecutorInterceptor... interceptors) {
        CollectionUtils.mergeArrayIntoCollection(interceptors, this.interceptorList);
    }


    public ExecutorInterceptor[] getInterceptors() {
        return (!this.interceptorList.isEmpty() ? this.interceptorList.toArray(new ExecutorInterceptor[0]) : null);
    }

    public List<ExecutorInterceptor> getInterceptorList() {
        return (!this.interceptorList.isEmpty() ? Collections.unmodifiableList(this.interceptorList) :
                Collections.emptyList());
    }

    boolean applyPreExecute(CommandWrapper cmd) {
        for (int i = 0; i < this.interceptorList.size(); i++) {
            ExecutorInterceptor interceptor = this.interceptorList.get(i);
            if (!interceptor.preExecute(cmd)) {
                triggerAfterCompletion(cmd);
                return false;
            }
            this.interceptorIndex = i;
        }
        return true;
    }


    void applyPostExecute(CommandWrapper cmd) {

        for (int i = this.interceptorList.size() - 1; i >= 0; i--) {
            ExecutorInterceptor interceptor = this.interceptorList.get(i);
            interceptor.postExecute(cmd);
        }
    }


    void triggerAfterCompletion(CommandWrapper cmd) {
        for (int i = this.interceptorIndex; i >= 0; i--) {
            ExecutorInterceptor interceptor = this.interceptorList.get(i);
            try {
                interceptor.afterCompletion(cmd);
            } catch (Throwable ex2) {
                log.error("HandlerInterceptor.afterCompletion threw exception", ex2);
            }
        }
    }

}
