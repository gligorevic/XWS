//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.11 at 01:32:51 PM CEST 
//


package com.baeldung.springsoap.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for car complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="car">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gearShiftName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fuelTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bodyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kmPassed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="locationToken" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mainImageUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mapImages" type="{http://www.baeldung.com/springsoap/gen}map" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "car", propOrder = {
    "brandName",
    "modelName",
    "gearShiftName",
    "fuelTypeName",
    "bodyName",
    "kmPassed",
    "locationToken",
    "userEmail",
    "mainImageUrl",
    "mapImages"
})
public class Car {

    @XmlElement(required = true)
    protected String brandName;
    @XmlElement(required = true)
    protected String modelName;
    @XmlElement(required = true)
    protected String gearShiftName;
    @XmlElement(required = true)
    protected String fuelTypeName;
    @XmlElement(required = true)
    protected String bodyName;
    protected int kmPassed;
    @XmlElement(required = true)
    protected String locationToken;
    @XmlElement(required = true)
    protected String userEmail;
    @XmlElement(required = true)
    protected String mainImageUrl;
    protected List<Map> mapImages;

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
     * Gets the value of the gearShiftName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGearShiftName() {
        return gearShiftName;
    }

    /**
     * Sets the value of the gearShiftName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGearShiftName(String value) {
        this.gearShiftName = value;
    }

    /**
     * Gets the value of the fuelTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuelTypeName() {
        return fuelTypeName;
    }

    /**
     * Sets the value of the fuelTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFuelTypeName(String value) {
        this.fuelTypeName = value;
    }

    /**
     * Gets the value of the bodyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBodyName() {
        return bodyName;
    }

    /**
     * Sets the value of the bodyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBodyName(String value) {
        this.bodyName = value;
    }

    /**
     * Gets the value of the kmPassed property.
     * 
     */
    public int getKmPassed() {
        return kmPassed;
    }

    /**
     * Sets the value of the kmPassed property.
     * 
     */
    public void setKmPassed(int value) {
        this.kmPassed = value;
    }

    /**
     * Gets the value of the locationToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationToken() {
        return locationToken;
    }

    /**
     * Sets the value of the locationToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationToken(String value) {
        this.locationToken = value;
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
     * Gets the value of the mainImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainImageUrl() {
        return mainImageUrl;
    }

    /**
     * Sets the value of the mainImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainImageUrl(String value) {
        this.mainImageUrl = value;
    }

    /**
     * Gets the value of the mapImages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mapImages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Map }
     * 
     * 
     */
    public List<Map> getMapImages() {
        if (mapImages == null) {
            mapImages = new ArrayList<Map>();
        }
        return this.mapImages;
    }

}
