package uva.tds.pr1.grupo12;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

//Deberia poder acceder al XML para poder implementar los metodos
public class EContactSystemImpl implements EContactSystemInterface {

	public static EContactSystemInterface contactSystemFactory() {
		return EContactSystemImpl.contactSystemFactory();
	}

	private ArrayList<Person> usuarios = new ArrayList<Person>();

	private ArrayList<Group> grupos = new ArrayList<Group>();

	Document doc;

	@Override
	public void loadFrom(Path pathToXML) {
		
		Element element=null;

		try {

			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = documentBuilder.parse(new InputSource(new FileInputStream(pathToXML.toString())));
			NodeList nodeList = doc.getElementsByTagName("grupo");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Group grupo = new Group();
				element = (Element) nodeList.item(i);
				
				//Node node = nodeList.item(i);

				grupo.setNombre(element.getAttributes().getNamedItem("nombre").getNodeValue());
				grupo.setID(element.getAttributes().getNamedItem("idGrupo").getNodeValue());

				grupos.add(grupo);

			}
			
			nodeList=doc.getElementsByTagName("persona");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Person persona=new Person();
				
				element=(Element)nodeList.item(i);
				
				NodeList nombre = element.getElementsByTagName("nombre");
				NodeList apellidos = element.getElementsByTagName("apellidos");
				NodeList correo = element.getElementsByTagName("correo_electronico");
				NodeList telefono = element.getElementsByTagName("telefono");
				
				persona.setNombre(nombre.item(0).getFirstChild().getTextContent());
				persona.setApellidos(apellidos.item(0).getFirstChild().getTextContent());
				persona.añadeCorreo(correo.item(0).getFirstChild().getTextContent());
				persona.añadeNumero(telefono.item(0).getFirstChild().getTextContent(), null);
				persona.setID(element.getAttributes().getNamedItem("alias").getNodeValue());
				
				String grupo=element.getAttributes().getNamedItem("grupo").getNodeValue();
				
				usuarios.add(persona);
				
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (SAXException e3) {
			e3.printStackTrace();
		}
	}

	@Override
	public void updateTo(Path pathToXML) {
	}

	@Override
	public boolean isXMLLoaded() {
		return (doc!=null)? true:false;
	}

	@Override
	public boolean isModifiedAfterLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createNewPerson(String name, String nickname, String surName, String[] emails,
			Map<String, EnumKindOfPhone> phones) {
	}

	@Override
	public void createNewGroup(String name, Contact[] contacts) {
	}

	@Override
	public Contact getAnyContactById(String id) {
		return null;
	}

	@Override
	public Person getPersonByNickname(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroupByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addContactToGroup(Contact contact, Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContactFromGroup(Contact contact, Group group) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeContactFromSystem(Contact contact) {
		// TODO Auto-generated method stub

	}

}
