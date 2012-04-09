package cz.cvut.fit.mi_mpr_dip.admission.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class BufferedResponseWrapper extends HttpServletResponseWrapper {

	private int statusCode;

	private Map<String, String> headers = new HashMap<String, String>();

	private BufferedServletOutputStream output;
	private PrintWriter writer;

	private static final String CONTENT_LENGTH_HEADER_NAME = "Content-Length";
	private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
	private static final String CONTENT_LANGUAGE_HEADER_NAME = "Content-Language";
	private static final String LOCATION_HEADER_NAME = "Location";

	public BufferedResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		output = new BufferedServletOutputStream(response.getOutputStream());
		writer = new PrintWriter(output);
	}

	public int getStatusCode() {
		return statusCode == 0 ? HttpServletResponse.SC_OK : statusCode;
	}

	public String getHeader(String name) {
		String value = headers.get(name);
		return value;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getBody() throws IOException {
		writer.close();
		output.close();
		return output.getBody();
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		super.sendError(sc, msg);
		statusCode = sc;
	}

	@Override
	public void sendError(int sc) throws IOException {
		super.sendError(sc);
		statusCode = sc;
	}

	@Override
	public void setStatus(int sc, String sm) {
		super.setStatus(sc, sm);
		statusCode = sc;
	}

	@Override
	public void setStatus(int sc) {
		super.setStatus(sc);
		statusCode = sc;
	}

	@Override
	public void addDateHeader(String name, long date) {
		super.addDateHeader(name, date);
		insertHeaderValue(name, (new Date(date)).toString());
	}

	@Override
	public void addHeader(String name, String value) {
		super.addHeader(name, value);
		insertHeaderValue(name, value);
	}

	@Override
	public void addIntHeader(String name, int value) {
		super.addIntHeader(name, value);
		insertHeaderValue(name, toString(value));
	}

	@Override
	public void setDateHeader(String name, long date) {
		super.setDateHeader(name, date);
		insertHeaderValue(name, (new Date(date)).toString());
	}

	@Override
	public void setHeader(String name, String value) {
		super.setHeader(name, value);
		insertHeaderValue(name, value);
	}

	@Override
	public void setIntHeader(String name, int value) {
		super.setIntHeader(name, value);
		insertHeaderValue(name, toString(value));
	}

	private String toString(int value) {
		return Integer.toString(value);
	}

	@Override
	public void setContentLength(int len) {
		super.setContentLength(len);
		insertHeaderValue(CONTENT_LENGTH_HEADER_NAME, toString(len));
	}

	@Override
	public void setContentType(String type) {
		super.setContentType(type);
		insertHeaderValue(CONTENT_TYPE_HEADER_NAME, type);
	}

	@Override
	public void setLocale(Locale loc) {
		super.setLocale(loc);
		insertHeaderValue(CONTENT_LANGUAGE_HEADER_NAME, String.valueOf(loc));
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		insertHeaderValue(LOCATION_HEADER_NAME, location);
		statusCode = HttpServletResponse.SC_FOUND;
		super.sendRedirect(location);
	}

	private void insertHeaderValue(String name, String value) {
		headers.put(name, value);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return output;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}
}