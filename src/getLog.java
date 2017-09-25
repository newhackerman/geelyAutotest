import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.java.accessibility.util.EventID;

/**
 * Servlet implementation class getLog
 */
@WebServlet(description = "读取文件", urlPatterns = { "/getLog" })
public class getLog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());

		InputStream str = request.getInputStream();
		System.out.println(str.toString());

		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); // 解决中文乱码问题
		response.setHeader("Cache-Control", "no-cache"); // 取消在本地的缓存
		response.setHeader("Pragma", "no-cache");
		// response.setDateHeader("Expires", -1); //不需要缓存
		PrintWriter out = response.getWriter(); // 返回响应

		String resp;
		String filepath = request.getSession().getServletContext().getRealPath("/");
		System.out.println("获取路径为:\t" + filepath);
		String sfilename = request.getParameter("filename");
		//System.out.println("要读取的文件名为:\t" + sfilename);
		String tline = request.getParameter("line");
		if (tline == null) {
			resp = "line 为必传参数";
		}
		int sline = Integer.parseInt(tline);
		
		if (filepath == null) {
			filepath="/usr/local/test/apache-tomcat-8.0.17/webapps/GetParameter";
			System.out.println("dir is not exist ");
		}
		if (sfilename == null) {
			sfilename = "catalina.out";
		}
		if (sline <= 0 || sline > 100000) {
			System.out.println("行数大于500或小于0时,默认为500行!");
			sline = 500;
		}
		// if(sparaname==null){sparaname="eventId";}
		// 去取文件内容:
		readlogfile filecontend = new readlogfile();
		resp = filecontend.getlog(filepath, sfilename, sline);

		System.out.println("get a response cocntend is :" + resp);
		if (resp == null) {
			System.out.println("未读到内容");
			throw new IOException("read error! ");
		}
		// 返回给调用方:

		out.write(resp);
		out.flush();
		out.close();
	}

}
