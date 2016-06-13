package com.wpkk.stepicJavaServer06.ResourceService;

import resources.TestResource;
import com.wpkk.stepicJavaServer06.sax.ReadXMLFileSAX;


public class ResourceServer {

    private TestResource resource;

    public ResourceServer(TestResource resource) {
        this.resource = resource;
    }

    public void setResourceFromXML(String path) {
        resource = (TestResource)ReadXMLFileSAX.readXML(path);
    }

    public int getAge() {
        return resource.getAge();
    }

    public String getName() {
        return resource.getName();
    }



}
