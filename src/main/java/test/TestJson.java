package test;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestJson {

    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        String example = "{catalog:[{app:tuangou-web,layout:[{ip: 192.168.1.1,id:[id1,id2,id3]},{ip:192.168.1.2,id:[id4,id5,id6]}]}" +
        		",{app:hippo-web,layout:[{ip: 192.168.1.1,id:[id7,id8]},{ip:192.168.1.3,id:[id9,id10]}]}]}";
        String jsonStr = java.net.URLDecoder.decode(example, "utf-8");
        JSONObject rootObject = new JSONObject(jsonStr);
        JSONArray catalogArray = rootObject.getJSONArray("catalog");
        for (int i = 0; i < catalogArray.length(); i++) {
            JSONObject layoutObject = catalogArray.getJSONObject(i);
            String app = (String)layoutObject.get("app");
            System.out.println("app: " + app);
            JSONArray layoutArray = layoutObject.getJSONArray("layout");
            for (int j = 0; j < layoutArray.length(); j++) {
                JSONObject hostIdObject = layoutArray.getJSONObject(j);
                String ip = (String) hostIdObject.get("ip");
                System.out.println("app: " + app + ", ip: " + ip);
                JSONArray idArray = hostIdObject.getJSONArray("id");
                for (int k = 0; k < idArray.length(); k++) {
                    System.out.println("app: " + app + ", ip: " + ip + ", id: " + idArray.getString(k));
                }
            }
        }
        
    }

}
