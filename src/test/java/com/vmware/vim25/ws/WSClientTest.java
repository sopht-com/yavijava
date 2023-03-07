package com.vmware.vim25.ws;

import com.vmware.vim25.InvalidLogin;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class WSClientTest {

    @Test(expected = InvalidLogin.class)
    public void testUnMarshall_Throws_InvalidLogin_When_Login_is_Invalid() throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get("src/test/java/com/vmware/vim25/ws/xml/InvalidLoginFault.xml"))) {
            XmlGenDom xmlGenDom = new XmlGenDom();
            xmlGenDom.fromXML("Login", inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
