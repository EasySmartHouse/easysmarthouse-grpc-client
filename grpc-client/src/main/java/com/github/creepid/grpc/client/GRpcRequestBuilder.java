
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.github.creepid.grpc.client.settings.GRpcSetting;
import com.github.creepid.grpc.client.settings.GRpcSettings;
import com.github.creepid.grpc.client.serialization.Serializer;
import com.github.creepid.grpc.client.serialization.SerializerContainer;
import com.github.creepid.grpc.client.utils.ClassHelper;
import static com.github.creepid.grpc.client.utils.collections.CollectionsHelper.*;
import com.github.creepid.grpc.client.utils.collections.filter.CollectionFilter;
import com.github.creepid.grpc.client.utils.collections.filter.CustomTypeFieldsValueAdder;
import com.github.creepid.grpc.client.utils.collections.filter.DublicateElementsFilter;
import com.github.creepid.grpc.client.utils.collections.filter.EmptyStringElementsFilter;
import com.github.creepid.grpc.client.utils.collections.filter.NativeTypeFilter;
import com.github.creepid.grpc.client.utils.collections.process.GRpcEncodeClassProcessor;
import com.github.creepid.grpc.client.utils.collections.filter.NullElementsFilter;
import com.github.creepid.grpc.client.utils.collections.process.StringConvertor;
import com.github.creepid.grpc.client.utils.collections.filter.ZeroElementsFilter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 *
 * @author rusakovich
 */
class GRpcRequestBuilder implements RpcRequestBuilder, StringTableAware {

