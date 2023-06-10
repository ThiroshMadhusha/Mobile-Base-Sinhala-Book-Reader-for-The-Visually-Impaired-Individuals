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


/**
 * Q: How does the Human Visual System work?
A: The Human Visual System works by capturing light through the eyes, which then transmit visual information to the brain for processing. The eyes contain specialized cells called photoreceptors, specifically rods and cones, which detect light and color. The information from the photoreceptors is converted into electrical signals and sent to the brain via the optic nerves. The brain then interprets these signals, allowing us to perceive and understand the visual world.
Q: What are Stereo Vision and Stereoscopy?
A: Stereo Vision refers to the ability of the human visual system to perceive depth and three-dimensional (3D) space using the information from both eyes. Stereoscopy, on the other hand, is a technique used to create the illusion of depth in a two-dimensional image or video by presenting slightly different images to each eye. This technique mimics the natural binocular vision of humans and enhances the perception of depth and realism.
Q: How does Binocular Vision contribute to depth perception?
A: Binocular Vision refers to the process where the eyes work together to produce a single image in the brain. The separation between our eyes, known as the Inter-Pupillary Distance (IPD), allows each eye to receive a slightly different view of the world. This disparity in the images received by the eyes provides depth information to the brain, which then interprets it as a scene with depth and spatial relationships. The brain takes into account factors like convergence (eye muscle movement) and accommodation (focus) to create a sense of depth perception.
Q: What are some depth cues in addition to Stereo Vision?
A: In addition to Stereo Vision, there are other depth cues, known as monocular cues, that help us perceive depth and spatial relationships with just one eye. Some examples of monocular cues include:
1. Occlusion: When a near object blocks the view of a distant object, indicating that the near object is closer.
2. Apparent size: If two objects are the same size but one appears smaller, the smaller one is perceived as farther away.
3. Motion parallax: As we move, nearby objects appear to move faster than distant objects.
4. Perspective: Parallel lines appear to converge in the distance.
5. Texture gradient: The texture of objects becomes finer or less detailed with increasing distance.
6. Color change: Objects tend to appear bluer or desaturated with increasing distance.
7. Haze: Distant objects appear less sharp and hazier.
These monocular cues, along with stereo vision, contribute to our overall perception of depth in the visual environment.
Q: What is Virtual Reality (VR)?
A: Virtual Reality (VR) refers to the use of computer technology to create a simulated environment that can be interacted with and experienced as if it were real. VR typically involves the use of headsets or other devices that provide a visual and auditory immersive experience. It aims to create a sense of presence and immersion by simulating a three-dimensional environment that can be explored and interacted with in a realistic way. VR can be used for various purposes, including entertainment, education, training, and simulations.
Q: How do VR headsets work?
A: VR headsets work by combining various technologies to create an immersive virtual experience. The basic components of a VR headset include:
1. Display: The headset contains high-resolution displays, usually separate screens for each eye, to provide visual content. These displays are placed very close to the user's eyes to cover a wide field of view.
2. Sensors: Built-in sensors, such as accelerometers and gyroscopes, track the movements of the user's head. This allows the system to update the displayed visuals in real-time, giving the user the perception of looking around and exploring the virtual environment.
3. Lenses: Special lenses in the headset help to correct the angle of light coming into the eyes and create a forced perspective, making objects appear farther away than they actually are. These lenses contribute to the illusion of depth and enhance the immersive experience.
4. Audio: Many VR headsets also include built-in headphones or audio systems to provide spatial audio, which enhances the sense of presence by delivering sound from different directions based on the user's head movements.
5. Controllers: Some VR systems come with handheld controllers or other input devices that allow users to interact with objects and navigate within the virtual environment.
 */

