package org.example;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.Charset;
import java.util.*;

public class Request {
    private static String[] requestLine;
    private static Map<String, String> header = new HashMap<>();
    private static List<NameValuePair> query = new ArrayList<>();

    public Request(String buffer) {
        final var requestLineEnd = buffer.indexOf("\r\n");

        requestLine = buffer.substring(0, requestLineEnd).split(" ");

        final var headersEnd = buffer.indexOf("\r\n\r\n");


        final var headersString = buffer.substring(requestLineEnd + 2, headersEnd);
        final var headers = headersString.split("\r\n");
        String[] headerBuffer;
        for (int i = 0; i < headers.length; i++) {
            headerBuffer = headers[i].split(":");
            header.put(headerBuffer[0], headerBuffer[1]);
        }


        if (requestLine[0].equals("POST")) {
            final var bodyString = buffer.substring(headersEnd + 4);
            query = URLEncodedUtils.parse(bodyString, Charset.defaultCharset());
        }

    }


    public String[] getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public List<NameValuePair> getQueryParams() {
        return query;
    }

    public List<NameValuePair> getQueryParam(String name) {
        List<NameValuePair> list = new ArrayList<>();
        for (int i = 0; i < query.size();i++){
            if (name.equals(query.get(i).getName())){
                list.add(query.get(i));
            }
        }
        return list;
    }
}
