package uva.tds.pr1.grupo12;

import java.util.*;

public class Person implements Contact {

	private String nombre;
	private String apellidos;
	private String ID;
	private Map<String, EnumKindOfPhone> numeros;
	private ArrayList<String> correos;
	private String nick;

	public Person() {
		
	}

	public Person(String nombre, String ID, ArrayList<String> correos, String apellidos, Map<String, EnumKindOfPhone> numeros,
			String nick) {
		this.nombre = nombre;
		this.ID = ID;
		this.correos = correos;
		this.apellidos = apellidos;
		this.numeros = numeros;
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Map<String, EnumKindOfPhone> getNumeros() {
		return numeros;
	}

	public void añadeNumero(String num, EnumKindOfPhone tipo) {
		numeros.put(num, tipo);
	}

	public void borraNumero(String num) {
		numeros.remove(num);
	}

	public ArrayList<String> getCorreos() {
		return correos;
	}
	
	public void añadeCorreo(String correo) {
		correos.add(correo);
	}
	
	public void borraCorreo(String correo) {
		correos.remove(correo);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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
