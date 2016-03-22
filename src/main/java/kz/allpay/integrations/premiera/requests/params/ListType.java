package kz.allpay.integrations.premiera.requests.params;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 6:15 PM
 */
public enum ListType {
    BusyPlaces, // – процент занятости мест
    Locked, // – в заблокированные сеансы будет добавлять атрибут Locked='1', а в незаблокированные атрибут не добавляется
    Delete, // – удаленные сеансы
    MovieName, // – в ответ добавляется название фильма
    NotCalcPlacesCount // – без подсчета мест (ускоряет работу запроса)
    ;
}
