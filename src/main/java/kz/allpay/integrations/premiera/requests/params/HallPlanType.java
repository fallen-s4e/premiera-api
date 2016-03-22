package kz.allpay.integrations.premiera.requests.params;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 6:15 PM
 */
public enum HallPlanType {
    Info, // — информация о планах зала
    Background, //— цвет фона
    Image, // – информация о фоновом изображении
    Row, // — номер ряд
    Place, // — номер места
    X, // — X координата объекта
    Y, // — Y координата объекта
    Width, // — ширина объекта
    Height, // — высота объекта
    Type, // — идентификатор типа места
    Fragment, // – идентификатор фрагмента
    PassPoint, // – ID точки прохода
    Object, // — отображать дополнительные объекты (отличные от мест, например экран)
}
