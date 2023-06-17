package net.qiqb.execution.common.step;

public class StepProcessorConfig {

    private transient StepProcessor stepProcessor;
    private boolean init = false;

    public StepProcessorConfig(StepProcessor stepProcessor) {
        if (stepProcessor == null) {
            throw new IllegalStateException("stepProcessor 不能为空");
        }
        this.stepProcessor = stepProcessor;
    }

    public StepProcessor getStepProcessor() {
        if (!this.init) {
            this.init = true;
            this.stepProcessor.init(this);
        }
        return stepProcessor;
    }
}
