package com.akiradata.orca;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.akiradata.orca.model.ObjectFactory;
import com.akiradata.orca.model.OrcaProjectType;

public class ProjectUtil {

	public static OrcaProjectType createProject(String name, File location) throws IOException, JAXBException{
		
		location.createNewFile();
		ObjectFactory of = new ObjectFactory();
		
		OrcaProjectType proj = of.createOrcaProjectType();
		proj.setTitle(name);
		proj.setVersion("0.7");
		
		JAXBElement<OrcaProjectType> rootElem = of.createOrcaProject(proj);
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Marshaller m = jaxbContext.createMarshaller();
		m.marshal(rootElem, location);
		
		return proj;		
	}

	public static void closeProject(OrcaProjectType project) {

	}

	public static OrcaProjectType openProject(File location) throws IOException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller m = jaxbContext.createUnmarshaller();
		JAXBElement<OrcaProjectType> rootElem = m.unmarshal(new StreamSource(location), OrcaProjectType.class);
		return rootElem.getValue();
	}

}
