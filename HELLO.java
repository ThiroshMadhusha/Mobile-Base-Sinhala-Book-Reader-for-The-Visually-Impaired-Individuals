package com.mashcode.mtit2022pastpaper.answers.question02.main;

import com.mashcode.mtit2022pastpaper.answers.question02.common.CommonConstant;

import java.io.File;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * Authored By MASH
 * 2022/08/27
 * this is sample answer for 2022 mtit past paper Q2
 */
public class Question02 {


    /**
     * @param args
     */
    public static void main(String[] args) {
        buildXmlFile();
    }

    /**
     * build xml file
     */
    public static void buildXmlFile() {
        Element school, students, student, name, address;
        Attr gender, initials, no, street;

        //call build document function
        Document document = buildDocument();

        //create element school
        school = createElement(document, CommonConstant.XML_ELEMENT_SCHOOL);
        document.appendChild(school);

        //create element students
        students = createElement(document, CommonConstant.XML_ELEMENT_STUDENTS);
        school.appendChild(students);

        //create element student
        student = createElement(document, CommonConstant.XML_ELEMENT_STUDENT);
        students.appendChild(student);


        //create attribute gender and set value
        gender = createAttribute(document, CommonConstant.XML_ATTRIBUTE_GENDER, CommonConstant.XML_DOCUMENT_GENDER_VALUE);
        student.setAttributeNode(gender);
        //create initials gender and set value
        initials = createAttribute(document, CommonConstant.XML_ATTRIBUTE_INITIALS, CommonConstant.XML_DOCUMENT_INITIALS_VALUE);

        //create element name
        name = createElement(document, CommonConstant.XML_ELEMENT_NAME);
        name.setAttributeNode(initials);
        //set value to name
        name.appendChild(document.createTextNode(CommonConstant.XML_DOCUMENT_NAME_VALUE));
        student.appendChild(name);

        //create element address and set attributes and values
        address = createElement(document, CommonConstant.XML_ELEMENT_ADDRESS);
        no = createAttribute(document, CommonConstant.XML_ATTRIBUTE_NO, CommonConstant.XML_DOCUMENT_NO_VALUE);
        street = createAttribute(document, CommonConstant.XML_ATTRIBUTE_STREET, CommonConstant.XML_DOCUMENT_STREET_VALUE);

        address.setAttributeNode(no);
        address.setAttributeNode(street);
        student.appendChild(setAttributeForElement(document, address, CommonConstant.XML_DOCUMENT_ADDRESS_VALUE));

        //xml generate method call
        transformToXml(document);
    }

    /**
     * build document
     *
     * @return
     */
    public static Document buildDocument() {
        Logger log = Logger.getLogger(Question02.class.getName());
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException exception) {
            log.info("Error getting generate Document ".concat(exception.getMessage()));
            throw new RuntimeException("Error getting generate Document ".concat(exception.getMessage()));
        }
    }

    /**
     * @param document
     * @param element
     * @param textNode
     * @return
     */
    public static Element setAttributeForElement(Document document, Element element, String textNode) {
        element.appendChild(document.createTextNode(textNode));
        return element;
    }


    /**
     * @param document
     * @param type
     * @param value
     * @return
     */
    public static Attr createAttribute(Document document, String type, String value) {
        Attr attribute = document.createAttribute(type);
        attribute.setValue(value);
        return attribute;
    }

    /**
     * @param document
     * @param elementName
     * @return
     */
    public static Element createElement(Document document, String elementName) {
        return document.createElement(elementName);
    }

    /**
     * @param document
     */
    public static void transformToXml(Document document) {

        Logger log = Logger.getLogger(Question02.class.getName());
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource domSource = new DOMSource(document);
            transformer.transform(domSource, new StreamResult(new File(CommonConstant.XML_DOCUMENT_FILE_NAME)));
            transformer.transform(domSource, new StreamResult(System.out));
            log.info("Xml build successfully ");

        } catch (TransformerException exception) {
            log.info("Error getting generate Xml document  ".concat(exception.getMessage()));
            throw new RuntimeException("Error getting generate Xml document ".concat(exception.getMessage()));
        }
    }
}
---------------------------------------------------------------------------

package com.mashcode.mtit2022pastpaper.answers.question02.common;

/**
 * Authored By MASH
 * 2022/08/27
 * this is sample answer for 2022 mtit past paper Q2
 */
public class CommonConstant {

