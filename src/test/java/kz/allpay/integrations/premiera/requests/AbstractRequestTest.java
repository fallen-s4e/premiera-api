package kz.allpay.integrations.premiera.requests;


import kz.allpay.integrations.premiera.requests.generated.GetHallPlanResponse;
import kz.allpay.integrations.premiera.requests.generated.GetHallsResponse;
import kz.allpay.integrations.premiera.requests.generated.GetSessionsResponse;
import kz.allpay.integrations.premiera.requests.params.HallPlanType;
import kz.allpay.integrations.premiera.requests.params.ListType;
import kz.allpay.integrations.premiera.service.SocketService;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.*;
import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.en;
import static org.junit.Assert.*;

/**
 * @author magzhan.karasayev
 * @since 3/5/16 4:04 PM
 */
public class AbstractRequestTest {

    @Test
    public void simpleTest() {
        String str = new GetHalls().setVersion(num(3)).setEncoding(str("UTF-8")).toRequest();
        assertTrue(str.contains("QueryCode=GetHalls"));
        assertTrue(str.contains("Version=3"));
        assertTrue(str.contains("Encoding=UTF-8"));
    }

    @Test
    public void getHallsTest() throws Exception {
        AbstractRequest<GetHallsResponse> getHallsRequest = new GetHalls()
                .setDateList(list(null))
                .setTheatres(list(null))
                .setServiceID(num(2))
                .setVersion(num(3));

        try (SocketService socketService = new SocketService("<hidden>", 9195)) {
            String result = socketService.sendSocketRequestPlain(getHallsRequest.toRequest());
        }
    }

    @Test
    @Ignore("ignored because it produces huge output")
    public void getSessionsTest() throws Exception {
        AbstractRequest<GetSessionsResponse> getSessions = new GetSessions()
//                    .setTheatres(list(Arrays.asList(num(1))))
                .setMovies(list(null))
                .setHalls(list(null))
                .setLevels(list(null))
                .setEntrance(bool(false))
                .setDateList(list(null))
                .setListSort(str("CHLDM"))
                .setListType(en(ListType.BusyPlaces))
                .setVersion(num(3))
                .setArchive(num(0))
                .setExpect(str(""))
                .setServiceID(num(2))
                .setVersion(num(3));

        try (SocketService socketService = new SocketService("<hidden>", 9195)) {
            GetSessionsResponse result = socketService.sendSocketRequest(getSessions);
        }
    }

    @Test
//    @Ignore("ignored because it produces huge output")
    public void getHallPlanTest() throws Exception {
        AbstractRequest<GetHallPlanResponse> getSessions = new GetHallPlan()
                .setHalls(list(null))
                .setLevels(list(null))
                .setTheatres(list(null))
                .setListType(list(Arrays.asList(en(HallPlanType.Background))))
                .setVersion(num(3))
//                .setArchive(num(0))
//                .setExpect(str(""))
                .setServiceID(num(2))
                .setVersion(num(3))
                .setArchive(null)
                ;

        try (SocketService socketService = new SocketService("<hidden>", 9195)) {
            GetHallPlanResponse result = socketService.sendSocketRequest(getSessions);
        }
    }
}
