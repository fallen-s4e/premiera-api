package kz.allpay.integrations.premiera.requests.params;

/**
 * This class introduced to create a type that knows how to serilize parameters
 * to premiera. The main method for it is: {@code ParamValue<T>.serialize}
 * @author magzhan.karasayev
 * @since 3/5/16 11:44 AM
 */
public abstract class ParamValue<T> {
    public abstract String convertToString();
    private final T value;

    protected ParamValue(T value) {
        this.value = value;
    }

    protected T getValue() {
        return value;
    }

    public String serialize() {
        return getValue() == null ? "" : convertToString();
    }

    public ParamValue<T> assertNotNull() {
        if (getValue() == null) {
            throw new NullPointerException("must not be null");
        }
        return this;
    }

    public static class Range<T1> {
        private ParamValue<T1> from;
        private ParamValue<T1> to;

        public Range(ParamValue<T1> from, ParamValue<T1> to) {
            this.from = from;
            this.to = to;
        }

        public ParamValue<T1> getFrom() {
            return from;
        }

        public ParamValue<T1> getTo() {
            return to;
        }
    }
}
