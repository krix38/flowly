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
                runMethod(abstractModel);
            }catch (Exception exception){
                abstractModel.setFailureInformation(new FailureInformation(exception));
            }
        }
        return abstractModel;
    }

    private <T> void runMethod(T model) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        if(classTypeprovided()){
            if(methodArgumentMatchesModelClass(model) && classHasDefaultConstructor()){
                method.invoke(getClassInstance(), model);
            }
        }else{
            method.invoke(instance, model);
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
