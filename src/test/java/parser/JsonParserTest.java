package parser;


import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import shop.Cart;
import shop.RealItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JsonParserTest {
    private RealItem real;
    private Cart artsiomCart;
    private Cart eugenCart;
    private Parser parser;
    private List<RealItem> realItems;



    @BeforeSuite(groups = {"checkinTests"})
    public void init() {
        artsiomCart = new Cart("artsiom-cart");
        eugenCart = new Cart("eugen-cart");
        real = new RealItem();
        parser = new JsonParser();
        realItems = new ArrayList<>();

        real.setName("Cart1");
        real.setWeight(20);
        real.setPrice(100.5);
    }

    @Test(groups = {"checkinTests"})
    public void testWriteToFile() throws IOException {
        artsiomCart.addRealItem(real);
        parser.writeToFile(artsiomCart);
        Path path = Paths.get("src/main/resources/artsiom-cart.json");
        Assert.assertTrue(Files.exists(path));

        try {
            File myFile = new File("src/main/resources/artsiom-cart.json");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String sss = "{\"cartName\":\"artsiom-cart\",\"realItems\":[{\"weight\":20.0,\"name\":\"Cart1\",\"price\":100.5}],\"virtualItems\":[],\"total\":120.6}";
                Assert.assertEquals(sss, data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("No such file was founded");
            e.printStackTrace();
        }
    }

    @Test (groups = {"don't need"})
    void isInstantiated() {
        Assert.assertNotNull(artsiomCart);
    }



    @Parameters("path")
    @Test(groups = {"don't need"})
    void testOfExcepion(String path) {
    Exception exception = Assert.assertThrows(NoSuchFileException.class, () -> parser.readFromFile(new File(path))); //I can't understand why this line doesn't  work correctly in idea
    Assert.assertNotNull(exception.getMessage());
}

    //@DataProvider(name = "path")
    //public static Object[] getPath(){
    //static Stream<String> getPath() {
      //  return new String[][]{{"src/main/resources/artsiom-cart"}, {"arsiom-cart.json", "artsiom-cart"}, {"resources/artsiom-cart"}, {"artsion cart"}};
    //}

    @Test(groups = {"checkinTests"})
    void readFromJsonFile()  {
        Cart testCart = parser.readFromFile(new File("src/main/resources/eugen-cart.json"));
        artsiomCart.showItems();
            Assert.assertEquals("eugen-cart", testCart.getCartName());
            Assert.assertEquals(26560.68, testCart.getTotalPrice());

    }
}