/**
 * 1. Introduction to Azure Portal
Azure Portal is a web-based console that allows users to access and manage their Azure resources. It provides a user-friendly interface for managing and monitoring various Azure services and resources. Users can access the Azure Portal using a web browser and perform tasks such as creating and managing virtual machines, configuring networking, deploying applications, monitoring resource usage, and more.
2. Creating Resources - Logical Grouping using Resource Groups
In Azure, resources are typically organized and managed using resource groups. A resource group is a logical container that holds related resources for an application or project. It allows you to manage and monitor resources collectively and apply consistent policies and access controls.
By creating a resource group, you can logically group and organize resources such as virtual machines, storage accounts, databases, and networking components that belong to the same application or project. Resource groups also provide a way to manage the lifecycle of resources, making it easier to deploy, update, and delete resources as a unit.
3. Azure APIM (Azure API Management)
Azure API Management (APIM) is a fully managed service that helps organizations publish, secure, manage, and analyze APIs (Application Programming Interfaces). APIM acts as a gateway that sits between the API providers and consumers, providing features such as authentication, authorization, rate limiting, caching, and analytics.
With Azure APIM, organizations can expose their APIs to external developers, partners, and internal teams in a controlled and secure manner. It provides features for API versioning, documentation, and developer portal, making it easier for developers to discover and consume APIs. APIM also allows organizations to monetize their APIs by enforcing usage quotas and charging for API access.
4. Different Domains in Azure
Azure offers a wide range of services across various domains, including:
1. Compute: This includes services for virtual machines, containers, and serverless computing.
2. Networking: Azure provides services for virtual networks, load balancers, VPN gateways, and DNS management.
3. Storage: Azure offers scalable and durable storage options, including blob storage, file storage, and disk storage.
4. Databases: Azure provides managed database services like Azure SQL Database, Azure Cosmos DB, and Azure Database for MySQL/PostgreSQL.
5. Artificial Intelligence (AI) and Machine Learning (ML): Azure offers services for AI and ML, such as Azure Cognitive Services, Azure Machine Learning, and Azure Bot Service.
6. Internet of Things (IoT): Azure IoT services enable organizations to connect, monitor, and manage IoT devices and analyze IoT data.
7. DevOps: Azure provides tools and services for DevOps practices, including Azure DevOps, Azure Pipelines, and Azure Container Registry.
5. Azure Services
Azure offers a wide range of services and products to cater to various IT needs. Some of the key Azure services include:
1. Azure Virtual Machines: Provides scalable virtual machines in the cloud.
2. Azure App Service: Allows you to build, deploy, and scale web and mobile apps.
3. Azure Storage: Provides scalable and durable storage for data.
4. Azure SQL Database: Managed relational database service.
5. Azure Cosmos DB: Globally distributed NoSQL database service.
6. Azure Functions: Serverless computer service for running event-triggered code.
7. Azure Cognitive Services: Offers AI-powered APIs for vision, speech, language, and more.
8. Azure DevOps: Provides tools for planning, development, testing, and delivery.
9. Azure Kubernetes Service (AKS): Managed Kubernetes service for container orchestration.
6. Azure Job Trends
Azure has been gaining popularity and adoption in the IT industry. Many organizations are choosing Azure for their cloud computing needs. As a result, there is a growing demand for professionals with Azure skills. Job roles related to Azure include cloud architects, solution architects, Azure administrators, Azure developers, and data engineers.
 */


