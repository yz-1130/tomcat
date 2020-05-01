package server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor extends Thread {

    private Socket socket;
    private Map<String,HttpServlet> servletMap;

    private Map<String,HttpServlet> stringHttpServletMap;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> servletMap,Map<String, HttpServlet> stringHttpServletMap) {
        this.socket = socket;
        this.servletMap = servletMap;
        this.stringHttpServletMap = stringHttpServletMap;
    }

    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();

            // 封装Request对象和Response对象
            Request request = new Request(inputStream);
            Response response = new Response(socket.getOutputStream());

            // 静态资源处理
            if(stringHttpServletMap.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            }else{
                // 动态资源servlet请求
                HttpServlet httpServlet = stringHttpServletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }

            socket.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
