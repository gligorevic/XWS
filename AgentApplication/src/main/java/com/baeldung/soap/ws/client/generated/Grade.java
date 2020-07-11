
package com.baeldung.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grade complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="grade"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="grade" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="requestId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="inBundle" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="agentUsername" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "grade", propOrder = {
    "grade",
    "requestId",
    "inBundle",
    "username",
    "agentUsername"
})
public class Grade {

    protected int grade;
    protected long requestId;
    protected boolean inBundle;
    @XmlElement(required = true)
    protected String username;
    @XmlElement(required = true)
    protected String agentUsername;

    public Grade(com.example.AgentApplication.domain.Grade grade){
        this.agentUsername = "agent@gmail.com";
        this.grade = grade.getGrade();
        this.username = grade.getUser().getUsername();
        this.inBundle = grade.isInBundle();
        this.requestId = grade.getRequest().getRemoteId();
    }

    public Grade(){

    }
    /**
     * Gets the value of the grade property.
     * 
     */
    public int getGrade() {
        return grade;
    }

    /**
     * Sets the value of the grade property.
     * 
     */
    public void setGrade(int value) {
        this.grade = value;
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

}
