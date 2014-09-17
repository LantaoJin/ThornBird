package test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpClientSingle {
    private static DefaultHttpClient httpClient = null;
    
    private HttpClientSingle(){};
    
    public static DefaultHttpClient instance(){
        if(httpClient == null){
            PoolingClientConnectionManager conMan = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault() );
            conMan.setMaxTotal(200);
            conMan.setDefaultMaxPerRoute(200);
            httpClient = new DefaultHttpClient(conMan);
            HttpParams params = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 60000);
            HttpConnectionParams.setSoTimeout(params, 20000);
            return httpClient;
        }
        return httpClient;
    }
    
    public static InputStream getResource(String uri) throws IOException {
        HttpGet method = new HttpGet(uri);
        HttpResponse httpResponse = instance().execute(method);
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        InputStream is = null;
        if (HttpStatus.SC_OK == statusCode) {
            System.out.println("200 OK request");
            is = httpResponse.getEntity().getContent();
        } else {
            EntityUtils.consume(httpResponse.getEntity());
            throw new IOException("Something went wrong, statusCode is " + statusCode);
        }
        return is;
    }
}
