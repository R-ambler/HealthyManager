package HM.Comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HRP.Comm.Util.HLogger;

/**
 * 项目公共方法
 * @author Administrator
 *
 */
public class CommFunction {

	public static String characterEncoding = CommConstant.characterEncoding;
	
	public static String getRequest(HttpServletRequest request){
		//建立接收流缓冲，准备处理
		String result = "";
		try {
			//获得响应流，获得输入对象
			InputStream inputStream = request.getInputStream();

			StringBuffer requestBuffer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, characterEncoding));

			//读入流，并转换成字符串
			String readLine;
			while ((readLine = reader.readLine()) != null) {
			    requestBuffer.append(readLine).append("\n");
			}
			reader.close();
			result = requestBuffer.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
	}
	
	public static void setResponse(HttpServletResponse response,String responseString){
		PrintWriter out;
		try {
			out = response.getWriter();
			//设置编码
	         response.setHeader("content-type", "text/html;charset="+characterEncoding);
	        response.setCharacterEncoding(characterEncoding);
	        //编码
	        String resultString = URLEncoder.encode(responseString,"UTF-8");
	        //响应输出
	        out.println(resultString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 关闭数据库连接
	public static void toCloseDBCon(ResultSet rs, PreparedStatement ps, Connection con,
			Statement st) {
		HLogger.info("CommFunction Function toClose Begin.");
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (st != null) {
				st.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HLogger.info("CommFunction Function toClose End.");
	}
}
