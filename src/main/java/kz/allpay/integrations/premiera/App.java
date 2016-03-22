package kz.allpay.integrations.premiera;

import kz.allpay.integrations.premiera.requests.AbstractRequest;
import kz.allpay.integrations.premiera.requests.GetHalls;
import kz.allpay.integrations.premiera.requests.GetSessions;
import kz.allpay.integrations.premiera.requests.generated.GetHallsResponse;
import kz.allpay.integrations.premiera.requests.generated.GetSessionsResponse;
import kz.allpay.integrations.premiera.requests.params.ListType;
import kz.allpay.integrations.premiera.service.SocketService;

import static kz.allpay.integrations.premiera.requests.params.ParamValueFactory.*;

/**
 * If you think something is wrong here is an easier way to test their API in linux:
exec 1337<>/dev/tcp/<hidden>/9195
echo '0000000162&QueryCode=GetSessions&DateList=&Movies=&ListSort=CHLDM&Entrance=0&Halls=&Levels=&ListType=BusyPlaces&Encoding=Windows-1251&Version=3&ServiceID=2&Archive=0&Expect=' >&1337
cat <&1337 > temp-123.txt
 *
 */
public class App
{
    public static void main(String[] args) throws Exception {
        try (SocketService socketService = new SocketService("<hidden>", 9195)) {

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

            AbstractRequest<GetHallsResponse> getHallsRequest = new GetHalls()
                    .setDateList(list(null))
                    .setTheatres(list(null))
                    .setServiceID(num(2))
                    .setVersion(num(3));

//            String result = socketService.sendSocketRequestPlain(getHallsRequest.toRequest());
//            System.out.println("result = " + result);

            /*
            String result = socketService.sendSocketRequestPlain(
                    "0000000182&ServiceID=2&QueryCode=GetSessions&Movies=&Theatres=&Halls=&Levels=&DateList=23.09.2009;24.09.2009&ListSort=CHLDM&ListType=BusyPlaces&Encoding=Windows-1251&Version=3&Entrance=0&Archive=0&Expect=");
            System.out.println("result = " + result);
            */
            GetSessionsResponse result = socketService.sendSocketRequest(getSessions);

            System.out.println("result = " + result);
        }
    }


}