/**
 * 
 * Question 3
a) Augmented Reality (AR) can be applied in the education domain to enhance the learning experience and provide interactive and immersive educational content. Here are a few examples:

1. Interactive Visualizations: AR can be used to bring static objects or concepts to life by overlaying additional information or interactive elements. For example, students studying anatomy can use AR to explore a 3D model of the human body, allowing them to view different organs, systems, and their functions in a more engaging and interactive manner.

2. Historical and Cultural Exploration: AR can transport students to different historical or cultural contexts by overlaying virtual objects or characters onto the real-world environment. For instance, students can use AR to visit ancient historical sites and witness significant events or interact with virtual representations of historical figures, making history come alive and fostering a deeper understanding.

3. Science Simulations: AR can simulate scientific experiments and phenomena that may be difficult or expensive to replicate in a traditional classroom setting. Students can use AR applications to conduct virtual chemistry experiments, observe the effects of natural disasters like earthquakes, or explore complex scientific concepts in a safe and controlled environment.

4. Language Learning: AR can assist language learners by providing real-time translations and language immersion experiences. For example, students learning a foreign language can use AR to scan text or objects and instantly receive translations or hear pronunciations, helping them improve vocabulary and comprehension.

5. Gamified Learning: AR can gamify educational content, making it more engaging and interactive. For instance, students can participate in scavenger hunts or problem-solving challenges where they need to use AR to find and interact with virtual objects in the real world, promoting active learning and critical thinking.

b) Virtual Reality (VR) refers to a simulated environment that can be similar to or completely different from the real world. It immerses users in a virtual experience, usually through the use of headsets or goggles. Here are some examples of how VR can be applied with real-world examples:

1. Training Simulations: VR can provide realistic training simulations for various industries. For example, airline pilots can use VR to practice flight procedures and emergency situations without the need for a physical aircraft. Medical professionals can simulate surgeries or medical procedures, allowing them to gain practical experience in a safe and controlled environment.

2. Virtual Travel and Tourism: VR can offer virtual travel experiences, allowing users to explore different destinations without physically being there. Users can visit famous landmarks, explore cultural sites, and even experience extreme activities like skydiving or deep-sea diving, all from the comfort of their homes.

3. Architectural and Design Visualization: VR can aid architects and designers by creating immersive virtual walkthroughs of buildings or environments. This allows clients to experience and provide feedback on architectural designs before construction begins, saving time and resources.

4. Therapeutic Applications: VR has been used in therapeutic settings to treat phobias, anxiety disorders, and post-traumatic stress disorders. For example, patients with a fear of heights can undergo exposure therapy in a virtual environment where they can gradually confront and overcome their fear.

5. Virtual Collaboration: VR enables people to collaborate and interact in a shared virtual space, regardless of their physical locations. This can be beneficial for remote teamwork, design reviews, or virtual meetings, where participants can communicate and manipulate virtual objects as if they were in the same room.
Question 4
a) Three Azure resources that can be used to develop the solution are:
1. Azure App Service:
   - Purpose: Azure App Service is a platform-as-a-service (PaaS) offering that allows developers to build, deploy, and scale web applications and APIs. It provides a managed environment for hosting web applications without needing to manage the underlying infrastructure.
   - Pros: Easy deployment, automatic scaling, built-in load balancing, supports various programming languages and frameworks, integrates with other Azure services.
   - Cons: Limited control over the underlying infrastructure, higher cost compared to infrastructure-as-a-service (IaaS) options, limited customization options for runtime environments.
2. Azure SQL Database:
   - Purpose: Azure SQL Database is a fully managed relational database service in Azure. It provides a scalable and secure platform for storing and managing structured data.
   - Pros: Automatic backups and patching, built-in high availability and disaster recovery, scalable performance, built-in security features, integration with other Azure services.
   - Cons: Higher cost compared to self-managed database solutions, limited control over server-level configurations.
3. Azure Active Directory (Azure AD):
   - Purpose: Azure AD is a cloud-based identity and access management service. It provides authentication and authorization functionalities to secure access to applications and resources in Azure.
   - Pros: Single sign-on capabilities, multi-factor authentication support, role-based access control, integration with other Azure services, extensive identity management features.
   - Cons: Additional configuration and setup required, may have a learning curve for implementation.
b) To cater to the requirement of having one base URL for both the student service and class timetable service, an approach could be to use Azure API Management.
Azure API Management:
- Purpose: Azure API Management provides a comprehensive solution for managing APIs, including publishing, securing, and monitoring them. It acts as a gateway between clients and backend services, allowing you to consolidate multiple APIs under a single base URL.
- Justification: By using Azure API Management, you can create an API gateway that acts as a reverse proxy for both the student service and class timetable service. This allows you to expose the services through a unified base URL, providing a consistent interface to clients. Additionally, Azure API Management offers features like rate limiting, authentication, and caching, which can enhance the security and performance of the APIs.

c) For implementing authentication, a recommended approach would be to use Azure Active Directory (Azure AD) along with OAuth 2.0 and OpenID Connect protocols.

Azure AD with OAuth 2.0 and OpenID Connect:
- Justification: Azure AD provides a robust and scalable identity and access management solution. It supports OAuth 2.0 and OpenID Connect protocols, which are widely adopted and provide industry-standard authentication and authorization mechanisms. By leveraging Azure AD, you can implement features like single sign-on, multi-factor authentication, role-based access control, and secure token-based authentication. Azure AD also integrates well with other Azure services, making it easier to secure access to the student profile and class timetable services while providing a seamless user experience.

 * 
 */


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class demonstrates the creation of an XML document using the DOM API and generates additional Java classes.
 */
public class Question02 {

    // Constants for XML element and attribute names
    private static final String GENDER_ATTRIBUTE_NAME = "Gender";
    private static final String NAME_ELEMENT_NAME = "name";
    private static final String INITIALS_ATTRIBUTE_NAME = "Initials";
    private static final String ADDRESS_ELEMENT_NAME = "Address";
    private static final String NO_ATTRIBUTE_NAME = "No";
    private static final String STREET_ATTRIBUTE_NAME = "Street";

    // Output XML file name
    private static final String OUTPUT_FILE_NAME = "students.xml";

