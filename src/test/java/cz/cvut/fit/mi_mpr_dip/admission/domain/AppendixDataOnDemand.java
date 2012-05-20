package cz.cvut.fit.mi_mpr_dip.admission.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Appendix.class)
public class AppendixDataOnDemand {

	@Autowired
	private AppendixContentDataOnDemand appendixContentDataOnDemand;

	public Appendix getNewTransientAppendix(int index) {
		Appendix obj = new Appendix();
		setAppendixType(obj, index);
		setFilename(obj, index);
		setIdentifier(obj, index);
		setMimeType(obj, index);
		setAppendixContent(obj, index);
		return obj;
	}

	public void setAppendixContent(Appendix obj, int index) {
		AppendixContent appendixContent = appendixContentDataOnDemand.getRandomAppendixContent();
		appendixContent.setAppendix(obj);

		obj.setAppendixContent(appendixContent);
	}
}
