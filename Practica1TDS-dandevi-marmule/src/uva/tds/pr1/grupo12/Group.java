package uva.tds.pr1.grupo12;

import java.util.*;

public class Group implements Contact {

	private String nombre;
	private ArrayList<Contact> listaContactos;
	private String ID;

	public Group() {
		
	}
	
	public Group(String nombre, ArrayList<Contact> listaContactos, String ID) {
		this.nombre = nombre;
		this.listaContactos = listaContactos;
		this.ID = ID;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Contact> getListaContactos() {
		return listaContactos;
	}

	public void añadeContacto(Contact contacto) {
		listaContactos.add(contacto);
	}
	
	public void borraContacto(Contact contacto) {
		listaContactos.remove(contacto);
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void setID(String ID) {
		this.ID = ID;
	}

}
