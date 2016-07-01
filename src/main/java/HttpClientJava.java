import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * Created on 2016/6/17
 *
 * @author feng.wei
 */
public class HttpClientJava {


    public static void main(String[] args) throws IOException {
        String url = "http://datacube152:8080/api/v1/views/TEZ/versions/0.7.0.2.3.4.0-70/instances/TEZ_CLUSTER_INSTANCE/resources/atsproxy/ws/v1/timeline/TEZ_DAG_ID?limit=1&_=1466145572270";

        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.setRequestHeader("cookie","AMBARISESSIONID=18tq68c97f563j68zyl1kteeg");
        httpClient.executeMethod(getMethod);

        String response = getMethod.getResponseBodyAsString();

        JSONObject jsonObject = new JSONObject();
        jsonObject = JSONObject.parseObject(response).getJSONArray("entities").getJSONObject(0);
        String timeTaken = jsonObject.getString("timeTaken");

        System.out.println(jsonObject.getJSONObject("primaryfilters").getJSONArray("dagName").getString(0));

    }
}
