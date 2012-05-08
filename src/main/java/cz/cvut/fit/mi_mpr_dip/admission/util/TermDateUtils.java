package cz.cvut.fit.mi_mpr_dip.admission.util;

import java.text.ParseException;
import java.util.Date;

public interface TermDateUtils {

	public Date fromUnderscoredIso(String text) throws ParseException;

	public Date fromIso(String text) throws ParseException;
	
	public String toUnderscoredIso(Date date);
	
	public String toIso(Date date);
}
