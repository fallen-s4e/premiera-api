package kz.allpay.integrations.premiera.requests;

import kz.allpay.integrations.premiera.requests.generated.GetHallsResponse;
import kz.allpay.integrations.premiera.requests.params.ParamValue;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 12:51 PM
 */
public class GetHalls extends AbstractRequest<GetHallsResponse> {
    private ParamValue<?> dateList;
    private ParamValue<?> theatres;

    @Override
    public Class<GetHallsResponse> getResponseClass() {
        return GetHallsResponse.class;
    }

    public GetHalls setDateList(ParamValue<?> dateList) {
        this.dateList = dateList;
        return this;
    }

    public GetHalls setTheatres(ParamValue<?> theatres) {
        this.theatres = theatres;
        return this;
    }
}
