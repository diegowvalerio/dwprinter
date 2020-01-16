package br.com.dwprinter.bean;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;


import org.omnifaces.util.Faces;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Relatorio{
	
	private HttpServletResponse response;
	private FacesContext context;
	private Connection con;
	
	public Relatorio() {
		this.context = FacesContext.getCurrentInstance();
		this.response = (HttpServletResponse) this.context.getExternalContext().getResponse();
	}
	
	public void imprimeetiqueta(String produto, Integer qtde_etiqueta, Integer quantidade,Float peso){		
		try{
			String caminho = "";
			caminho = Faces.getRealPath("/pages/relatorios/etiqueta/etiqueta_estoque");
		
			JasperCompileManager.compileReportToFile(caminho+".jrxml");
			JasperReport rp = (JasperReport) JRLoader.loadObjectFromFile(caminho+".jasper");
			
			Map<String, Object> params = new HashMap<String, Object>();
			
					
			params.put("PRODUTOID", produto);
			params.put("QTDE_ETIQUETA", qtde_etiqueta);
			params.put("PESO", peso);
			params.put("QUANTIDADE", quantidade);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperPrint print = JasperFillManager.fillReport(rp, params,  getConexao());
						
			
			JasperExportManager.exportReportToPdfStream(print, baos);
			
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition","inline; filename=relatorio.pdf");
			response.getOutputStream().write(baos.toByteArray());
			response.getOutputStream().flush();
			response.getOutputStream().close();
			context.responseComplete();
			closeConnection();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Erro ao gerar o relatorio!"));
		}
	}

	/*conexao*/
	private Connection getConexao(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@MSERVER2:1521:AWORKSDB", "SEVEN", "SEVEN");
			return con;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void closeConnection(){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public String usuarioconectado() {
		String nome;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			nome = ((UserDetails) principal).getUsername();
		} else {
			nome = principal.toString();
		}
		// System.out.println(nome);
		return nome;
	}
}
