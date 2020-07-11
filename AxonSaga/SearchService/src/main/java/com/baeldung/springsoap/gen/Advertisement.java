//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.11 at 01:49:06 PM CEST 
//


package com.baeldung.springsoap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for advertisement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="advertisement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="kmRestriction" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="priceFrom" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="priceTo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="gearShiftName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fuelTypeName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="bodyName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kmPassed" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberChildSeats" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="collisionDamage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="userEmail" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mainImagePath" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rentingCityLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rentingStreetLocation" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="freeFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="freeTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "advertisement", propOrder = {
    "carId",
    "kmRestriction",
    "priceFrom",
    "priceTo",
    "brandName",
    "modelName",
    "gearShiftName",
    "fuelTypeName",
    "bodyName",
    "kmPassed",
    "numberChildSeats",
    "collisionDamage",
    "userEmail",
    "mainImagePath",
    "rentingCityLocation",
    "rentingStreetLocation",
    "freeFrom",
    "freeTo"
})
public class Advertisement {

    protected long carId;
    protected int kmRestriction;
    protected int priceFrom;
    protected int priceTo;
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
    protected int numberChildSeats;
    protected boolean collisionDamage;
    @XmlElement(required = true)
    protected String userEmail;
    @XmlElement(required = true)
    protected String mainImagePath;
    @XmlElement(required = true)
    protected String rentingCityLocation;
    @XmlElement(required = true)
    protected String rentingStreetLocation;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar freeFrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar freeTo;

    /**
     * Gets the value of the carId property.
     * 
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets the value of the carId property.
     * 
     */
    public void setCarId(long value) {
        this.carId = value;
    }

    /**
     * Gets the value of the kmRestriction property.
     * 
     */
    public int getKmRestriction() {
        return kmRestriction;
    }

    /**
     * Sets the value of the kmRestriction property.
     * 
     */
    public void setKmRestriction(int value) {
        this.kmRestriction = value;
    }

    /**
     * Gets the value of the priceFrom property.
     * 
     */
    public int getPriceFrom() {
        return priceFrom;
    }

    /**
     * Sets the value of the priceFrom property.
     * 
     */
    public void setPriceFrom(int value) {
        this.priceFrom = value;
    }

    /**
     * Gets the value of the priceTo property.
     * 
     */
    public int getPriceTo() {
        return priceTo;
    }

    /**
     * Sets the value of the priceTo property.
     * 
     */
    public void setPriceTo(int value) {
        this.priceTo = value;
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
     * Gets the value of the numberChildSeats property.
     * 
     */
    public int getNumberChildSeats() {
        return numberChildSeats;
    }

    /**
     * Sets the value of the numberChildSeats property.
     * 
     */
    public void setNumberChildSeats(int value) {
        this.numberChildSeats = value;
    }

    /**
     * Gets the value of the collisionDamage property.
     * 
     */
    public boolean isCollisionDamage() {
        return collisionDamage;
    }

    /**
     * Sets the value of the collisionDamage property.
     * 
     */
    public void setCollisionDamage(boolean value) {
        this.collisionDamage = value;
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
     * Gets the value of the mainImagePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMainImagePath() {
        return mainImagePath;
    }

    /**
     * Sets the value of the mainImagePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMainImagePath(String value) {
        this.mainImagePath = value;
    }

    /**
     * Gets the value of the rentingCityLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRentingCityLocation() {
        return rentingCityLocation;
    }

    /**
     * Sets the value of the rentingCityLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRentingCityLocation(String value) {
        this.rentingCityLocation = value;
    }

    /**
     * Gets the value of the rentingStreetLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRentingStreetLocation() {
        return rentingStreetLocation;
    }

    /**
     * Sets the value of the rentingStreetLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRentingStreetLocation(String value) {
        this.rentingStreetLocation = value;
    }

    /**
     * Gets the value of the freeFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFreeFrom() {
        return freeFrom;
    }

    /**
     * Sets the value of the freeFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFreeFrom(XMLGregorianCalendar value) {
        this.freeFrom = value;
    }

    /**
     * Gets the value of the freeTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFreeTo() {
        return freeTo;
    }

    /**
     * Sets the value of the freeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFreeTo(XMLGregorianCalendar value) {
        this.freeTo = value;
    }

}
