package by.teachmeskills.sharelane;

import java.net.MalformedURLException;
import java.net.URL;

public class test {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println(new URL("http", "localhost", 4444, "/").toString());
    }
}
