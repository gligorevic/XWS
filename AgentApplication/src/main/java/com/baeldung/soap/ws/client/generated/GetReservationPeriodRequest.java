
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
 *         &lt;element name="reservationPeriod" type="{http://www.baeldung.com/springsoap/gen}reservationPeriod"/&gt;
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
    "reservationPeriod"
})
@XmlRootElement(name = "getReservationPeriodRequest")
public class GetReservationPeriodRequest {

    @XmlElement(required = true)
    protected ReservationPeriod reservationPeriod;

    /**
     * Gets the value of the reservationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link ReservationPeriod }
     *     
     */
    public ReservationPeriod getReservationPeriod() {
        return reservationPeriod;
    }

    /**
     * Sets the value of the reservationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReservationPeriod }
     *     
     */
    public void setReservationPeriod(ReservationPeriod value) {
        this.reservationPeriod = value;
    }

}
