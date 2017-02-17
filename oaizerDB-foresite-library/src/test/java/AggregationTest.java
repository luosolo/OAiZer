import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.dspace.foresite.Agent;
import org.dspace.foresite.Aggregation;
import org.dspace.foresite.OREException;
import org.dspace.foresite.OREFactory;
import org.dspace.foresite.ORESerialiser;
import org.dspace.foresite.ORESerialiserException;
import org.dspace.foresite.ORESerialiserFactory;
import org.dspace.foresite.ResourceMap;
import org.dspace.foresite.ResourceMapDocument;
import org.junit.Test;


public class AggregationTest {

	@Test
	public void test() throws OREException, URISyntaxException, ORESerialiserException {
		
		Aggregation aggregationView=OREFactory.createAggregation(new URI("http://www.aggregation.cazzi/"));
		ResourceMap rem= aggregationView.createResourceMap(new URI("http://www.aggregation.mazzi/"));
		Agent creator= OREFactory.createAgent(new URI("http://Dnet-LAB-EXPORTER"));
		List<Agent> creators= new ArrayList<Agent>();
		creators.add(creator);
		rem.setCreators(creators);
		aggregationView.setCreators(creators);	
		ORESerialiser serial= ORESerialiserFactory.getInstance("RDF/XML");
		ResourceMapDocument doc= serial.serialise(rem);
		System.out.println(doc.toString());		
		
	}

}
