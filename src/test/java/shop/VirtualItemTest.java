package shop;


import org.testng.Assert;
import org.testng.annotations.Test;


public class VirtualItemTest {


    @Test
    void creatVirtualItms() {
        VirtualItem virt = new VirtualItem();
        virt.setName("Virt");
        virt.setPrice(100);
        virt.setSizeOnDisk(512);

        String expected = "Class: class shop.VirtualItem; Name: Virt; Price: 100.0; Size on disk: 512.0";
        Assert.assertEquals(expected, virt.toString());
    }
}
