package kz.allpay.integrations.premiera.requests.params;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 11:45 AM
 */
public class ParamValueFactory {
    public static <T extends Enum<?>> ParamValue<T> en(T enumValue) {
        return new ParamValue<T>(enumValue) {
            @Override
            public String convertToString() {
                return getValue().toString();
            }
        };
    }

    public static ParamValue<Boolean> bool(Boolean bool) {
        return new ParamValue<Boolean>(bool) {
            @Override
            public String convertToString() {
                return getValue() ? "1" : "0";
            }
        };
    }

    public static ParamValue<String> str(final String string) {
        return new ParamValue<String>(string) {
            @Override
            public String convertToString() {
                return getValue();
            }
        };
    }

    public static ParamValue<Integer> num(final Integer integer) {
        return new ParamValue<Integer>(integer) {
            @Override
            public String convertToString() {
                return "" + getValue();
            }
        };
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    public static ParamValue<Date> date(final Date date) {
        return new ParamValue<Date>(date) {
            @Override
            public String convertToString() {
                return dateFormat.format(getValue());
            }
        };
    }

    public static <T> ParamValue<ParamValue.Range<T>> range(ParamValue<T> from, ParamValue<T> to) {
        from.assertNotNull();
        to.assertNotNull();
        return new ParamValue<ParamValue.Range<T>>(new ParamValue.Range<T>(from, to)) {
            @Override
            public String convertToString() {
                return getValue().getFrom().assertNotNull().serialize() + "-" +
                        getValue().getTo().assertNotNull().serialize();
            }
        };
    }

    public static <T> ParamValue<List<ParamValue<T>>> list(List<ParamValue<T>> list) {
        return new ParamValue<List<ParamValue<T>>>(list) {
            @Override
            public String convertToString() {
                StringBuilder sb = new StringBuilder();
                for (ParamValue<T> paramValue : getValue()) {
                    if (paramValue.getValue() != null) {
                        if (sb.length() != 0) {
                            sb.append(";");
                        }
                        sb.append(paramValue.serialize());
                    }
                }
                return sb.toString();
            }
        };
    }
}
