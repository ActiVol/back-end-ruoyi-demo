package com.ruoyi.volunteer.config;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Persist<E, S>  {

    /**
     * 持久化到内存map中
     *
     * @return
     */
    @Bean(name = "stateMachineMemPersister")
    public static StateMachinePersister getPersister() {
        return new DefaultStateMachinePersister(new StateMachinePersist() {
            @Override
            public void write(StateMachineContext context, Object contextObj) throws Exception {
                System.out.println("持久化状态机,context:"+ JSON.toJSONString(context) +",contextObj:" + JSON.toJSONString(contextObj) );
                map.put(contextObj, context);
            }
            @Override
            public StateMachineContext read(Object contextObj) throws Exception {
                System.out.println("获取状态机,contextObj:" + JSON.toJSONString(contextObj));
                StateMachineContext stateMachineContext = (StateMachineContext) map.get(contextObj);
                System.out.println("获取状态机结果,stateMachineContext:" + JSON.toJSONString(stateMachineContext));
                return stateMachineContext;
            }
            private Map map = new HashMap();
        });
    }

}
