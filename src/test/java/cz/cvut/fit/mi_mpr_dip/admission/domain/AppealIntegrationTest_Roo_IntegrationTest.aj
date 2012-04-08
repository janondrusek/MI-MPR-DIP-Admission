// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.cvut.fit.mi_mpr_dip.admission.domain;

import cz.cvut.fit.mi_mpr_dip.admission.domain.Appeal;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AppealDataOnDemand;
import cz.cvut.fit.mi_mpr_dip.admission.domain.AppealIntegrationTest;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AppealIntegrationTest_Roo_IntegrationTest {
    
    declare @type: AppealIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: AppealIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml");
    
    declare @type: AppealIntegrationTest: @Transactional;
    
    @Autowired
    private AppealDataOnDemand AppealIntegrationTest.dod;
    
    @Test
    public void AppealIntegrationTest.testCountAppeals() {
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", dod.getRandomAppeal());
        long count = Appeal.countAppeals();
        Assert.assertTrue("Counter for 'Appeal' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void AppealIntegrationTest.testFindAppeal() {
        Appeal obj = dod.getRandomAppeal();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", obj);
        Long id = obj.getAppealId();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to provide an identifier", id);
        obj = Appeal.findAppeal(id);
        Assert.assertNotNull("Find method for 'Appeal' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Appeal' returned the incorrect identifier", id, obj.getAppealId());
    }
    
    @Test
    public void AppealIntegrationTest.testFindAllAppeals() {
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", dod.getRandomAppeal());
        long count = Appeal.countAppeals();
        Assert.assertTrue("Too expensive to perform a find all test for 'Appeal', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Appeal> result = Appeal.findAllAppeals();
        Assert.assertNotNull("Find all method for 'Appeal' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Appeal' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void AppealIntegrationTest.testFindAppealEntries() {
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", dod.getRandomAppeal());
        long count = Appeal.countAppeals();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Appeal> result = Appeal.findAppealEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Appeal' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Appeal' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void AppealIntegrationTest.testFlush() {
        Appeal obj = dod.getRandomAppeal();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", obj);
        Long id = obj.getAppealId();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to provide an identifier", id);
        obj = Appeal.findAppeal(id);
        Assert.assertNotNull("Find method for 'Appeal' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyAppeal(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Appeal' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void AppealIntegrationTest.testMergeUpdate() {
        Appeal obj = dod.getRandomAppeal();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", obj);
        Long id = obj.getAppealId();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to provide an identifier", id);
        obj = Appeal.findAppeal(id);
        boolean modified =  dod.modifyAppeal(obj);
        Integer currentVersion = obj.getVersion();
        Appeal merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getAppealId(), id);
        Assert.assertTrue("Version for 'Appeal' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void AppealIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", dod.getRandomAppeal());
        Appeal obj = dod.getNewTransientAppeal(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Appeal' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Appeal' identifier to be null", obj.getAppealId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Appeal' identifier to no longer be null", obj.getAppealId());
    }
    
    @Test
    public void AppealIntegrationTest.testRemove() {
        Appeal obj = dod.getRandomAppeal();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to initialize correctly", obj);
        Long id = obj.getAppealId();
        Assert.assertNotNull("Data on demand for 'Appeal' failed to provide an identifier", id);
        obj = Appeal.findAppeal(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Appeal' with identifier '" + id + "'", Appeal.findAppeal(id));
    }
    
}
