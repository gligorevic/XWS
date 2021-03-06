
package com.baeldung.soap.ws.client.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.baeldung.soap.ws.client.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.baeldung.soap.ws.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetPricelistRequest }
     * 
     */
    public GetPricelistRequest createGetPricelistRequest() {
        return new GetPricelistRequest();
    }

    /**
     * Create an instance of {@link Pricelist }
     * 
     */
    public Pricelist createPricelist() {
        return new Pricelist();
    }

    /**
     * Create an instance of {@link GetPricelistResponse }
     * 
     */
    public GetPricelistResponse createGetPricelistResponse() {
        return new GetPricelistResponse();
    }

    /**
     * Create an instance of {@link GetPricelistItemRequest }
     * 
     */
    public GetPricelistItemRequest createGetPricelistItemRequest() {
        return new GetPricelistItemRequest();
    }

    /**
     * Create an instance of {@link PricelistItem }
     * 
     */
    public PricelistItem createPricelistItem() {
        return new PricelistItem();
    }

    /**
     * Create an instance of {@link GetPricelistItemResponse }
     * 
     */
    public GetPricelistItemResponse createGetPricelistItemResponse() {
        return new GetPricelistItemResponse();
    }

    /**
     * Create an instance of {@link GetReportRequest }
     * 
     */
    public GetReportRequest createGetReportRequest() {
        return new GetReportRequest();
    }

    /**
     * Create an instance of {@link Report }
     * 
     */
    public Report createReport() {
        return new Report();
    }

    /**
     * Create an instance of {@link GetReportResponse }
     * 
     */
    public GetReportResponse createGetReportResponse() {
        return new GetReportResponse();
    }

}
