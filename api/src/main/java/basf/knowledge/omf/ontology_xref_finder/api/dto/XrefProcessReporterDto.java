package basf.knowledge.omf.ontology_xref_finder.api.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basf.knowledge.omf.ontology_xref_finder.core.interfaces.IXrefProcessReporter;
import basf.knowledge.omf.ontology_xref_finder.core.model.ReportItemXrefMatch;

public class XrefProcessReporterDto {
	private List<ReportXrefMatchDto> xrefFound = new ArrayList<ReportXrefMatchDto>();

	public XrefProcessReporterDto(IXrefProcessReporter reporter) {
		Map<String, ReportXrefMatchDto> xrefFoundMap = new HashMap<String, ReportXrefMatchDto>();
		
		for (ReportItemXrefMatch xrefMatch: reporter.getXrefFound()) {
			ReportXrefMatchDto xrefMatchDto = null;
			ReportXrefMatchItemDto xrefMatchItemDto = new ReportXrefMatchItemDto(
					xrefMatch.getXrefLabel(), xrefMatch.getXrefIri().getIRIString(), 
					xrefMatch.getOntology(), xrefMatch.getxRefSynonymLabels()
			);
			if (xrefFoundMap.containsKey(xrefMatch.getInputLabel())) {
				xrefMatchDto = xrefFoundMap.get(xrefMatch.getInputLabel());
			} else {
				xrefMatchDto = new ReportXrefMatchDto(xrefMatch.getInputLabel());
				xrefFoundMap.put(xrefMatch.getInputLabel(), xrefMatchDto);
			}
			xrefMatchDto.addReportXrefMatchItemDto(xrefMatchItemDto);
		}
		
		xrefFound.addAll(xrefFoundMap.values());
		
	}

	public List<ReportXrefMatchDto> getXrefFound() {
		return xrefFound;
	}

	public void setXrefFound(List<ReportXrefMatchDto> xrefFound) {
		this.xrefFound = xrefFound;
	}

	

}
