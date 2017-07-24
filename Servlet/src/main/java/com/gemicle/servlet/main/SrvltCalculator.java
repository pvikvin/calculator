package com.gemicle.servlet.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SrvltCalculator")
public class SrvltCalculator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String str_a = request.getParameter("a");
		String str_b = request.getParameter("b");
		String str_op = request.getParameter("op");		
		
		double value_a = 0;
		double value_b = 0;
		
		boolean noError = true;
		
		try {
			value_a = Double.parseDouble(str_a);
			value_b = Double.parseDouble(str_b);
		}
		catch ( Exception ex ) {
			noError = false;
		}
		
		if ( noError ) {
			
			double result = 0;
			
			try {
				
				if (str_op.equals("+")) result = functionSum( value_a, value_b );
				else
					if (str_op.equals("-")) result = functionDif( value_a, value_b );
					else
						if (str_op.equals("*")) result = functionMul( value_a, value_b );
						else
							if (str_op.equals("/") && (value_b!=0)) result = functionDiv( value_a, value_b );
							else
								noError = false;
			}
			catch ( Exception ex ) {
				noError = false;
			}
			
			if ( noError ) {
				doSetResult( response, result );
				return;
			}
			
		}
		
		doSetError( response );
	}
	
	protected void doSetResult( HttpServletResponse response, double result ) throws UnsupportedEncodingException, IOException {
		String reply = "{\"error\":0,\"result\":" + Double.toString(result) + "}";
		response.getOutputStream().write( reply.getBytes("UTF-8") );
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus( HttpServletResponse.SC_OK );
	}		
	
	protected void doSetError( HttpServletResponse response ) throws UnsupportedEncodingException, IOException {
		String reply = "{\"error\":1}";
		response.getOutputStream().write( reply.getBytes("UTF-8") );
		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus( HttpServletResponse.SC_OK );
	}
	
	protected double functionSum( double a, double b ) {
		return a + b;
	}
	
	protected double functionDif( double a, double b ) {
		return a - b;
	}
	
	protected double functionMul( double a, double b ) {
		return a * b;
	}
	
	protected double functionDiv( double a, double b ) {
		return a / b;
	}
	
}
