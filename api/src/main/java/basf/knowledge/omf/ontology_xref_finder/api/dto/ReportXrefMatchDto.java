package basf.knowledge.omf.ontology_xref_finder.api.dto;

import java.util.HashSet;
import java.util.Set;

public class ReportXrefMatchDto {
	private String text;
	private Set<ReportXrefMatchItemDto> xrefs;

	public ReportXrefMatchDto(String text, Set<ReportXrefMatchItemDto> reportXrefMatchItemDto) {
		this.text = text;
		this.xrefs = reportXrefMatchItemDto;
	}

	public ReportXrefMatchDto(String text) {
		this.text = text;
		this.xrefs = new HashSet<ReportXrefMatchItemDto>();
	}

	public void addReportXrefMatchItemDto(ReportXrefMatchItemDto item) {
		this.xrefs.add(item);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<ReportXrefMatchItemDto> getXrefs() {
		return xrefs;
	}

	public void setXrefs(Set<ReportXrefMatchItemDto> xrefs) {
		this.xrefs = xrefs;
	}

}
