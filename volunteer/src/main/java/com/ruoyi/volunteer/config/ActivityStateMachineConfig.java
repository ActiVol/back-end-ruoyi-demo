package com.ruoyi.volunteer.config;

import com.ruoyi.volunteer.enums.ActivityStatus;
import com.ruoyi.volunteer.enums.ActivityStatusChangeEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine(name = "ActivityStateMachine")
public class ActivityStateMachineConfig {

    /**
     * 配置状态
     *
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<ActivityStatus, ActivityStatusChangeEvent> states) throws Exception {
                states
                .withStates()
                .initial(ActivityStatus.WAIT_CONFIRM)
                .states(EnumSet.allOf(ActivityStatus.class));
    }
    /**
     * 配置状态转换事件关系
     *
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<ActivityStatus, ActivityStatusChangeEvent> transitions) throws Exception {
        transitions
                // 确认事件
                .withExternal().source(ActivityStatus.WAIT_CONFIRM).target(ActivityStatus.CONFIRM).event(ActivityStatusChangeEvent.TO_CONFIRM)
                .and()
                // 拒绝事件
                .withExternal().source(ActivityStatus.WAIT_CONFIRM).target(ActivityStatus.REFUSE).event(ActivityStatusChangeEvent.TO_REFUSE)
                .and()
                // 取消事件
                .withExternal().source(ActivityStatus.WAIT_CONFIRM).target(ActivityStatus.CLOSE).event(ActivityStatusChangeEvent.TO_CLOSE)
                .and()
                // 确认后取消
                .withExternal().source(ActivityStatus.CONFIRM).target(ActivityStatus.CLOSE).event(ActivityStatusChangeEvent.OK_CLOSE)
        ;
    }

}
