package com.github.krix38.flowly.action;

import com.github.krix38.flowly.exception.ActionExecutionException;
import com.github.krix38.flowly.model.AbstractModel;
import com.github.krix38.flowly.model.FailureInformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by krix on 29.09.2017.
 */
public class Action {

    private Class tClass;

    private Method method;

    private Object instance;

    public Action(Class tClass, Method method) {
        this.tClass = tClass;
        this.method = method;
    }

    public Action(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public AbstractModel doAction(AbstractModel abstractModel) throws Exception {
        if(!abstractModel.hasFailed()){
            try{
                abstractModel = runMethod(abstractModel);
            }catch (InvocationTargetException exception){
                abstractModel.setFailureInformation(new FailureInformation(exception.getTargetException()));
            }
        }
        return abstractModel;
    }


    private <T> AbstractModel runMethod(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException, ActionExecutionException {
        if(classTypeprovided()){
            return instantiateClassAndInvokeMethod(model);
        }else{
            return invokeMethod(model, instance);
        }
    }

    private <T> AbstractModel instantiateClassAndInvokeMethod(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException, ActionExecutionException {
        if(classHasDefaultConstructor()){
            return invokeMethodForInstantiatedClass(model);
        }else{
            throw new ActionExecutionException(String.format("Action %s does not have default constructor provided", tClass.getSimpleName()));
        }
    }

    private <T> AbstractModel invokeMethodForInstantiatedClass(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(methodArgumentMatchesModelClass(model)){
            return invokeMethod(model, getClassInstance());
        }else {
            return (AbstractModel) model;
        }
    }

    private <T> AbstractModel invokeMethod(T model, Object instance) throws IllegalAccessException, InvocationTargetException {
        if(method.getReturnType() != void.class){
            return (AbstractModel) method.invoke(instance, model);
        }else {
            method.invoke(instance, model);
            return (AbstractModel) model;
        }

    }

    private Boolean classTypeprovided() {
        return tClass != null;
    }

    private Boolean classHasDefaultConstructor(){
        for(Constructor constructor : tClass.getConstructors()){
            if(constructor.getParameters().length == 0){
                return true;
            }
        }
        return false;
    }

    private Constructor getClassDefaultConstructor(){
        Constructor returnConstructor = null;
        for(Constructor constructor : tClass.getConstructors()){
            if(constructor.getParameters().length == 0){
                returnConstructor = constructor;
            }
        }
        return returnConstructor;
    }

    private <T> Boolean methodArgumentMatchesModelClass(T model) {
        return method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == model.getClass();
    }

    private Object getClassInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return getClassDefaultConstructor().newInstance();
    }

}
