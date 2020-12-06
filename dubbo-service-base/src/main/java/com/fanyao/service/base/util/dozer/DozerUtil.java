package com.fanyao.service.base.util.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: bugProvider
 * @date: 2019/10/15 10:12
 * @description:
 */
public class DozerUtil {

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }


    public static <T,Q> List<T> mapList(Mapper mapper,Collection<Q> sourceList, Class<T> destinationClass){
        return sourceList.stream().map(it -> mapper.map(it, destinationClass)).collect(Collectors.toList());
    }


}
