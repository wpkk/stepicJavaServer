package com.wpkk.stepicJavaServer06.ResourceService;

public class ResourceServerController implements ResourceServerControllerMBean{

    private ResourceServer resourceServer;

    public ResourceServerController(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;

    }

    public int getAge() {
        return resourceServer.getAge();
    }

    public String getName() {
        return resourceServer.getName();
    }

}
