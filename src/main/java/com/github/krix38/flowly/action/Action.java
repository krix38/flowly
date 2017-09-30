package com.github.krix38.flowly.action;

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

    public AbstractModel doAction(AbstractModel abstractModel){
        if(!abstractModel.hasFailed()){
            try{
                abstractModel = runMethod(abstractModel);
            }catch (Exception exception){
                abstractModel.setFailureInformation(new FailureInformation(getTargetException(exception)));
            }
        }
        return abstractModel;
    }

    private Throwable getTargetException(Exception exception) {
        if(exception instanceof InvocationTargetException){
            return ((InvocationTargetException) exception).getTargetException();
        }else{
            return exception;
        }
    }

    private <T> AbstractModel runMethod(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(classTypeprovided()){
            return instantiateClassAndInvokeMethod(model);
        }else{
            return invokeMethod(model, instance);
        }
    }

    private <T> AbstractModel instantiateClassAndInvokeMethod(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(methodArgumentMatchesModelClass(model) && classHasDefaultConstructor()){
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
        for(Constructor constructor : tClass.getConstructors()){
            if(constructor.getParameters().length == 0){
                return constructor;
            }
        }
        return null;
    }

    private <T> Boolean methodArgumentMatchesModelClass(T model) {
        return method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == model.getClass();
    }

    private Object getClassInstance() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return getClassDefaultConstructor().newInstance();
    }

}
