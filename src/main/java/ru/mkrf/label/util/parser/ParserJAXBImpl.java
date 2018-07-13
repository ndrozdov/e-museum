package ru.mkrf.label.util.parser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ParserJAXBImpl implements Parser {

    public Object getObject(File file, Class c) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object object = unmarshaller.unmarshal(file);
        return object;
    }

    public void saveObject(File file, Object o) throws JAXBException {

    }
}