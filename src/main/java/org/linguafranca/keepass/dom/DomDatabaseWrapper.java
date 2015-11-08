package org.linguafranca.keepass.dom;

import org.linguafranca.keepass.*;
import org.linguafranca.keepass.AbstractDatabase;
import org.linguafranca.keepass.kdbx.KdbxFormatter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.DatatypeConverter;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.linguafranca.keepass.dom.DomHelper.*;

/**
 * @author jo
 */
public class DomDatabaseWrapper extends AbstractDatabase {

    Document document;
    Element dbRootGroup;
    Element dbMeta;

    DomKeepassDatabase domDatabase = DomKeepassDatabase.createEmptyDatabase();


    public DomDatabaseWrapper () throws IOException {
        init();
    }

    public DomDatabaseWrapper (Formatter formatter, Credentials credentials, InputStream inputStream) throws IOException {
        formatter.load(domDatabase, credentials, inputStream);
        init();
    }

    public static DomDatabaseWrapper load (Credentials credentials, InputStream inputStream) throws IOException {
        return new DomDatabaseWrapper(new KdbxFormatter(), credentials, inputStream);
    }

    private void init() {
        document = domDatabase.getDoc();
        try {
            dbRootGroup = ((Element) DomHelper.xpath.evaluate("/KeePassFile/Root/Group", document, XPathConstants.NODE));
            dbMeta = ((Element) DomHelper.xpath.evaluate("/KeePassFile/Meta", document, XPathConstants.NODE));
        } catch (XPathExpressionException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(Credentials credentials, OutputStream outputStream) throws IOException {
        new KdbxFormatter().save(domDatabase, credentials, outputStream);
    }

    public void save(Formatter formatter, Credentials credentials, OutputStream outputStream) throws IOException {
        formatter.save(domDatabase, credentials, outputStream);
    }

    public boolean shouldProtect(String name) {
        Element protectionElement = getElement("MemoryProtection/Protect" + name, dbMeta, false);
        if (protectionElement == null) {
            return false;
        }
        return Boolean.valueOf(protectionElement.getTextContent());
    }

    @Override
    public Group getRootGroup() {
        return new DomGroupWrapper(dbRootGroup, this);
    }

    @Override
    public Group newGroup() {
        return new DomGroupWrapper(document.createElement(GROUP_ELEMENT_NAME), this);
    }

    @Override
    public Entry newEntry() {
        return new DomEntryWrapper(document.createElement(ENTRY_ELEMENT_NAME), this);
    }

    @Override
    public Icon newIcon() {
        return new DomIconWrapper(document.createElement(ICON_ELEMENT_NAME));
    }

    @Override
    public Icon newIcon(Integer i) {
        Icon icon =  newIcon();
        icon.setIndex(i);
        return icon;
    }

    public String getName() {
        return getElementContent("DatabaseName", dbMeta);
    }

    public void setName(String name) {
        setElementContent("DatabaseName", dbMeta, name);
        touchElement("DatabaseNameChanged", dbMeta);
    }

    @Override
    public String getDescription() {
        return getElementContent("DatabaseDescription", dbMeta);
    }

    @Override
    public void setDescription(String description) {
        setElementContent("DatabaseDescription", dbMeta, description);
        touchElement("DatabaseDescriptionChanged", dbMeta);
    }
}
