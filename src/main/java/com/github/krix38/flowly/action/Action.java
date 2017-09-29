package com.github.krix38.flowly.action;

import com.github.krix38.flowly.model.AbstractModel;
import com.github.krix38.flowly.model.FailureInformation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by krix on 29.09.2017.
 */
public class Action {

    private Class tClass;

    private Method method;

    public Action(Class tClass, Method method) {
        this.tClass = tClass;
        this.method = method;
    }

    public AbstractModel doAction(AbstractModel abstractModel){
        if(!abstractModel.hasFailed()){
            try{
                runMethod(abstractModel);
            }catch (Exception exception){
                abstractModel.setFailureInformation(new FailureInformation(exception));
            }
        }
        return abstractModel;
    }

    private <T> void runMethod(T abstractModel) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(method.getParameterTypes()[0] == abstractModel.getClass()){
            method.invoke(getClassInstance(), abstractModel);
        }
    }

    private Object getClassInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return tClass.getConstructors()[0].newInstance();
    }

}
