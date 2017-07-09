package Comm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.rambler.healthymanager.R;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 06/01 0001.
 */
public class Service {
    /**
     * 与服务端交互
     *
     * @param opNameString
     * @param jsonDataString
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public String toVisitServer(Context context, String opNameString, String jsonDataString) {
        Log.i("Service", "Function toVisitServer Begin.");
        String resultDecoder = "";
        try {
            //组装数据
            JSONArray jsonArray = new JSONArray();
            JSONObject opName = new JSONObject();
            JSONObject jsonData = new JSONObject();
            opName.put("OP_NAME", opNameString);
            jsonData.put("JsonData", jsonDataString);
            jsonArray.put(opName);
            jsonArray.put(jsonData);

            String jsonString = jsonArray.toString();

            URL serverURL = new URL(CommVariable.serverURL);     //服务端访问路径
            String serverEncoding = CommVariable.serverEncoding; //服务端编码格式
            String serverRequestMethod = CommVariable.serverRequestMethod;//服务端访问模式（POST or GET）
            HttpURLConnection conn = (HttpURLConnection) serverURL.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(serverRequestMethod);
            conn.setConnectTimeout(3*1000);
            conn.setReadTimeout(3*1000);
            conn.connect();

            PrintWriter pw = new PrintWriter(conn.getOutputStream());
            pw.print(jsonString);
            pw.flush();
            pw.close();

            InputStream inputStream = conn.getInputStream();
            String result = IOUtils.toString(inputStream, serverEncoding);
            //解码
            resultDecoder = URLDecoder.decode(result);
            Log.i("Service", "服务端返回结果集为："+resultDecoder);
        } catch (MalformedURLException e) {
            Log.e("Service", "Function toVisitServer MalformedURLException");
        } catch (JSONException e) {
            Log.e("Service", "Function toVisitServer JSONException");
        } catch (IOException e) {
            Log.e("Service", "Function toVisitServer IOException");
            Toast.makeText(context.getApplicationContext(), "连接服务器失败，请检查网络或稍后重试！", Toast.LENGTH_SHORT).show();
        }
        Log.i("Service", "Function toVisitServer End.");
        return resultDecoder;
    }
}
