// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.cvut.fit.mi_mpr_dip.admission.domain.personal;

import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatus;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatusDataOnDemand;
import cz.cvut.fit.mi_mpr_dip.admission.domain.personal.MaritalStatusIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect MaritalStatusIntegrationTest_Roo_IntegrationTest {
    
    declare @type: MaritalStatusIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: MaritalStatusIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: MaritalStatusIntegrationTest: @Transactional;
    
    @Autowired
    private MaritalStatusDataOnDemand MaritalStatusIntegrationTest.dod;
    
    @Test
    public void MaritalStatusIntegrationTest.testCountMaritalStatuses() {
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", dod.getRandomMaritalStatus());
        long count = MaritalStatus.countMaritalStatuses();
        Assert.assertTrue("Counter for 'MaritalStatus' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testFindMaritalStatus() {
        MaritalStatus obj = dod.getRandomMaritalStatus();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to provide an identifier", id);
        obj = MaritalStatus.findMaritalStatus(id);
        Assert.assertNotNull("Find method for 'MaritalStatus' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'MaritalStatus' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testFindAllMaritalStatuses() {
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", dod.getRandomMaritalStatus());
        long count = MaritalStatus.countMaritalStatuses();
        Assert.assertTrue("Too expensive to perform a find all test for 'MaritalStatus', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<MaritalStatus> result = MaritalStatus.findAllMaritalStatuses();
        Assert.assertNotNull("Find all method for 'MaritalStatus' illegally returned null", result);
        Assert.assertTrue("Find all method for 'MaritalStatus' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testFindMaritalStatusEntries() {
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", dod.getRandomMaritalStatus());
        long count = MaritalStatus.countMaritalStatuses();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<MaritalStatus> result = MaritalStatus.findMaritalStatusEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'MaritalStatus' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'MaritalStatus' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testFlush() {
        MaritalStatus obj = dod.getRandomMaritalStatus();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to provide an identifier", id);
        obj = MaritalStatus.findMaritalStatus(id);
        Assert.assertNotNull("Find method for 'MaritalStatus' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyMaritalStatus(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'MaritalStatus' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testMergeUpdate() {
        MaritalStatus obj = dod.getRandomMaritalStatus();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to provide an identifier", id);
        obj = MaritalStatus.findMaritalStatus(id);
        boolean modified =  dod.modifyMaritalStatus(obj);
        Integer currentVersion = obj.getVersion();
        MaritalStatus merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'MaritalStatus' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", dod.getRandomMaritalStatus());
        MaritalStatus obj = dod.getNewTransientMaritalStatus(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'MaritalStatus' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'MaritalStatus' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void MaritalStatusIntegrationTest.testRemove() {
        MaritalStatus obj = dod.getRandomMaritalStatus();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'MaritalStatus' failed to provide an identifier", id);
        obj = MaritalStatus.findMaritalStatus(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'MaritalStatus' with identifier '" + id + "'", MaritalStatus.findMaritalStatus(id));
    }
    
}