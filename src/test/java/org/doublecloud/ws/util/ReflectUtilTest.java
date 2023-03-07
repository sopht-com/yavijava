package org.doublecloud.ws.util;

import com.vmware.vim25.PropertyChange;
import org.doublecloud.ws.util.testUtils.ReflectUtilExtendsAbs1With2Fields;
import org.doublecloud.ws.util.testUtils.ReflectUtilExtendsSuper4Fields;
import org.doublecloud.ws.util.testUtils.ReflectUtilImplementsSimpleWith4Fields;
import org.doublecloud.ws.util.testUtils.ReflectUtilSingleObjWith2Fields1Method;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ReflectUtilTest {

    @Test
    public void test_GetAllFields_Returns_All_Fields_From_Single_Class() {
        Field[] fields = ReflectUtil.getAllFields(ReflectUtilSingleObjWith2Fields1Method.class);
        assertEquals(2, fields.length);
    }

    @Test
    public void test_GetAllFields_Returns_All_Fields_With_Superclass() {
        Field[] fields = ReflectUtil.getAllFields(ReflectUtilExtendsSuper4Fields.class);
        assertEquals(4, fields.length);
    }

    @Test
    public void test_GetAllFields_Returns_All_Fields_With_AbstractClass() {
        Field[] fields = ReflectUtil.getAllFields(ReflectUtilExtendsAbs1With2Fields.class);
        assertEquals(2, fields.length);
    }

    @Test
    public void test_GetAllFields_Returns_All_Fields_With_Interface() {
        Field[] fields = ReflectUtil.getAllFields(ReflectUtilImplementsSimpleWith4Fields.class);
        assertEquals(4, fields.length);
    }

    @Test
    public void testToByteArray() {
        List<String> values = new ArrayList<>();
        char[] chars = "ox991LwhCGLf2gntXqKkSPdqC+A=".toCharArray();
        for (char c : chars) {
            values.add(Character.toString(c));
        }
        byte[] actual = ReflectUtil.toByteArray(values);
        byte[] expected = javax.xml.bind.DatatypeConverter.parseBase64Binary("ox991LwhCGLf2gntXqKkSPdqC+A=");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testReflectUtil_ParseToObject_Returns_String_Array() {
        String type = "String[]";
        List<String> strings = new ArrayList<>();
        strings.add("string1");
        strings.add("string2");
        strings.add("string3");
        String[] stringArray = (String[]) ReflectUtil.parseToObject(type, strings);
        assert stringArray.getClass().isArray();
    }

    @Test
    public void testReflectUtil_SetObjectField_Supports_Base64Binary_To_ByteArray() throws Exception {
        String base64BinaryStr = "EtUGWJdr2BYg3Dom7G6oPAlHHcc=";
        PropertyChange pc = new PropertyChange();
        Field field = PropertyChange.class.getField("val");
        String type = "base64Binary";
        ReflectUtil.setObjectField(pc, field, type, base64BinaryStr);
        assert pc.getVal() instanceof byte[];
    }
}
