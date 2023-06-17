package net.qiqb.execution.common.step;

import net.qiqb.execution.executor.CommandWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepProcessorChainImpl implements StepProcessorChain {
    public static final int INCREMENT = 10;
    private int pos = 0;
    private int n = 0;

    private StepProcessorConfig[] stepProcessorConfigs = new StepProcessorConfig[0];

    @Override
    public void doProcess(CommandWrapper command) {
        if (pos < n) {
            final StepProcessorConfig stepProcessorConfig = stepProcessorConfigs[pos++];
            final StepProcessor stepProcessor = stepProcessorConfig.getStepProcessor();
            if (stepProcessor.isSkip(command)) {
                // 自动执行下一个
                this.doProcess(command);
                return;
            }
            stepProcessor.doProcess(command, this);
        }
    }

    public void addStepProcessor(StepProcessorConfig stepProcessorConfig) {

        // Prevent the same filter being added multiple times
        for (StepProcessorConfig config : stepProcessorConfigs) {
            if (config == stepProcessorConfig) {
                return;
            }
        }

        if (n == stepProcessorConfigs.length) {
            StepProcessorConfig[] newFilters =
                    new StepProcessorConfig[n + INCREMENT];
            System.arraycopy(stepProcessorConfigs, 0, newFilters, 0, n);
            stepProcessorConfigs = newFilters;
        }
        stepProcessorConfigs[n++] = stepProcessorConfig;

    }

    void release() {
        for (int i = 0; i < n; i++) {
            stepProcessorConfigs[i] = null;
        }
        n = 0;
        pos = 0;
    }
}
