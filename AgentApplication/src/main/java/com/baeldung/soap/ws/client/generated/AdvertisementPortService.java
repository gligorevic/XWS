
package com.baeldung.soap.ws.client.generated;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AdvertisementPortService", targetNamespace = "http://www.baeldung.com/springsoap/gen", wsdlLocation = "http://localhost:8084/ws/advertisement.wsdl")
public class AdvertisementPortService
    extends Service
{

    private final static URL ADVERTISEMENTPORTSERVICE_WSDL_LOCATION;
    private final static WebServiceException ADVERTISEMENTPORTSERVICE_EXCEPTION;
    private final static QName ADVERTISEMENTPORTSERVICE_QNAME = new QName("http://www.baeldung.com/springsoap/gen", "AdvertisementPortService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8084/ws/advertisement.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        ADVERTISEMENTPORTSERVICE_WSDL_LOCATION = url;
        ADVERTISEMENTPORTSERVICE_EXCEPTION = e;
    }

    public AdvertisementPortService() {
        super(__getWsdlLocation(), ADVERTISEMENTPORTSERVICE_QNAME);
    }

    public AdvertisementPortService(WebServiceFeature... features) {
        super(__getWsdlLocation(), ADVERTISEMENTPORTSERVICE_QNAME, features);
    }

    public AdvertisementPortService(URL wsdlLocation) {
        super(wsdlLocation, ADVERTISEMENTPORTSERVICE_QNAME);
    }

    public AdvertisementPortService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, ADVERTISEMENTPORTSERVICE_QNAME, features);
    }

    public AdvertisementPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AdvertisementPortService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AdvertisementPort
     */
    @WebEndpoint(name = "AdvertisementPortSoap11")
    public AdvertisementPort getAdvertisementPortSoap11() {
        return super.getPort(new QName("http://www.baeldung.com/springsoap/gen", "AdvertisementPortSoap11"), AdvertisementPort.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AdvertisementPort
     */
    @WebEndpoint(name = "AdvertisementPortSoap11")
    public AdvertisementPort getAdvertisementPortSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.baeldung.com/springsoap/gen", "AdvertisementPortSoap11"), AdvertisementPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (ADVERTISEMENTPORTSERVICE_EXCEPTION!= null) {
            throw ADVERTISEMENTPORTSERVICE_EXCEPTION;
        }
        return ADVERTISEMENTPORTSERVICE_WSDL_LOCATION;
    }

}
