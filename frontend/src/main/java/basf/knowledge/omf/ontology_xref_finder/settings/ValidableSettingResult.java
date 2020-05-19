package basf.knowledge.omf.ontology_xref_finder.settings;


public class ValidableSettingResult {
	public Integer status; // JOptionPane.ERROR_MESSAGE
	public String message; // Message
	
	public ValidableSettingResult() {
	
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
