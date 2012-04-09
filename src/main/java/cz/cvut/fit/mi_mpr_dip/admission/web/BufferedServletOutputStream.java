package cz.cvut.fit.mi_mpr_dip.admission.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class BufferedServletOutputStream extends ServletOutputStream {
	private final ServletOutputStream output;
	private final ByteArrayOutputStream body = new ByteArrayOutputStream();

	public BufferedServletOutputStream(ServletOutputStream output) {
		this.output = output;
	}

	@Override
	public void write(int i) throws IOException {
		byte[] b = { (byte) i };
		body.write(b, 0, 1);
		output.write(b, 0, 1);
	}

	public String getBody() {
		return new String(body.toByteArray());
	}
}
