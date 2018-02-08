package uva.tds.pr1.grupo12;

import java.nio.file.Path;
import java.util.Map;

public interface EContactSystemInterface {
	
	//public static EcontactSystemInterface contactSystemFactory();

	void loadFrom(Path pathToXML);

	void updateTo(Path pathToXML);

	boolean isXMLLoaded();

	boolean isModifiedAfterLoaded();

	void createNewPerson(String name, String nickname, String surName, String[] emails,
			Map<String, EnumKindOfPhone> phones);

	void createNewGroup(String name, Contact[] contacts);

	Contact getAnyContactById(String id);

	Person getPersonByNickname(String name);

	Group getGroupByName(String name);

	void addContactToGroup(Contact contact, Group group);

	void removeContactFromGroup(Contact contact, Group group);

	void removeContactFromSystem(Contact contact);
}