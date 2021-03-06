package org.academiadecodigo.hackatonfundao.faustinder.services;

import com.sun.corba.se.impl.protocol.INSServerRequestDispatcher;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {

    private static ServiceRegistry instance;
    private Map<String, Service> services;

    private ServiceRegistry() {
        services = new HashMap<>();
    }

    public static ServiceRegistry getInstance() {

        if (instance == null) {
            instance = new ServiceRegistry();
        }

        return instance;
    }

    public void add(String name, Service service) {
        services.put(name, service);
    }

    public Service get(String name) {
        return services.get(name);
    }
}
