
package com.baeldung.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;


/**
 * <p>Java class for request complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="request"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userSentRequest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="crationDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="inBundle" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="startDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="endDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="adId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "request", propOrder = {
    "brandName",
    "modelName",
    "userEmail",
    "userSentRequest",
    "crationDate",
    "inBundle",
    "startDate",
    "endDate",
    "adId"
})
public class Request {

    @XmlElement(required = true)
    protected String brandName;
    @XmlElement(required = true)
    protected String modelName;
    @XmlElement(required = true)
    protected String userEmail;
    @XmlElement(required = true)
    protected String userSentRequest;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar crationDate;
    protected boolean inBundle;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    protected long adId;

    public Request(com.example.AgentApplication.domain.Request request) throws DatatypeConfigurationException {
        this.adId = request.getAdvertisement().getRemoteId();
        this.brandName = request.getAdvertisement().getCar().getBrand().getBrandName();
        this.modelName = request.getAdvertisement().getCar().getModel().getModelName();
        this.inBundle = false;
        this.userEmail = "agent@gmail.com";
        this.userSentRequest = request.getUserSentRequest().getEmail();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(request.getCreationDate());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.crationDate = date2;
        c.setTime(request.getStartDate());
        date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.startDate = date2;
        c.setTime(request.getEndDate());
        date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.endDate = date2;
    }

    public Request() {

    }

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandName(String value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the userEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the value of the userEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserEmail(String value) {
        this.userEmail = value;
    }

    /**
     * Gets the value of the userSentRequest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserSentRequest() {
        return userSentRequest;
    }

    /**
     * Sets the value of the userSentRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserSentRequest(String value) {
        this.userSentRequest = value;
    }

    /**
     * Gets the value of the crationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCrationDate() {
        return crationDate;
    }

    /**
     * Sets the value of the crationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCrationDate(XMLGregorianCalendar value) {
        this.crationDate = value;
    }

    /**
     * Gets the value of the inBundle property.
     * 
     */
    public boolean isInBundle() {
        return inBundle;
    }

    /**
     * Sets the value of the inBundle property.
     * 
     */
    public void setInBundle(boolean value) {
        this.inBundle = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the adId property.
     * 
     */
    public long getAdId() {
        return adId;
    }

    /**
     * Sets the value of the adId property.
     * 
     */
    public void setAdId(long value) {
        this.adId = value;
    }

}
