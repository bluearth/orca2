//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.13 at 07:40:41 PM ICT 
//


package com.akiradata.orca.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.akiradata.orca.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OrcaProject_QNAME = new QName("http://www.akirdata.com/orca/orca-project", "orca-project");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.akiradata.orca.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrcaProjectType }
     * 
     */
    public OrcaProjectType createOrcaProjectType() {
        return new OrcaProjectType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrcaProjectType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.akirdata.com/orca/orca-project", name = "orca-project")
    public JAXBElement<OrcaProjectType> createOrcaProject(OrcaProjectType value) {
        return new JAXBElement<OrcaProjectType>(_OrcaProject_QNAME, OrcaProjectType.class, null, value);
    }

}
