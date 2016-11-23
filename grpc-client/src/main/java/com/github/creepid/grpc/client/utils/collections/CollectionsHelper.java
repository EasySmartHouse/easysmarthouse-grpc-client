/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections;

import com.github.creepid.grpc.client.utils.collections.filter.CollectionFilter;
import com.github.creepid.grpc.client.utils.ReflectionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author rusakovich
 */
public class CollectionsHelper {

    private CollectionsHelper() {
    }

    public static <T> void filter(Collection<T> source, CollectionFilter<T>... filters) {
        if (source == null) {
            return;
        }

        if (filters == null || filters.length == 0) {
            return;
        }

        Collection<T> destination = ReflectionUtil.createInstance(source.getClass());
        for (T t : source) {

            boolean suitable = true;
            for (CollectionFilter<T> filter : filters) {
                if (filter instanceof CollectionAware) {
                    CollectionAware collAware = (CollectionAware) filter;
                    collAware.setCollection(destination);
                }
                suitable = filter.isSuitable(t);
                if (!suitable) {
                    break;
                }
            }

            if (!suitable) {
                continue;
            }

            destination.add(t);
        }

        source.clear();
        source.addAll(destination);
    }

    public static <D, S> Collection<D> process(Collection<S> source, CollectionProcessor<D, S> processor) {
        Collection<D> destination = ReflectionUtil.createInstance(source.getClass());
        for (S s : source) {
            destination.add(processor.getProcessedElement(s));
        }
        return destination;
    }

    public static <K, V> Map<K, V> sort(Map<K, V> map) {
        return new TreeMap<>(map);
    }

    public static <T> List<T> getList(T[] arr) {
        return (arr == null)
                ? new ArrayList<T>()
                : new ArrayList<T>(Arrays.asList(arr));
    }

}
