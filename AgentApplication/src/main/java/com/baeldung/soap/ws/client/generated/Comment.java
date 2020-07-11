
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
 * <p>Java class for comment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="comment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="text" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="commentStatus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="agentUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="crationDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="inBundle" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="creationDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "comment", propOrder = {
    "text",
    "commentStatus",
    "username",
    "agentUsername",
    "crationDate",
    "inBundle",
    "creationDate",
    "requestId"
})
public class Comment {

    @XmlElement(required = true)
    protected String text;
    protected int commentStatus;
    @XmlElement(required = true)
    protected String username;
    @XmlElement(required = true)
    protected String agentUsername;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar crationDate;
    protected boolean inBundle;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creationDate;
    protected long requestId;

    public Comment(com.example.AgentApplication.domain.Comment comment) throws DatatypeConfigurationException {
        this.agentUsername = "agent@gmail.com";
        this.username = comment.getUser().getUsername();
        this.commentStatus = 1;
        this.inBundle = comment.isInBundle();
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(comment.getCreationDate());
        XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        this.creationDate = date2;
        this.requestId = comment.getRequest().getRemoteId();
        this.text = comment.getText();
    }

    public Comment(){

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
     * Gets the value of the commentStatus property.
     * 
     */
    public int getCommentStatus() {
        return commentStatus;
    }

    /**
     * Sets the value of the commentStatus property.
     * 
     */
    public void setCommentStatus(int value) {
        this.commentStatus = value;
    }

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the agentUsername property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgentUsername() {
        return agentUsername;
    }

    /**
     * Sets the value of the agentUsername property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgentUsername(String value) {
        this.agentUsername = value;
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
     * Gets the value of the creationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the creationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
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

}
