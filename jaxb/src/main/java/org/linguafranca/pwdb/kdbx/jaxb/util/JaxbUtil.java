package org.linguafranca.pwdb.kdbx.jaxb.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.util.JAXBSource;
import javax.xml.namespace.QName;

public class JaxbUtil {

    // https://stackoverflow.com/questions/879453/how-to-make-a-deep-copy-of-jaxb-object-like-xmlbean-xmlobject-copy
    // however it does not copy transients which we need it to, so we don't use it
    public static <T> T deepCopyJAXB(T object, Class<T> clazz) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            JAXBElement<T> contentObject = new JAXBElement<T>(new QName(clazz.getSimpleName()), clazz, object);
            JAXBSource source = new JAXBSource(jaxbContext, contentObject);
            return jaxbContext.createUnmarshaller().unmarshal(source, clazz).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deepCopyJAXB(T object) {
        if(object==null) throw new RuntimeException("Can't guess at class");
        return deepCopyJAXB(object, (Class<T>) object.getClass());
    }
}
