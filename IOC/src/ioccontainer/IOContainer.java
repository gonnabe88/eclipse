package ioccontainer;

//Import the Java classes
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

//Import the Reflection classes
import java.lang.reflect.Method;

//Import the JDOM classes
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/*
 * This is our simple IOC Container, it parses
 * IOC-application-context.xml and uses DI
 * (implemented using Reflection and a simple Factory)
 * to create and assemble the beans.
 */
public class IOContainer {

	/**
	 * Keeps track of all our bean nodes.
	 */
	private List beanList;



	/**
	 * Creates a new Container that is configured by the specified XML
	 * filename, we use JDOM SAX to traverse through our XML document.
	 */
	public IOContainer(String application_context) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(application_context);
			Element root = doc.getRootElement();
			this.beanList = root.getChildren("bean");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a configured bean based on the name for example: <bean id="Band"
	 * class="fm.devhub.objects.Band"> has the name 'Band'
	 */
	public Object getBean(String name) {

		// Iterate over our bean list, this was
		// populated during the instantiation of our
		// IOC container.
		for (Iterator i = this.beanList.iterator(); i.hasNext();) {
			Element bean = (Element) i.next();
			String id = bean.getAttributeValue("id");

			if (id.equals(name)) {
				try {

					String className = bean.getAttributeValue("class");

					// Create an instance of our configured
					// bean.
					Object beanInstance = Class.forName(className)
							.newInstance();

					// This will return all of the
					// properties associated with the bean.
					// For example:
					// <property name="name" value="Led Zeppelin" />
					// <property name="label" value="Atlantic" />
					// <property name="genre" value="Rock" />
					List propertyList = bean.getChildren("property");

					if (propertyList.size() > 0) {

						// Returns the runtime class of an object.
						// That Class object is the object that is
						// locked by static synchronized methods
						// of the represented class.
						Class beanClass = beanInstance.getClass();

						// Build a mapping of setter method names to Method
						// objects
						Map classMethods = new HashMap();
						Method[] methods = beanClass.getMethods();
						for (int x = 0; x < methods.length; x++) {
							String methodName = methods[x].getName();
							if (methodName.startsWith("set")) {
								methodName = Character.toLowerCase(methodName
										.charAt(3)) + methodName.substring(4);
								classMethods.put(methodName, methods[x]);
							}
						}

						// Iterate over all of the properties in the XML file
						for (Iterator it = propertyList.iterator(); it
								.hasNext();) {
							// Obtain the initialization property
							Element propertyElement = (Element) it.next();
							String propertyName = propertyElement
									.getAttributeValue("name");
							String propertyValue = propertyElement
									.getAttributeValue("value");
							String propertyReference = null;

							// Check for a referenced bean
							if (propertyValue == null) {
								propertyReference = propertyElement
										.getAttributeValue("bean-reference");
							}

							// Invoke the appropriate setter method
							if (classMethods.containsKey(propertyName)) {
								// Obtain the setter method for this property
								Method method = (Method) classMethods
										.get(propertyName);

								// Handle the parameter type, convert to the
								// appropriate type
								Class[] parameterTypes = method
										.getParameterTypes();
								String parameterName = parameterTypes[0]
										.getCanonicalName();
								Object[] params = new Object[1];

								if (parameterName.equals("java.lang.String")) {
									params[0] = propertyValue;
									// Check for a reference
								} else {
									if (propertyReference != null) {
										params[0] = getBean(propertyReference);
									}
								}

								// Invokes the underlying method represented
								// by this Method object, on the specified
								// object with the specified parameters.
								method.invoke(beanInstance, params);
							}
						}
					}

					return beanInstance;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// If no matching objects are found, then return null
		return null;
	}
}