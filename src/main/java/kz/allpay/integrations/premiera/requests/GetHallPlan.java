package kz.allpay.integrations.premiera.requests;

import kz.allpay.integrations.premiera.requests.generated.GetHallPlanResponse;
import kz.allpay.integrations.premiera.requests.params.HallPlanType;
import kz.allpay.integrations.premiera.requests.params.ParamValue;

import java.util.List;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 12:51 PM
 */
public class GetHallPlan extends AbstractRequest<GetHallPlanResponse> {
    private ParamValue<?> theatres;
    private ParamValue<?> halls;
    private ParamValue<?> levels;
    private ParamValue<List<ParamValue<HallPlanType>>> listType;

    @Override
    public Class<GetHallPlanResponse> getResponseClass() {
        return GetHallPlanResponse.class;
    }

    public GetHallPlan setTheatres(ParamValue<?> theatres) {
        this.theatres = theatres;
        return this;
    }

    public GetHallPlan setHalls(ParamValue<?> halls) {
        this.halls = halls;
        return this;
    }

    public GetHallPlan setLevels(ParamValue<?> levels) {
        this.levels = levels;
        return this;
    }

    public GetHallPlan setListType(ParamValue<List<ParamValue<HallPlanType>>> listType) {
        this.listType = listType;
        return this;
    }
}
