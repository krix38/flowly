package com.github.krix38.flowly.register;

import com.github.krix38.flowly.action.Action;
import com.github.krix38.flowly.annotation.FlowAction;
import com.github.krix38.flowly.exception.ActionExecutionException;
import com.github.krix38.flowly.model.AbstractModel;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created by krix on 29.09.2017.
 */

public class FlowRegister {

    private LinkedList<Action> actions = new LinkedList<Action>();

    public <T> void register(Class<T> tClass){
        for(Method method : tClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(FlowAction.class)){
                actions.add(new Action(tClass, method));
            }
        }
    }

    public void register(Object instance){
        for(Method method : instance.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(FlowAction.class)){
                actions.add(new Action(instance, method));
            }
        }
    }

    public AbstractModel run(AbstractModel abstractModel) throws ActionExecutionException {
        try {
            while (!actions.isEmpty()) {
                Action action = actions.removeFirst();
                abstractModel = action.doAction(abstractModel);
            }
        }catch (Exception exception){
            throw new ActionExecutionException(exception);
        }
        return abstractModel;
    }

}
