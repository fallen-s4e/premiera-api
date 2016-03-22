package kz.allpay.integrations.premiera.requests;

import kz.allpay.integrations.premiera.requests.generated.GetSessionsResponse;
import kz.allpay.integrations.premiera.requests.params.ListType;
import kz.allpay.integrations.premiera.requests.params.ParamValue;

import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.str;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 12:51 PM
 */
public class GetSessions  extends AbstractRequest<GetSessionsResponse> {
    private ParamValue<?> dateList;
    private ParamValue<?> theatres;
    private ParamValue<?> movies;
    private ParamValue<String> listSort = str("CHLDM");
    private ParamValue<Boolean> entrance;
    private ParamValue<?> halls;
    private ParamValue<?> levels;
    private ParamValue<ListType> listType;

    public GetSessions setDateList(ParamValue<?> dateList) {
        this.dateList = dateList;
        return this;
    }

    public GetSessions setTheatres(ParamValue<?> theatres) {
        this.theatres = theatres;
        return this;
    }

    public GetSessions setMovies(ParamValue<?> movies) {
        this.movies = movies;
        return this;
    }

    public GetSessions setListSort(ParamValue<String> listSort) {
        this.listSort = listSort;
        return this;
    }

    public GetSessions setEntrance(ParamValue<Boolean> entrance) {
        this.entrance = entrance;
        return this;
    }

    @Override
    public Class<GetSessionsResponse> getResponseClass() {
        return GetSessionsResponse.class;
    }

    public GetSessions setHalls(ParamValue<?> halls) {
        this.halls = halls;
        return this;
    }

    public GetSessions setLevels(ParamValue<?> levels) {
        this.levels = levels;
        return this;
    }

    public GetSessions setListType(ParamValue<ListType> listType) {
        this.listType = listType;
        return this;
    }
}
