
package com.baeldung.soap.ws.client.generated;

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
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pricelist" type="{http://www.baeldung.com/springsoap/gen}pricelist"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pricelist"
})
@XmlRootElement(name = "getPricelistRequest")
public class GetPricelistRequest {

    @XmlElement(required = true)
    protected Pricelist pricelist;

    /**
     * Gets the value of the pricelist property.
     * 
     * @return
     *     possible object is
     *     {@link Pricelist }
     *     
     */
    public Pricelist getPricelist() {
        return pricelist;
    }

    /**
     * Sets the value of the pricelist property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pricelist }
     *     
     */
    public void setPricelist(Pricelist value) {
        this.pricelist = value;
    }

}