    private static final RequestHeader[] MANDATORY_HEADERS = new RequestHeader[]{
        new RequestHeader("Content-Type", "text/x-gwt-rpc; charset=utf-8"),
        new RequestHeader("Accept-Encoding", "gzip, deflate"),
        new RequestHeader("Keep-Alive", "115"),
        new RequestHeader("Cache-Control", "no-cache"),
        new RequestHeader("Pragma", "no-cache"),
        new RequestHeader("X-GWT-Permutation", "HostedMode"),
        new RequestHeader("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7"),
        new RequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")};

    private static final String SEPARATOR = "|";

    private static final String DEFAULT_PROTOCOL_VERSION = "7";
    private static final String DEFAULT_FLAGS = "0";

    private final String baseUrl;
    private final String policyFile;
    private final String protocolVersion;
    private final String flags;

    private final Class serviceInterface;
    private final Method method;
    private final Object[] args;

    private final Queue<String> requestEntities = new LinkedList<>();

    private final List<RequestHeader> customHeaders;

    private final List<String> strTable = new LinkedList<>();
    private final List<String> indexTable = new LinkedList<>();
    private List<Class<?>> paramTypes;
    private List<Object> vals;

    public GRpcRequestBuilder(GRpcSettings rpcSettings, Class serviceInterface, Method method, Object[] args) {
        this.baseUrl = rpcSettings.getString(GRpcSetting.BASE_URL);
        this.policyFile = rpcSettings.getString(GRpcSetting.POLICY_FILE_STRONG_NAME);
        this.protocolVersion = (rpcSettings.contains(GRpcSetting.PROTOCOL_VERSION))
                ? rpcSettings.getString(GRpcSetting.PROTOCOL_VERSION)
                : DEFAULT_PROTOCOL_VERSION;
        this.flags = (rpcSettings.contains(GRpcSetting.MANUAL_FLAGS))
                ? rpcSettings.getString(GRpcSetting.MANUAL_FLAGS)
                : DEFAULT_FLAGS;
        this.serviceInterface = serviceInterface;
        this.method = method;
        this.args = args;
        this.customHeaders = (rpcSettings.contains(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER))
                ? (List<RequestHeader>) (List<?>) rpcSettings.getSettings(GRpcSetting.CUSTOM_HTTP_HTTPS_HEADER)
                : Collections.EMPTY_LIST;
    }

    /**
     * Gets request string
     *
     * @return
     */
    private String getRequestString() {
        StringBuilder reqBuilder = new StringBuilder();
        int entitiesCount = requestEntities.size();
        for (int i = 0; i < entitiesCount; i++) {
            reqBuilder.append(requestEntities.poll())
                    .append(SEPARATOR);
        }
        return reqBuilder.toString();
    }

    /**
     * Gets class index from the string table
     *
     * @param type - type of the element
     * @return index in string table
     */
    @Override
    public int getTypeIndex(Class<?> type) {
        return strTable.size() + (paramTypes.indexOf(type) + 1);
    }

    /*
     * @return offset to add to table index
     */
    public int getIndexTableOffset() {
        return (strTable.size() + paramTypes.size() + 1);
    }

    /**
     * Calculate string table size
     *
     * @return
     */
    public int getStringTableSize() {
        return strTable.size() + paramTypes.size() + vals.size();
    }

    /**
     * Add index of giver type to index table
     *
     * @param type - type to add
     */
    @Override
    public void addTypeIndex(Class<?> type) {
        indexTable.add(String.valueOf(getTypeIndex(type)));
    }

    /**
     * Add index value
     *
     * @param value - value to add to index table
     */
    @Override
    public void addValueIndex(Object value) {
        indexTable.add(String.valueOf(vals.indexOf(value) + getIndexTableOffset()));
    }

    /**
     * Add value to index table
     *
     * @param value - value to add
     */
    @Override
    public void addValueToIndexes(String value) {
        indexTable.add(value);
    }

    /**
     * Removes the value from values list
     *
     * @param object - value to remove
     */
    @Override
    public void flushValue(Object object) {
        if (vals.contains(object)) {
            vals.remove(object);
        }
    }

    /**
     * Removes the type from type list
     *
     * @param type - value to remove
     */
    @Override
    public void flushType(Class<?> type) {
        if (paramTypes.contains(type)) {
            paramTypes.remove(type);
        }
    }

    @Override
    public String getBody() {
        //add protocol version
        requestEntities.add(protocolVersion);
        //add flags
        requestEntities.add(flags);

        //method param types
        List<Class<?>> methodTypes = Arrays.asList(method.getParameterTypes());
        //runtime param types
        List<Class<?>> runtimeTypes = Arrays.asList(ClassHelper.getClasses(args));

        //checking methods and runtime types
        if (methodTypes.size() != runtimeTypes.size()) {
            throw new IllegalStateException("Method params count is different from runtime params!");
        }

        //merge types lists
        this.paramTypes = new ArrayList<>(methodTypes);
        for (int i = 0; i < methodTypes.size(); i++) {
            Class<?> methodType = methodTypes.get(i);
            Class<?> runtimeType = runtimeTypes.get(i);

            if (ClassHelper.getBoxUnboxType(runtimeType) != methodType) {
                paramTypes.add(runtimeType);
            }
        }

        //remain only not null, unique elements  
        filter(paramTypes,
                new CollectionFilter[]{
                    new NullElementsFilter(),
                    new DublicateElementsFilter()
                }
        );

        //form unique arguments list
        this.vals = getList(args);
        //remain only not blank, unique elements of native type and add types of fields of user types
        filter(vals,
                new CollectionFilter[]{
                    new NullElementsFilter(), new EmptyStringElementsFilter(),
                    new ZeroElementsFilter(), new DublicateElementsFilter(),
                    new CustomTypeFieldsValueAdder(), new NativeTypeFilter()
                }
        );

        //form string table
        //add base URL
        strTable.add(baseUrl);
        //add policy file name
        strTable.add(policyFile);
        //add service interface name
        strTable.add(serviceInterface.getName());
        //add service method name
        strTable.add(method.getName());

        //fill index table
        for (int i = 1; i <= strTable.size(); i++) {
            indexTable.add(String.valueOf(i));
        }
        //add method parameters count
        indexTable.add(String.valueOf(method.getParameterTypes().length));

        //process method type params
        for (int i = 1; i <= methodTypes.size(); i++) {
            Class<?> methodType = methodTypes.get(i - 1);
            Class<?> runtimeType = runtimeTypes.get(i - 1);

            if (runtimeType == null) {
                runtimeType = methodType;
            }

            //find aproppriate serializer
            Serializer serializer = SerializerContainer.findSerializerForClass(runtimeType);
            //encode the type
            serializer.encodeType(methodType, runtimeType, this);
        }

        //process values
        for (int i = 1; i <= methodTypes.size(); i++) {
            Class<?> methodType = methodTypes.get(i - 1);
            Class<?> runtimeType = runtimeTypes.get(i - 1);

            if (runtimeType == null) {
                runtimeType = methodType;
            }

            //getting argument value
            Object argValue = args[i - 1];
            //find aproppriate serializer
            Serializer serializer = SerializerContainer.findSerializerForClass(runtimeType);
            //encode the type
            serializer.encodeValue(argValue, methodType, this);
        }

        //string table elements count
        requestEntities.add(String.valueOf(getStringTableSize()));
        //add all the values from the string table
        for (String str : strTable) {
            requestEntities.add(str);
        }

        //encode the classes
        for (String encodedClass : process(paramTypes, new GRpcEncodeClassProcessor())) {
            requestEntities.add(encodedClass);
        }

        //convers arguments to string
        for (String strValue : process(vals, new StringConvertor())) {
            requestEntities.add(strValue);
        }

        //add table indexes
        for (String index : indexTable) {
            requestEntities.add(index);
        }

        return getRequestString();
    }

    @Override
    public Set<RequestHeader> getHeaders() {
        Set<RequestHeader> headers = new HashSet<>();
        headers.addAll(Arrays.asList(MANDATORY_HEADERS));
        headers.addAll(customHeaders);
        return headers;
    }

}
