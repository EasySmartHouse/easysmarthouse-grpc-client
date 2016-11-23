/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client.utils.collections.filter;

import static com.github.creepid.grpc.client.utils.ClassHelper.isCustom;
import com.github.creepid.grpc.client.utils.collections.CollectionAware;
import static com.github.creepid.grpc.client.utils.collections.CollectionsHelper.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static com.github.creepid.grpc.client.utils.ReflectionUtil.getFieldsWithVals;

/**
 *
 * @author rusakovich
 */
public class CustomTypeFieldsValueAdder<T> implements CollectionFilter<T>, CollectionAware {

    private Collection<T> collection;

    @Override
    public boolean isSuitable(T elem) {
        //TODO make is recursively
        if (elem != null && isCustom(elem.getClass())) {
            Map fields = getFieldsWithVals(elem);
            List values = new ArrayList(fields.values());
            filter(values,
                    new CollectionFilter[]{
                        new NullElementsFilter(), new DublicateElementsFilter(),
                        new EmptyStringElementsFilter(), new ZeroElementsFilter()
                    });
            collection.addAll(values);
        }
        return true;
    }

    @Override
    public void setCollection(Collection collection) {
        this.collection = collection;
    }

}
