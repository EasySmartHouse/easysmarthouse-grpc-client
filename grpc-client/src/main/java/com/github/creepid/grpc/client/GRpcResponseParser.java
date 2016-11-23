/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.creepid.grpc.client;

import com.github.creepid.grpc.client.serialization.SerializabilityUtil;
import com.github.creepid.grpc.client.serialization.SerializedInstanceReference;
import com.github.creepid.grpc.client.serialization.Serializer;
import com.github.creepid.grpc.client.serialization.SerializerContainer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rusakovich
 */
class GRpcResponseParser implements RpcResponseParser {

    private static final int PROTOCOL_VERSION = 7;

    private static final int SYSTEM_FLAGS_GROUP = 3;
    private static final int STRINGS_GROUP = 2;
    private static final int INDEXES_GROUP = 1;

    private static final String STRINGS_DELIM = "\",\"";
    private static final String INDEXES_DELIM = ",";

    private static final Pattern JS_ARRAY_PATTERN = Pattern.compile("(.*?),\\[(.*?)\\],(.*)");

    private static final String OK_PREFIX = "//OK";
    private static final String EXCEPTION_PREFIX = "//EX";

    public GRpcResponseParser() {
    }

    private String getArray(String fullStr) {
        int firstIndex = fullStr.indexOf('[');
        int lastIndex = fullStr.lastIndexOf(']');

        if (firstIndex == -1 || lastIndex == -1) {
            return null;
        }

        return fullStr.substring(firstIndex + 1, lastIndex);
    }

    private String excapeFromEdgeQuotes(String str) {
        if (str == null) {
            return str;
        }

        StringBuilder builder = new StringBuilder(str);
        if (str.startsWith("\"")) {
            int firstIndex = builder.indexOf("\"");
            builder.replace(firstIndex, firstIndex + 1, "");
        }

        if (str.endsWith("\"")) {
            int lastIndex = builder.lastIndexOf("\"");
            builder.replace(lastIndex, lastIndex + 1, "");
        }

        return builder.toString();
    }

    private ArrayDeque<String> getDeque(String str, String delim) {
        ArrayDeque<String> deque = new ArrayDeque<>();
        if (str == null) {
            return deque;
        }
        String[] delimited = str.split(delim);
        for (int i = 0; i < delimited.length; i++) {
            deque.push(excapeFromEdgeQuotes(delimited[i]));
        }
        return deque;
    }

    @Override
    public Object getResponseObject(String responseStr) {
        if (responseStr == null) {
            throw new IllegalArgumentException("Response must not be null!");
        }

        if (responseStr.startsWith(OK_PREFIX)) {
            String arrContent = getArray(responseStr);
            if (arrContent != null) {
                Matcher matcher = JS_ARRAY_PATTERN.matcher(arrContent);
                if (matcher.find()) {
                    ArrayDeque<String> systemFlags = getDeque(matcher.group(SYSTEM_FLAGS_GROUP), INDEXES_DELIM);
                    int protocol = Integer.valueOf(systemFlags.pop());
                    if (protocol != PROTOCOL_VERSION) {
                        throw new UnsupportedOperationException("Not supported protocol version!");
                    }

                    List<String> stringTable = new ArrayList<>(getDeque(matcher.group(STRINGS_GROUP), STRINGS_DELIM));
                    Collections.reverse(stringTable);

                    ArrayDeque<String> indexes = getDeque(matcher.group(INDEXES_GROUP), INDEXES_DELIM);

                    String returnTypeIndex = indexes.peek();
                    String returnTypeName = stringTable.get(Integer.valueOf(returnTypeIndex) - 1);

                    SerializedInstanceReference decoded = SerializabilityUtil.decodeSerializedInstanceReference(returnTypeName);

                    String signature = decoded.getSignature();
                    String name = decoded.getName();

                    if (!signature.isEmpty()) {
                        Serializer serializer = SerializerContainer.findSerializerForClass(name);
                        return serializer.decode(stringTable, indexes);
                    } else {
                        //if signature is empty - string value
                        return name;
                    }
                }
            }
            return null;
        } else if (responseStr.startsWith(EXCEPTION_PREFIX)) {
            //TODO create an exception object
            ////EX[2,1,["com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533","Too few tokens in RPC request"],0,7]
            return null;
        } else {
            throw new IllegalStateException(String.format("Unknown response format: [%s]", responseStr));
        }
    }

}
