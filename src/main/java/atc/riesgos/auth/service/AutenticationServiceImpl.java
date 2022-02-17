package atc.riesgos.auth.service;


import atc.riesgos.auth.dto.auth.JWT;
import atc.riesgos.auth.dto.menu.MenuPath;
import atc.riesgos.auth.http.Client;
import atc.riesgos.config.log.Log;
import atc.riesgos.auth.dto.menu.MenuUI;
import atc.riesgos.auth.http.Loginable;

//import atc.riesgos.dao.rest.RequestFactoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AutenticationServiceImpl implements AutenticationService {

    @Value("${auth.time.out}")
    private int timeout;

    @Value("${auth.authentication.uri}")
    private String authUri;

    @Value("${auth.uimenu.uri}")
    private String uiMenuUri;

    @Value("${auth.pathmenu.uri}")
    private String pathMenuUri;

    @Value("${auth.available.uri}")
    private String availableUri;

   // @Autowired
    //RequestFactoryService requestFactory;

    //<editor-fold defaultstate="collapsed" desc="Public Function">

    public ResponseEntity<?> authentication(Loginable data,HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        JWT response = postBy(data, JWT.class, authUri, 1, 4, "sistema", request.getHeader("sistema"));

        return ResponseEntity.ok().headers(responseHeaders).body(response);
    }

    public ResponseEntity<?> getMenuUI(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();

        Map<String, String> map = new HashMap<String, String>();
        map.put("sistema", request.getHeader("sistema"));



        MenuUI[] response = getBy(MenuUI[].class, uiMenuUri, 1, 2, request.getHeader("Authorization"),map);

        return ResponseEntity.ok().headers(responseHeaders).body(response);


    }

    public ResponseEntity<?> getMenuPath(HttpServletRequest request) {
        HttpHeaders responseHeaders = new HttpHeaders();
        Map<String, String> map = new HashMap<String, String>();
        map.put("sistema", request.getHeader("sistema"));

        MenuPath[] response = getBy(MenuPath[].class, pathMenuUri, 1, 2, request.getHeader("Authorization"),map);

        return ResponseEntity.ok().headers(responseHeaders).body(response);
        
    }

    public Boolean askAvailable(HttpServletRequest request) {
        try{
            Map<String, String> map = new HashMap<String, String>();
            map.put("sistema", request.getHeader("sistema"));

            return getBy(Boolean.class, availableUri, 1, 2, request.getHeader("Authorization"), map);
        }catch(Exception e){
            Log.log("Error de token: " + e);
        }

        return false;
    }


    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="private function">

    private <R extends Object> R postBy(Object body, Class<R> responseClass, String uri, int contentType,
                                        int typeAuth, String authKey, String auth) {
        return callPostBy(body, HttpMethod.POST, responseClass, uri, contentType, typeAuth, authKey, auth).getBody();
    }
    private <T extends Object> T getBy(Class<T> entity, String uri, int contentType, int typeAuth, String token, Map<String, String> someHeader){

        return callGetBy(HttpMethod.GET, entity, uri, contentType, typeAuth, token, someHeader).getBody();
    }

    // -----------------------------------------------


    private <B, R extends Object> ResponseEntity<R> callPostBy(B body, HttpMethod method, Class<R> responseClass, String uri, int contentType, int typeAuth, String authKey, String auth) {
        HttpEntity<B> entity = null;
        String statusCode = null;
        try {
            Client client = new Client();
            client.mediaTypeHeader(contentType);
            client.authentication(typeAuth, authKey, auth);

            entity = new HttpEntity<>(body, client.getHeaders()); // request

            ClientHttpRequestFactory requestFactory = Client.build(timeout);

            RestTemplate restTemplate = new RestTemplate(requestFactory);
            ResponseEntity<R> response = restTemplate.exchange(uri, method, entity, responseClass);

            statusCode = Log.toJSON(response.getStatusCodeValue());

            return response;

        } catch (Exception e) {
            Log.log("Error postBy: ", e);
        }

        return null;

    }
    private <T extends Object> ResponseEntity<T> callGetBy(HttpMethod method, Class<T> responseClass, String uri, int contentType,
                                                           int typeAuth, String token, Map<String, String> someHeader) {

        HttpEntity<T> entity = null;
        String statusCode = null;
        try {
            Client client = new Client();
            client.mediaTypeHeader(contentType);
            client.authentication(typeAuth, token);
            client.addSomeHeaders(someHeader);


            entity = new HttpEntity<>(client.getHeaders()); // request
            ClientHttpRequestFactory requestFactory = Client.build(timeout);

            RestTemplate restTemplate = new RestTemplate(requestFactory);

            ResponseEntity<T> response = restTemplate.exchange(uri, method, entity, responseClass);

            statusCode = Log.toJSON(response.getStatusCodeValue());

            return response;
        } catch (Exception e) {
            Log.log("Error getBy.: ", e);

        }
        return null;

    }


    //</editor-fold>

}