    //add some comments below attributes
    public static final String XML_ELEMENT_SCHOOL = "School";
    public static final String XML_ELEMENT_STUDENT = "Student";
    public static final String XML_ELEMENT_STUDENTS = "Students";
    public static final String XML_ELEMENT_NAME = "Name";
    public static final String XML_ELEMENT_ADDRESS = "Address";
    public static final String XML_ATTRIBUTE_INITIALS = "Initials";
    public static final String XML_ATTRIBUTE_GENDER = "Gender";
    public static final String XML_ATTRIBUTE_STREET = "Street";
    public static final String XML_ATTRIBUTE_NO = "No";
    public static final String XML_DOCUMENT_FILE_NAME = "students.xml";
    public static final String XML_DOCUMENT_GENDER_VALUE = "Male";
    public static final String XML_DOCUMENT_NAME_VALUE = "Nalaka Dissanayake";
    public static final String XML_DOCUMENT_NO_VALUE = "115/1";
    public static final String XML_DOCUMENT_STREET_VALUE = "Avenue Street";
    public static final String XML_DOCUMENT_ADDRESS_VALUE = "RID: 115/1j Avenue Street, Randy";
    public static final String XML_DOCUMENT_INITIALS_VALUE = "S.A";

}
---------------------------------------------------------------------------------------
//2022Q1A
import java.util.ArrayList;

 

public class ThreadTest {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

 

        Runnable producer = () -> {
            synchronized (list) {
                int value = 0;
                while (true) {
                    System.out.println("Producer started");
                    try {
                        value += 10;
                        list.add(value);
                        System.out.println("Producer adding value " + value + " to List.");
                        list.notify();
                        list.wait();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

 

        Runnable consumer = () -> {
            synchronized (list) {
                while (true) {
                    System.out.println("Consumer started");
                    try {
                        if (list.isEmpty()) {
                            list.notify();
                            list.wait();
                        } else {
                            int value = list.remove(0);
                            System.out.println("Consumer thred value " + value + " to List.");
                            System.out.println("Elements in List = []");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

 

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

 

        producerThread.start();
        consumerThread.start();
    }
}



-------------------------------------------------------

//2022Q1B

import java.util.Arrays;

 

public class ThirdType {
    public static void main(String[] args) {
        String[] stringArray = {"sSS", "rrR", "aaa", "UUU", "Jjj", "PpP", "Yyy"};

        Arrays.sort(stringArray, String::compareToIgnoreCase);

        for (String value : stringArray) {
            System.out.println(value);
        }
    }
}

-------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;

interface ITrafficService {
    String checkSpeed(List<Double> listOfCheckPoints);
}

public class VehicleMonitor implements ITrafficService {
    @Override
    public String checkSpeed(List<Double> listOfCheckPoints) {
        double total = listOfCheckPoints.stream()
                .reduce(0.0, (sum, speed) -> sum + speed);

        double averageSpeed = total / listOfCheckPoints.size();

        if (averageSpeed >= 100.0) {
            return "Issue Fine";
        } else if (averageSpeed >= 80.0) {
            return "Warning message";
        } else if (averageSpeed >= 50.0) {
            return "Good speed";
        } else if (averageSpeed >= 30.0) {
            return "Normal";
        } else {
            return "Slow";
        }
    }

    public static void main(String[] args) {
        VehicleMonitor vehicleMonitor = new VehicleMonitor();
        ArrayList<Double> speedinCheckPoint = new ArrayList<>();
        speedinCheckPoint.add(20.0);
        speedinCheckPoint.add(30.0);
        speedinCheckPoint.add(60.0);
        speedinCheckPoint.add(80.0);
        speedinCheckPoint.add(100.0);
        speedinCheckPoint.add(120.0);
        String result = vehicleMonitor.checkSpeed(speedinCheckPoint);
        System.out.println("Vehicle average status is = " + result);
    }
}
---------------------------------------------------------------------
public class Q1b {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int row = 1; row <= 5; row++) {
                for (int column = 1; column <= 5; column++) {
                    System.out.print(column);
                }
                System.out.println();
            }
        });

        thread.start();
    }
}
-------------------------------------------------
import java.util.ArrayList;
import java.util.List;

interface IVehicle {
    void displayVehicles();
}

public class Qla {
    public static void main(String[] args) {
        IVehicle iVehicle = Qla::displayVehicles;

        iVehicle.displayVehicles();
    }

    public static void displayVehicles() {
        List<String> vehicles = new ArrayList<>();
        vehicles.add("Car");
        vehicles.add("Bus");
        vehicles.add("Van");
        vehicles.add("Jeep");
        vehicles.add("Lorry");
        vehicles.forEach(System.out::println);
    }
}
----------------------------------------------------------