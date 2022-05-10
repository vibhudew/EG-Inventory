package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servermodel.Inventory;

/**
 * Servlet implementation class InventoryAPI
 */
@WebServlet("/InventoryAPI")
public class InventoryAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	Inventory invObj = new Inventory();
	
	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) 
	{ 
		Map<String, String> map = new HashMap<String, String>(); 
		try
		{ 
				Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
				String queryString = scanner.hasNext() ? 
						scanner.useDelimiter("\\A").next() : ""; 
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
     * @see HttpServlet#HttpServlet()
     */
    public InventoryAPI() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("invID"));
		int qty = Integer.parseInt(request.getParameter("stockQty"));
		

		String output = invObj.insertInventory(id,
											request.getParameter("invItemCode"),
											request.getParameter("invItemName"),
											qty,
											request.getParameter("manufactYr"),
											request.getParameter("latestRepairDate") );
		
		response.getWriter().write(output);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		
		//int qty = Integer.parseInt(paras.get("stockQty").toString());
		
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
		
		Map paras = getParasMap(request); 
		
		String output = invObj.deleteInventory(paras.get("invID").toString()); 
		
		response.getWriter().write(output);
	}

}
