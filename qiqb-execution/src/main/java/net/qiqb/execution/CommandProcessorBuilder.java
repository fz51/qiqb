package net.qiqb.execution;

import net.qiqb.execution.common.step.StepProcessorConfig;

import java.util.ArrayList;
import java.util.List;

public class CommandProcessorBuilder {

    private List<StepProcessorConfig> stepProcessorConfigs = new ArrayList<>();


    public void addStepProcessorConfigs(StepProcessorConfig stepProcessorConfig) {
        stepProcessorConfigs.add(stepProcessorConfig);
    }

    public List<StepProcessorConfig> getStepProcessorConfigs() {
        return stepProcessorConfigs;
    }
}
