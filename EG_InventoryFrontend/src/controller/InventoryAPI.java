package controller;

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

 //========================================================IMPLEMENTS DC BUS===================================================================================
/**
 * Servlet implementation class CartAPI
 */
@WebServlet("/InventoryAPI")
public class InventoryAPI extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	Inventory inv = new Inventory();
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	//=================================================for INSERT OPERATION====================================================================
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = inv.insertInventory(request.getParameter("invCode"),
				request.getParameter("invName"),
				request.getParameter("manufact"),
				request.getParameter("stockQty"),
				request.getParameter("repair"),
				request.getParameter("handledBy"));
		response.getWriter().write(output);
	}

	
	// ========================================CONVERT REQUEST PARAMETERS TO MAP===============================================================================
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

	//======================================================for UPDATE OPEARTION================================================================================
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		String output = inv.updateInventory(paras.get("hidInvIDSave").toString(),
				paras.get("invCode").toString(),
				paras.get("invName").toString(),
				paras.get("manufact").toString(),
				paras.get("stockQty").toString(),
				paras.get("repair").toString(),
				paras.get("handledBy").toString());
		response.getWriter().write(output);
	}

	//===========================================================for DELETE OPERATION================================================================================
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map paras = getParasMap(request);
		String output = inv.deleteInventory(paras.get("invID").toString());
		response.getWriter().write(output);
	}
	

}
