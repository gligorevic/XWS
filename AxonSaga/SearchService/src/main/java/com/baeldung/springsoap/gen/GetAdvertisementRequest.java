//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 11:30:50 AM CEST 
//


package com.baeldung.springsoap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="advertisement" type="{http://www.baeldung.com/springsoap/gen}advertisement"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "advertisement"
})
@XmlRootElement(name = "getAdvertisementRequest")
public class GetAdvertisementRequest {

    @XmlElement(required = true)
    protected Advertisement advertisement;

    /**
     * Gets the value of the advertisement property.
     * 
     * @return
     *     possible object is
     *     {@link Advertisement }
     *     
     */
    public Advertisement getAdvertisement() {
        return advertisement;
    }

    /**
     * Sets the value of the advertisement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Advertisement }
     *     
     */
    public void setAdvertisement(Advertisement value) {
        this.advertisement = value;
    }

}
