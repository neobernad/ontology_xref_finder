package basf.knowledge.omf.ontology_xref_finder.core.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class PomParser {
	private Model pom = null;
	
	public PomParser() throws FileNotFoundException, IOException, XmlPullParserException {
		MavenXpp3Reader reader = new MavenXpp3Reader();
        pom = reader.read(new FileReader("pom.xml"));
	}

	public Model getPom() {
		return pom;
	}
	
}
