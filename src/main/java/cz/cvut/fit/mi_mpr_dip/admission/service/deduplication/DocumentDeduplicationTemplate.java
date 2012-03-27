package cz.cvut.fit.mi_mpr_dip.admission.service.deduplication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Document;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.DocumentType;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.Person;

@Service
public class DocumentDeduplicationTemplate implements PersonDeduplicationTemplate {

	@Override
	public void deduplicate(Person person) {
		Set<Document> documents = person.getDocuments();
		if (CollectionUtils.isNotEmpty(documents)) {
			deduplicateDocuments(documents);
		}
	}

	private void deduplicateDocuments(Set<Document> documents) {
		Set<DocumentType> documentTypes = collectDocumentTypes(documents);
		deduplicateDocumentTypes(documentTypes);
		deduplicateDocuments(documents, documentTypes);
	}

	private Set<DocumentType> collectDocumentTypes(Set<Document> documents) {
		Set<DocumentType> documentTypes = new HashSet<DocumentType>();
		for (Document document : documents) {
			documentTypes.add(document.getDocumentType());
		}
		return documentTypes;
	}

	private void deduplicateDocumentTypes(Set<DocumentType> documentTypes) {
		Set<DocumentType> replacements = new HashSet<DocumentType>();
		for (Iterator<DocumentType> iterator = documentTypes.iterator(); iterator.hasNext();) {
			DocumentType documentType = iterator.next();
			List<DocumentType> dbDocumentTypes = DocumentType.findDocumentTypesByNameEquals(documentType.getName())
					.getResultList();
			if (CollectionUtils.isNotEmpty(dbDocumentTypes)) {
				iterator.remove();
				replacements.add(dbDocumentTypes.get(0));
			}
		}
		documentTypes.addAll(replacements);
	}

	private void deduplicateDocuments(Set<Document> documents, Set<DocumentType> documentTypes) {
		for (DocumentType documentType : documentTypes) {
			for (Document document : documents) {
				if (document.getDocumentType().equals(documentType)) {
					document.setDocumentType(documentType);
				}
			}
		}
	}
}
