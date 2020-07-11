
package com.baeldung.soap.ws.client.generated;

import com.example.AgentApplication.domain.RequestContainer;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for container complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="container"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userSentRequest" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="boundleList" type="{http://www.baeldung.com/springsoap/gen}request" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "container", propOrder = {
    "userEmail",
    "userSentRequest",
    "boundleList"
})
public class Container {

    @XmlElement(required = true)
    protected String userEmail;
    @XmlElement(required = true)
    protected String userSentRequest;
    @XmlElement(required = true)
    protected List<Request> boundleList;

    public Container(RequestContainer requestContainer){
        this.userEmail = requestContainer.getUserEmail();
        this.userSentRequest = requestContainer.getUserSentRequest();
    }

    public Container(){

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
     * Gets the value of the boundleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the boundleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBoundleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Request }
     * 
     * 
     */
    public List<Request> getBoundleList() {
        if (boundleList == null) {
            boundleList = new ArrayList<Request>();
        }
        return this.boundleList;
    }

}
