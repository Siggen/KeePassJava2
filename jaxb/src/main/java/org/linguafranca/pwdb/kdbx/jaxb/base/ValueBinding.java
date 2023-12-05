package org.linguafranca.pwdb.kdbx.jaxb.base;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlValue;

/**
 * Ancestor class of StringField.Value, providing a way of flagging protection
 * separate from @Protected and @ProtectInMemory
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class ValueBinding {

    @XmlTransient
    public boolean protectOnOutput;

    @XmlValue
    public String value;

    public String getValue(){
        return value;
    }

    public void setValue(String string){
        value = string;
    }

}
