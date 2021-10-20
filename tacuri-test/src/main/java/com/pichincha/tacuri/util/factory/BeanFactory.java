package com.pichincha.tacuri.util.factory;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;

import java.lang.reflect.InvocationTargetException;

@Log4j2
public class BeanFactory {
    public static <T> T getBean(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (BeansException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
