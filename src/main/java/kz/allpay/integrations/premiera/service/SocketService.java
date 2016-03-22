package kz.allpay.integrations.premiera.service;

import kz.allpay.integrations.premiera.requests.AbstractRequest;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *
 exec 1337<>/dev/tcp/hidden/9195
 echo '0000000082&ServiceID=1&QueryCode=GetHalls&DateList=&Theatres=&Encoding=UTF-8&Version=3' >&1337
 cat <&1337
 exec 42>&1337

 exec 1337<>/dev/tcp/hidden/9195
 echo '0000000082&ServiceID=1&QueryCode=GetHalls&DateList=&Theatres=&Encoding=Windows-1251&Version=3' >&1337
 cat <&1337 | iconv -f "windows-1251" -t "UTF-8"
 exec 42>&1337
 *
 */
public class SocketService implements AutoCloseable {
    private static final Logger log = Logger.getLogger(SocketService.class.getName());

    private final String host;
    private final int port;
    private Socket socket = null;
    /** not used yet */
    private final int serviceId = 2;
    /** not used yet */
    private final int httpPort = 8080;

    private static final int lengthOfSizeBlock = 10;

    /**
     * This class abstracts from xml marshalling using jaxb implementation
     * @author mark jay
     */
    private static final class XmlMarshaller {
        private JAXBContext jaxbContext = null;
        private Marshaller marshaller = null;
        private Unmarshaller unmarshaller = null;
        public void init(Class<?> clazz) throws JAXBException {
            if (jaxbContext == null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

                unmarshaller = jaxbContext.createUnmarshaller();
            }
        }
        public <T> String marshall(String qName, Class<T> clazz, T instance) throws JAXBException {
            init(clazz);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(
                    new JAXBElement<T>(new QName(qName), clazz, instance),
                    stringWriter);
            return stringWriter.toString();
        }
        public <T> T unmarshall(String content, Class<T> clazz) throws JAXBException {
            init(clazz);
            try {
                StreamSource xmlSource = new StreamSource(new StringReader(content));
                JAXBElement<T> jaxbElement = unmarshaller.unmarshal(xmlSource, clazz);
                T instance = jaxbElement.getValue();
                return instance;
            } catch (javax.xml.bind.UnmarshalException ex) {
                log.warning("could not unmarshall response, response was = " + content);
                System.out.println("could not unmarshall response, response was = " + content);
                throw ex;
            }
        }
    }
    private static XmlMarshaller xmlMarshaller = new XmlMarshaller();

    public SocketService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public <ResponseType> ResponseType sendSocketRequest(AbstractRequest<ResponseType> request)
            throws IOException, JAXBException {
        String responseAsString = sendSocketRequestPlain(request);
        return xmlMarshaller.unmarshall(responseAsString, request.getResponseClass());
    }

    public String sendSocketRequestPlain(String requestAsString) throws IOException {
        log.info("sending request : " + requestAsString);

        Socket socket = getSocket();
        InputStreamReader in = new InputStreamReader(socket.getInputStream(), "utf-8");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(requestAsString);

        int length = readLength(in)-1;
        log.info("server answered length : " + length);
        in.read(); // skipping the first '&' symbol

        String answer = readAnswer(in, length);
        log.info("server answered : " + answer);

        return answer;
    }

    /**
     * for some reason I could make it work with {@code java.io.InputStreamReader#read(char[])}
     * @param in
     * @param length
     * @return
     * @throws IOException
     */
    private String readAnswer(InputStreamReader in, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader in1 = new BufferedReader(in);
        System.out.println("test");
        for (int i = 0; i< length; i++) {
            char read = (char)in1.read();
            if (read == -1) {
                throw new IllegalStateException("must not be here");
            }
            sb.append(read);
        }
        System.out.println("in1.read() = " + in1.read());
        System.out.println("in.read() = " + in.read());
        return sb.toString().replaceAll("</XML>.*", "</XML>");
    }

    public String sendSocketRequestPlain(AbstractRequest<?> request) throws IOException {
        return sendSocketRequestPlain(request.toRequest());
    }

    private int readLength(InputStreamReader in) throws IOException {
        char[] buff = new char[lengthOfSizeBlock];
        in.read(buff, 0, 10);
        return Integer.parseInt(new String(buff));
    }

    private Socket getSocket() throws IOException {
        if (socket == null) {
            log.info("Creating socket to '" + host + "' on port " + port);
            socket = new Socket(host, port);
        }
        return socket;
    }

    @Override
    public void close() throws Exception {
        if (socket != null) {
            log.info("Closing socket to '" + host + "' on port " + port);
            socket.close();
            socket = null;
        }
    }
}
