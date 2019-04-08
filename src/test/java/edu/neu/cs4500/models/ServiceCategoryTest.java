package edu.neu.cs4500.models;

import static org.junit.jupiter.api.Assertions.*;

import edu.neu.cs4500.models.Service;
import edu.neu.cs4500.models.ServiceCategory;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

class ServiceCategoryTest {

    ServiceCategory serviceCategory1;
    ServiceCategory serviceCategory2;

    Service service1;
    Service service2;

    @BeforeEach
    void create() {
        this.service1 = new Service();
        this.service1.setId(1);
        this.service1.setServiceName("Appliances");

        this.service2 = new Service();
        this.service2.setId(1);
        this.service2.setServiceName("Gardening");

        this.serviceCategory1 = new ServiceCategory();
        this.serviceCategory1.setId(1);
        this.serviceCategory1.setServiceCategoryName("Home");
        this.serviceCategory1.setServices(new ArrayList<Service>());

        this.serviceCategory2 = new ServiceCategory();
        this.serviceCategory2.setId(2);
        this.serviceCategory2.setServiceCategoryName("Outdoor");
        ArrayList<Service> lst = new ArrayList<Service>();
        lst.add(this.service1);
        this.serviceCategory2.setServices(lst);
    }

    @Test
    void testAddingServiceToNoServices() {
        assert (this.serviceCategory1.getServices().isEmpty());
        this.serviceCategory1.addServiceToCategory(service1);
        assertFalse(this.serviceCategory1.getServices().isEmpty());
    }

    @Test
    void testAddingServiceToExistingServices() {
        assertFalse(this.serviceCategory2.getServices().isEmpty());
        this.serviceCategory1.addServiceToCategory(service2);
        assertEquals(2, this.serviceCategory1.getServices().size());
    }

}
