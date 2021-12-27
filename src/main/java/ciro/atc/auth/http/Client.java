package ciro.atc.auth.http;


import ciro.atc.config.log.Log;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.util.Collections;
import java.util.Map;


@Setter
@Getter
public class Client // extends HttpHeaders
{

   // final public String host;

    //final private static String HTTP = "http://%s";

   // final public Function<RequestFactory, HttpHeaders> head;

   // final private HttpClientBuilder composer = HttpClients.custom();

    //@Value("${rest.time.out}")
    //private int TIME_OUT;
    /*
    public Client(String url, String name, String key) {
        setContentType(MediaType.APPLICATION_JSON);
        host = url.startsWith("http") ? url : String.format(HTTP, url);
        head = s -> key != null ? by(s, name, key) : this;
    }*/

    private HttpHeaders headers = new HttpHeaders();

    public Client() {
    }

   /* public HttpHeaders simpleHeader(){
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }*/

    public void mediaTypeHeader(int contentType){
        if(contentType == 1){
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }else if (contentType == 2){
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
    }

    public void authentication (int typeAuth, String authKey, String auth){
        if(typeAuth == 2 || typeAuth == 3){
            String key = String.format(authKey, auth);
            headers.add("Authorization", key);
        } else if(typeAuth== 4){
            headers.add(authKey, auth);
        }
    }

    public void authentication (int typeAuth, String key){
        if(typeAuth == 2 || typeAuth == 3){
            //String key = String.format(authKey, auth);
            headers.add("Authorization", key);
        }/* else if(typeAuth== 4){
            headers.add(authKey, auth);
        }*/
    }

    public void addSomeHeaders(Map<String, String> h){

        for (Map.Entry<String, String> entry : h.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }

    }




   /* private HttpHeaders by(RequestFactory service, String name, String key) {
        add(name, key);
        service.getMessageConverters().add(
                new MappingJackson2HttpMessageConverter()
        );
        return this;
    }*/

     public static HttpComponentsClientHttpRequestFactory build(int TIME_OUT) {
         HttpComponentsClientHttpRequestFactory http = null;
         try {
             final TrustStrategy accepting = (c, a) -> true;
             final SSLContext context = SSLContexts.custom().loadTrustMaterial(null, accepting).build();
             final SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
             final Registry<ConnectionSocketFactory> socket = RegistryBuilder.<ConnectionSocketFactory>create()
                     .register("https", ssl).register("http", new PlainConnectionSocketFactory()).build();
             final BasicHttpClientConnectionManager manager = new BasicHttpClientConnectionManager(socket);
             final CloseableHttpClient https = HttpClients.custom().setSSLSocketFactory(ssl).setConnectionManager(manager).build();
//        return new HttpComponentsClientHttpRequestFactory(https);
            // HttpComponentsClientHttpRequestFactory
                     http = new HttpComponentsClientHttpRequestFactory(https);

             http.setConnectionRequestTimeout(TIME_OUT);
             http.setConnectTimeout(TIME_OUT);
             http.setReadTimeout(TIME_OUT);


         } catch (Exception e){
             Log.error("CLIENT: " , e.getMessage());
         }

         return http;

    }
}