    /**
     * The main method, entry point of the program.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        try {
            // Create a new XML document
            Document document = createDocument();

            // Create the root element "School"
            Element schoolElement = createSchoolElement(document);

            // Create the "Students" element
            Element studentsElement = createStudentsElement(document);

            // Create the "Student" element with a gender attribute
            Element studentElement = createStudentElement(document, "Male");

            // Create the "name" element with initials attribute and text content
            Element nameElement = createNameElement(document, "S.A.", "Nalaka Dissanayake");

            // Create the "Address" element with attributes and text content
            Element addressElement = createAddressElement(document, "115/1", "Avenue Street", "No: 115/1, Avenue Street, Kandy");

            // Append child elements to their parent elements
            studentElement.appendChild(nameElement);
            studentElement.appendChild(addressElement);
            studentsElement.appendChild(studentElement);
            schoolElement.appendChild(studentsElement);
            document.appendChild(schoolElement);

            // Create a Transformer for XML serialization
            Transformer transformer = createTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(OUTPUT_FILE_NAME));

            // Write the XML document to a file
            transformer.transform(domSource, streamResult);
            System.out.println("XML file generated successfully!");

            // Generate additional Java classes
            generateXmlOutputAnswer();
            generateGenerateXMLOutput();
        } catch (Exception e) {
            // Handle exceptions properly
            e.printStackTrace();
        }
    }

    /**
     * Creates a new XML document.
     *
     * @return The created Document object
     * @throws Exception if an error occurs during document creation
     */
    private static Document createDocument() throws Exception {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }

    /**
     * Creates the root element "School".
     *
     * @param document The Document object
     * @return The created School element
     */
    private static Element createSchoolElement(Document document) {
        return document.createElement("School");
    }

    /**
     * Creates the "Students" element.
     *
     * @param document The Document object
     * @return The created Students element
     */
    private static Element createStudentsElement(Document document) {
        return document.createElement("Students");
    }

    /**
     * Creates the "Student" element with the specified gender attribute.
     *
     * @param document The Document object
     * @param gender   The gender attribute value
     * @return The created Student element
     */
    private static Element createStudentElement(Document document, String gender) {
        Element studentElement = document.createElement("Student");
        Attr genderAttr = document.createAttribute(GENDER_ATTRIBUTE_NAME);
        genderAttr.setValue(gender);
        studentElement.setAttributeNode(genderAttr);
        return studentElement;
    }

    /**
     * Creates the "name" element with initials attribute and text content.
     *
     * @param document  The Document object
     * @param initials  The initials attribute value
     * @param name      The text content of the name element
     * @return The created name element
     */
    private static Element createNameElement(Document document, String initials, String name) {
        Element nameElement = document.createElement(NAME_ELEMENT_NAME);
        Attr initialsAttr = document.createAttribute(INITIALS_ATTRIBUTE_NAME);
        initialsAttr.setValue(initials);
        nameElement.setAttributeNode(initialsAttr);
        nameElement.appendChild(document.createTextNode(name));
        return nameElement;
    }

    /**
     * Creates the "Address" element with attributes and text content.
     *
     * @param document      The Document object
     * @param number        The value of the "No" attribute
     * @param street        The value of the "Street" attribute
     * @param addressText   The text content of the Address element
     * @return The created Address element
     */
    private static Element createAddressElement(Document document, String number, String street, String addressText) {
        Element addressElement = document.createElement(ADDRESS_ELEMENT_NAME);
        Attr numberAttr = document.createAttribute(NO_ATTRIBUTE_NAME);
        numberAttr.setValue(number);
        addressElement.setAttributeNode(numberAttr);
        Attr streetAttr = document.createAttribute(STREET_ATTRIBUTE_NAME);
        streetAttr.setValue(street);
        addressElement.setAttributeNode(streetAttr);
        addressElement.appendChild(document.createTextNode(addressText));
        return addressElement;
    }

    /**
     * Creates a Transformer for XML serialization.
     *
     * @return The created Transformer object
     * @throws TransformerConfigurationException if an error occurs during Transformer creation
     */
    private static Transformer createTransformer() throws TransformerConfigurationException {
        return TransformerFactory.newInstance().newTransformer();
    }

    /**
     * Generates the "XmlOutputAnswer" class with its content.
     *
     * @throws IOException if an error occurs during file writing
     */
    private static void generateXmlOutputAnswer() throws IOException {
        String xmlOutputAnswerContent = """
                
                
                public class XmlOutputAnswer {
                    public static void main(String[] args) {
                        System.out.println("XmlOutputAnswer: XML output generated successfully!");
                    }
                }
                """;

        FileWriter fileWriter = new FileWriter("XmlOutputAnswer.java");
        fileWriter.write(xmlOutputAnswerContent);
        fileWriter.close();
    }

    /**
     * Generates the "GenerateXMLOutput" class with its content.
     *
     * @throws IOException if an error occurs during file writing
     */
    private static void generateGenerateXMLOutput() throws IOException {
        String generateXMLOutputContent = """
                
                public class GenerateXMLOutput {
                    public static void main(String[] args) {
                        System.out.println("GenerateXMLOutput: XML output generated successfully!");
                    }
                }
                """;

        FileWriter fileWriter = new FileWriter("GenerateXMLOutput.java");
        fileWriter.write(generateXMLOutputContent);
        fileWriter.close();
    }
}
