package srg.resources;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class ResourceContainerTest {

    private ResourceContainer resourceRepair;
    private ResourceContainer resourceRepairTwo;

    @Before
    public void setup() {
        resourceRepair = new ResourceContainer(ResourceType.REPAIR_KIT, 10);
        resourceRepairTwo = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    }

    @Test
    public void newResourceConstructor() {
        ResourceContainer newResource = new ResourceContainer(ResourceType.REPAIR_KIT, 3);
        assertEquals(newResource.getAmount(), 3);
        assertEquals(newResource.getType(), ResourceType.REPAIR_KIT);
        assertEquals(newResource.getShortName(), "REPAIR_KIT");
    }

    @Test
    public void resourceCanStoreFuel() {
        assertEquals(resourceRepair.canStore(ResourceType.FUEL), false);
    }

    @Test
    public void resourceCanStoreRepair() {
        assertEquals(resourceRepair.canStore(ResourceType.REPAIR_KIT), true);
    }

    @Test
    public void resourceRepairGetAmount() {
        assertEquals(resourceRepair.getAmount(), 10);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorException() {
        new ResourceContainer(ResourceType.FUEL, 1);
    }

    @Test
    public void resourceRepairToString() {
        assertEquals(resourceRepair.toString(), "REPAIR_KIT: 10");
    }

    @Test
    public void resourceRepairTwoToString() {
        assertEquals(resourceRepairTwo.toString(), "REPAIR_KIT: 5");
    }

    @Test
    public void resourceRepairSetAmount() {
        resourceRepair.setAmount(5);
        assertEquals(resourceRepair.getAmount(), 5);
    }

    @Test
    public void resourceRepairSetAmountMultiple() {
        resourceRepair.setAmount(5);
        assertEquals(resourceRepair.getAmount(), 5);
        resourceRepairTwo.setAmount(5);
        assertEquals(resourceRepairTwo.getAmount(), 5);
        resourceRepair.setAmount(7);
        assertEquals(resourceRepair.getAmount(), 7);
    }

    @Test
    public void resourceRepairGetType() {
        assertEquals(resourceRepair.getType(), ResourceType.REPAIR_KIT);
    }

    @Test
    public void resourceRepairGetShortName() {
        assertEquals(resourceRepair.getShortName(), "REPAIR_KIT");
    }

}
