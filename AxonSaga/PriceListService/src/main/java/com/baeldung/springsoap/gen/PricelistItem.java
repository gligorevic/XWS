//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.07.12 at 12:06:12 AM CEST 
//


package com.baeldung.springsoap.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pricelistItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pricelistItem">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pricelistId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="advertisementId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="pricePerDay" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="pricePerKm" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="priceCollisionDamage" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="minNumberDays" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="percentage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pricelistItem", propOrder = {
    "pricelistId",
    "brandName",
    "modelName",
    "advertisementId",
    "pricePerDay",
    "pricePerKm",
    "priceCollisionDamage",
    "minNumberDays",
    "percentage"
})
public class PricelistItem {

    protected long pricelistId;
    @XmlElement(required = true)
    protected String brandName;
    @XmlElement(required = true)
    protected String modelName;
    protected long advertisementId;
    protected float pricePerDay;
    protected float pricePerKm;
    protected float priceCollisionDamage;
    protected int minNumberDays;
    protected int percentage;

    /**
     * Gets the value of the pricelistId property.
     * 
     */
    public long getPricelistId() {
        return pricelistId;
    }

    /**
     * Sets the value of the pricelistId property.
     * 
     */
    public void setPricelistId(long value) {
        this.pricelistId = value;
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
     * Gets the value of the advertisementId property.
     * 
     */
    public long getAdvertisementId() {
        return advertisementId;
    }

    /**
     * Sets the value of the advertisementId property.
     * 
     */
    public void setAdvertisementId(long value) {
        this.advertisementId = value;
    }

    /**
     * Gets the value of the pricePerDay property.
     * 
     */
    public float getPricePerDay() {
        return pricePerDay;
    }

    /**
     * Sets the value of the pricePerDay property.
     * 
     */
    public void setPricePerDay(float value) {
        this.pricePerDay = value;
    }

    /**
     * Gets the value of the pricePerKm property.
     * 
     */
    public float getPricePerKm() {
        return pricePerKm;
    }

    /**
     * Sets the value of the pricePerKm property.
     * 
     */
    public void setPricePerKm(float value) {
        this.pricePerKm = value;
    }

    /**
     * Gets the value of the priceCollisionDamage property.
     * 
     */
    public float getPriceCollisionDamage() {
        return priceCollisionDamage;
    }

    /**
     * Sets the value of the priceCollisionDamage property.
     * 
     */
    public void setPriceCollisionDamage(float value) {
        this.priceCollisionDamage = value;
    }

    /**
     * Gets the value of the minNumberDays property.
     * 
     */
    public int getMinNumberDays() {
        return minNumberDays;
    }

    /**
     * Sets the value of the minNumberDays property.
     * 
     */
    public void setMinNumberDays(int value) {
        this.minNumberDays = value;
    }

    /**
     * Gets the value of the percentage property.
     * 
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the percentage property.
     * 
     */
    public void setPercentage(int value) {
        this.percentage = value;
    }

}
