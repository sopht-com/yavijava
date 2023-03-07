package com.vmware.vim25.ws;

import com.vmware.vim25.InvalidLogin;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.rmi.RemoteException;

public class WSClientTest {

    @Test(expected = InvalidLogin.class)
    public void testUnMarshall_Throws_InvalidLogin_When_Login_is_Invalid() throws RemoteException {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File("src/test/java/com/vmware/vim25/ws/xml/InvalidLoginFault.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XmlGenDom xmlGenDom = new XmlGenDom();
        xmlGenDom.fromXML("Login", inputStream);
    }
}
