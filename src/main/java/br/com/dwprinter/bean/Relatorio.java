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
	
	public void imprimecheque(String nome, double valor, String cidade, String valorextenso, Date data, boolean cruzar){
		Locale local = new Locale("pt","BR");
		DateFormat formato = new SimpleDateFormat("MMMM",local);
		SimpleDateFormat formato2 = new SimpleDateFormat("dd");
		SimpleDateFormat formato3 = new SimpleDateFormat("yyyy");
		
		DecimalFormat df = new DecimalFormat("#,###.00");
		String valorformatado = df.format(valor); 
		
		
		String dia = formato2.format(data);
		String mes = formato.format(data);
		String ano = formato3.format(data);
		
		try{
			String caminho = "";
			caminho = Faces.getRealPath("/pages/reports/cheque/chequea4");
		
			JasperCompileManager.compileReportToFile(caminho+".jrxml");
			JasperReport rp = (JasperReport) JRLoader.loadObjectFromFile(caminho+".jasper");
			
			Map<String, Object> params = new HashMap<String, Object>();
			
					
			params.put("NOME", nome);
			params.put("VALOR", valorformatado);
			params.put("VALOREXTENSO", valorextenso);
			params.put("CIDADE", cidade);
			params.put("DIA", dia);
			params.put("MES", mes.toUpperCase());
			params.put("ANO", ano);
			params.put("USUARIO", usuarioconectado());
			params.put("CRUZAR", cruzar);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperPrint print = JasperFillManager.fillReport(rp, params, getConexao());
			
			print.setOrientation(OrientationEnum.LANDSCAPE);
			
			JasperExportManager.exportReportToPdfStream(print, baos);
					    		
			response.reset();
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			response.setHeader("Content-disposition","attachment; filename=relatorio.pdf");
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
			 String connectionUrl = "jdbc:sqlserver://SIGE\\SQLEXPRESS:1433;databaseName=SATLBASE"; 
			 
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connectionUrl, "sa", "@rv0re24Xcv");
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
