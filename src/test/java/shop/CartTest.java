package shop;


import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import parser.JsonParser;
import parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class CartTest {

    private List<RealItem> realItems;
    private Cart cart3;
    private RealItem testt;
    private VirtualItem testt2;

    @BeforeSuite
    public void init() {
        cart3 = new Cart("test-cart");
        testt = new RealItem();
        testt2 = new VirtualItem();

        testt2.setSizeOnDisk(200);
        testt2.setPrice(30);

        testt.setName("MyCart");
        testt.setPrice(50);
        testt.setWeight(30.5);
        cart3.addRealItem(testt);
        cart3.addVirtualItem(testt2);
    }

    @Test
    public void testGetCartPrice() {

        Assert.assertEquals(96.0, cart3.getTotalPrice());
    }

    @Test
    public void deleteFromCart() throws FileNotFoundException {
        Parser parser = new JsonParser();
        parser.writeToFile(cart3);
        String a = "{\"cartName\":\"test-cart\",\"realItems\":[{\"weight\":30.5,\"name\":\"MyCart\",\"price\":50.0}]," +
                "\"virtualItems\":[{\"sizeOnDisk\":200.0,\"price\":30.0}],\"total\":96.0}";

        File myFile = new File("src/main/resources/test-cart.json");
        Scanner myReader = new Scanner(myFile);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            Assert.assertEquals(a, data);


            cart3.deleteRealItem(testt);
            cart3.deleteVirtualItem(testt2);

            Parser parser1 = new JsonParser();
            parser1.writeToFile(cart3);
            String b = "{\"cartName\":\"test-cart\",\"realItems\":[],\"virtualItems\":[],\"total\":0.0}";

            File myFile1 = new File("src/main/resources/test-cart.json");
            Scanner myReader1 = new Scanner(myFile1);
            while (myReader1.hasNextLine()) {
                String data1 = myReader1.nextLine();

                Assert.assertEquals(b, data1);
                        Assert.assertEquals(0.0, cart3.getTotalPrice());


            }
        }
    }
}