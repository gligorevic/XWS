
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
 * <p>Java class for report complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="report"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="km" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="adId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userEmailRented" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="rentedFrom" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="rentedTo" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "report", propOrder = {
    "text",
    "km",
    "adId",
    "requestId",
    "userEmail",
    "userEmailRented",
    "rentedFrom",
    "rentedTo"
})
public class Report {

    @XmlElement(required = true)
    protected String text;
    protected int km;
    protected long adId;
    protected long requestId;
    @XmlElement(required = true)
    protected String userEmail;
    @XmlElement(required = true)
    protected String userEmailRented;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rentedFrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rentedTo;

    public Report(){

    }

    public Report(com.example.AgentApplication.domain.Report report) throws DatatypeConfigurationException {
        this.adId = report.getRequest().getAdvertisement().getRemoteId();
        this.requestId = report.getRequest().getRemoteId();
        this.km = report.getKm();
        this.text = report.getText();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(report.getRequest().getStartDate());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.rentedFrom = date2;
        c.setTime(report.getRequest().getEndDate());
        date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.rentedTo = date2;
        this.userEmail = "agent@gmail.com";
        this.userEmailRented = report.getRequest().getUserSentRequest().getEmail();
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the km property.
     * 
     */
    public int getKm() {
        return km;
    }

    /**
     * Sets the value of the km property.
     * 
     */
    public void setKm(int value) {
        this.km = value;
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

    /**
     * Gets the value of the requestId property.
     * 
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Sets the value of the requestId property.
     * 
     */
    public void setRequestId(long value) {
        this.requestId = value;
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
     * Gets the value of the userEmailRented property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserEmailRented() {
        return userEmailRented;
    }

    /**
     * Sets the value of the userEmailRented property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserEmailRented(String value) {
        this.userEmailRented = value;
    }

    /**
     * Gets the value of the rentedFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRentedFrom() {
        return rentedFrom;
    }

    /**
     * Sets the value of the rentedFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRentedFrom(XMLGregorianCalendar value) {
        this.rentedFrom = value;
    }

    /**
     * Gets the value of the rentedTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRentedTo() {
        return rentedTo;
    }

    /**
     * Sets the value of the rentedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRentedTo(XMLGregorianCalendar value) {
        this.rentedTo = value;
    }

}
