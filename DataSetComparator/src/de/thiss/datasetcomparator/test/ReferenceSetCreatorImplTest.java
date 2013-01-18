package de.thiss.datasetcomparator.test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import de.thiss.datasetcomparator.ReferenceSetLoader;
import de.thiss.datasetcomparator.impl.ReferenceSetLoaderImpl;

public class ReferenceSetCreatorImplTest {

	@Test
	public void testInitReferenceSet() {
		ReferenceSetLoader impl = new ReferenceSetLoaderImpl("jdbc:mysql://localhost/SCMIS_Test?user=root&password=!Gold007!",
														"select * from ReferenceData");
		HashMap<String, Double> hashMap = impl.initializeReferenceSet();
		assertEquals(8, hashMap.size());
	}

}
