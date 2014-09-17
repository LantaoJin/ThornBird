package test;

import com.dianping.lion.EnvZooKeeperConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LionWriter {
    public final static String DEFAULT_LION_HOST = "http://lionapi.dp:8080/";
    public final static String LION_SET_PATH = "setconfig";
    public final static String LION_GET_PATH = "getconfig";
    public final static String LION_PROJECT = "blackhole";
    public final static String APPS = "blackhole.apps";
    public final static String APP_HOSTS_PREFIX = "blackhole.hosts.";
    public final static String APP_CONF_PREFIX = "blackhole.conf.";
    private int id = 71;

    public static void main(String[] args) throws IOException, InterruptedException {
        LionWriter writer = new LionWriter();
//        String watchKey = APP_HOSTS_PREFIX + "testapp";
//        String url = writer.generateGetURL(watchKey);
//        String response = writer.httpClientExec(url);
//        System.out.println(response);
//        String[] oldHosts = getStringListOfLionValue(response);
//        System.out.println(Arrays.toString(oldHosts));
//        String[] newHosts = Arrays.copyOf(oldHosts, oldHosts.length + 1);
//        newHosts[newHosts.length - 1] = "host3";
//        String newHostsLionString = getLionValueOfStringList(newHosts);
//        writer.change(watchKey, newHostsLionString);
        
        /** add new conf and hosts ***************
         * 
        File appfile = new File(args[0]);
        File detailFile = new File(args[1]);
        Set<String> app = new HashSet<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(appfile), "UTF-8"), 8 * 1024);
        String line = null;
        while ((line = reader.readLine()) != null) {
            app.add(line);
        }
        reader.close();
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(detailFile), "UTF-8"), 8 * 1024);
        String confString;
        String hostString;
        int num = 1;
        while ((line = reader.readLine()) != null) {
            //get
            System.out.print(num++ + " ");
            if (app.contains(line)) {
                System.out.println(line);
                hostString = reader.readLine();
                confString = reader.readLine();
                //change
                String response = writer.change("blackhole.conf." + line, confString);
                if (!response.startsWith("0")) {
                    break;
                }
                response = writer.change("blackhole.hosts." + line, hostString);
                if (!response.startsWith("0")) {
                    break;
                }
            } else {
                System.out.println("something wrong, continue");
                continue;
            }
        }
        ***********************************************/
        
        /** set cmdb app **********************/
        File setFile = new File(args[0]);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(setFile), "UTF-8"), 8 * 1024);
        String line = null;
        int num = 1;
        String confTmpString;
        while ((line = reader.readLine()) != null) {
            StringBuilder confBuilder = new StringBuilder();
            System.out.print(num++ + " ");
            if (line.startsWith("dpods_nginx_log_")) {
                confTmpString = line.split("_")[3];
                confBuilder.append("[").append("\"").append(confTmpString).append("\"").append("]");
                System.out.println("blackhole.cmdb." + line + "=>" + confBuilder.toString());
                String response = writer.change("blackhole.cmdb." + line, confBuilder.toString());
                if (!response.startsWith("0")) {
                    break;
                }
            } else {
                String[] tmp = line.split(" ");
                confTmpString = tmp[1];
                confBuilder.append("[").append("\"").append(confTmpString).append("\"").append("]");
                System.out.println("blackhole.cmdb." + tmp[0] + "=>" + confBuilder.toString());
                String response = writer.change("blackhole.cmdb." + tmp[0], confBuilder.toString());
                if (!response.startsWith("0")) {
                    break;
                }
            }
        }
    }
    
    public String change(String key, String value) throws InterruptedException {
        System.out.println("key=" + key + " value=" + value);
        String url = generateSetURL(key, value);
        System.out.println(url);
        String response = httpClientExec(url);
        System.out.println(response);
        Thread.sleep(2000);
        return response;
    }
    
    public String httpClientExec(String url) {
        System.out.println("http client access url: " + url);
        StringBuilder responseBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream is = null;
        try {
            is = HttpClientSingle.getResource(url);
        } catch (IOException e) {
            System.out.println("Can not get http response. " + e.getMessage());
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8 * 1024);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Oops, got an exception in reading http response content." + e.getMessage());
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
            }
        }
        return responseBuilder.toString();
    }
    
    public String generateGetURL(String key) {
        return DEFAULT_LION_HOST +
                LION_GET_PATH +
                generateURIPrefix() +
                "&k=" + key;
    }

    public String generateSetURL(String key, String value) {
        String encodedValue = "";
        try {
            encodedValue = URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return DEFAULT_LION_HOST +
                LION_SET_PATH +
                generateURIPrefix() +
                "&ef=1" +
                "&k=" + key +
                "&v=" + encodedValue;
    }
      

    private String generateURIPrefix() {
        return "?&p=" + LION_PROJECT +
                "&e=" + "product" + //EnvZooKeeperConfig.getEnv() +
                "&id=" + id;
    }
    
    public static String[] getStringListOfLionValue(String value) {
        if (value == null) {
            return null;
        }
        if (value.charAt(0) != '[' || value.charAt(value.length() - 2) != ']') {//2
            return null;
        }
        String[] tmp = value.substring(1, value.length() - 2).split(",");
        String[] result = new String[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            result[i] = tmp[i].substring(1, tmp[i].length() -1 );
        }
        return result;
    }

    //["host01","host02"]
    public static String getLionValueOfStringList(String[] hosts) {
        StringBuilder lionStringBuilder = new StringBuilder();
        lionStringBuilder.append('[');
        for (int i = 0; i < hosts.length; i++) {
            lionStringBuilder.append('"').append(hosts[i]).append('"');
            if (i != hosts.length - 1) {
                lionStringBuilder.append(',');
            }
        }
        lionStringBuilder.append(']');
        return lionStringBuilder.toString();
    }
}
