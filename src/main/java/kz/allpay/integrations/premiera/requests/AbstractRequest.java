package kz.allpay.integrations.premiera.requests;

import kz.allpay.integrations.premiera.requests.params.ParamValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.num;
import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.str;

/**
 * Name of a class that extends AbstractRequest must be as QueryCode parameter from documentation.
 * each parameter must have the same name as in documentation except it could have first char as lowercase character
 * as ordinal java naming convention.
 *
 * @author magzhan.karasayev
 * @since 3/5/16 12:52 PM
 */
public abstract class AbstractRequest<ResponseType> {
    private static final int lengthOfSizeBlock = 10;

    private ParamValue<String> encoding = str("UTF-8");
    private ParamValue<Integer> version;
    private ParamValue<Integer> serviceID = num(2);
    private ParamValue<Integer> archive = num(0);
    private ParamValue<String> expect;

    public abstract Class<ResponseType> getResponseClass();

    public AbstractRequest<ResponseType> setEncoding(ParamValue<String> encoding) {
        this.encoding = encoding;
        return this;
    }

    public AbstractRequest<ResponseType> setVersion(ParamValue<Integer> version) {
        this.version = version;
        return this;
    }

    public AbstractRequest<ResponseType> setServiceID(ParamValue<Integer> serviceID) {
        this.serviceID = serviceID;
        return this;
    }


    public String toRequest() {
        StringBuilder sb = new StringBuilder();

        // query code
        sb.append("&QueryCode=");
        sb.append(getClass().getSimpleName());

        // create parameters
        for (Field field : getAllFields(new ArrayList<Field>(), getClass())) {
            try {
                field.setAccessible(true);
                ParamValue<?> v = (ParamValue<?>)field.get(this);
                if (v != null) {
                    sb.append("&");

                    // upcase first
                    sb.append(field.getName().substring(0, 1).toUpperCase());
                    sb.append(field.getName().substring(1));

                    sb.append("=");
                    sb.append(v.serialize());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("must not be here");
            }
        }

        // create string which contains number of chars - length of the message
        String size = (sb.length()-1)+"";  // -1 for '&' symbol. For some reasons they don't count it
        int sizeOfSize = size.length();
        for (int i = 0; i < lengthOfSizeBlock - sizeOfSize; i++) {
            sb.insert(0, "0");
        }
        sb.insert(lengthOfSizeBlock - sizeOfSize, size);

        return sb.toString();
    }

    /**
     * finds all fields recursively
     * @param fields - fields of a type ParamValue
     * @param type
     * @return
     */
    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        for (Field field : type.getDeclaredFields()) {
            if (ParamValue.class.isAssignableFrom(field.getType())) {
                fields.add(field);
            }
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public AbstractRequest<ResponseType> setArchive(ParamValue<Integer> archive) {
        this.archive = archive;
        return this;
    }

    public AbstractRequest<ResponseType> setExpect(ParamValue<String> expect) {
        this.expect = expect;
        return this;
    }
}
