package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servermodel.Inventory;

import java.util.HashMap; 
import java.util.Map; 
import java.util.Scanner;

/**
 * Servlet implementation class InventoryAPI
 */
@WebServlet("/InventoryAPI")
public class InventoryAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Inventory invObj = new Inventory();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InventoryAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


		String output = invObj.insertInventory(request.getParameter("invItemCode"),
											request.getParameter("invItemName"),
											request.getParameter("stockQty"),
											request.getParameter("manufactYr"),
											request.getParameter("latestRepairDate") );
		
		response.getWriter().write(output);
		
	}
	
	/*
	 * To read the request parameters in doPut() and doDelete(), we will use a custom method. 
	 * This method reads the request parameters, store them in a Map, and returns. 
	 * 
	 * */
	
	// Convert request parameters to a Map
	private static Map<String, String> getParasMap(HttpServletRequest request) 
	{ 
		Map<String, String> map = new HashMap<String, String>(); 
		try
		{ 
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : ""; 
			scanner.close(); 
			
			String[] params = queryString.split("&"); 
			for (String param : params) 
			{ 
				String[] p = param.split("="); 
				map.put(p[0], p[1]); 
			} 
		} 
		catch (Exception e) 
		{ 
		} 
		return map; 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String, String> paras = getParasMap(request); 
		
		String output = invObj.updateInventory(paras.get("hidInvIDSave").toString(),
											paras.get("invItemCode").toString(),
											paras.get("invItemName").toString(),
											paras.get("stockQty").toString(),
											paras.get("manufactYr").toString(),
											paras.get("latestRepairDate").toString() );
		
		response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Map<String, String> paras = getParasMap(request); 
		
		String output = invObj.deleteInventory(paras.get("ItemID").toString()); 
		response.getWriter().write(output); 
	}

}