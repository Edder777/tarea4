package leer_json_jsonsimple;

import java.net.InetAddress;
import java.net.Socket;
import java.io.*;
import java.net.*;
import java.lang.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.*;

public class Tarea4 {

	public static int countChar(String str, char c) {
		int count = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c)
				count++;
		}

		return count;
	}

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();
		String archiv = "Reporte.json";

		try {
			Object obj = parser.parse(new FileReader(archiv));

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println("**** Tarea 4 **** ");
			System.out.println("Bienvenido.");
			System.out.println("Ingrese la cadena para comenzar: ");
			Scanner sc1 = new Scanner(System.in);
			String texto = sc1.nextLine();
			int numeroDeVeces = 0;
			// char izar
			char[] aCaracteres = texto.toCharArray();

			List<String> textoArray = new ArrayList();

			// LLENAMOS UNA LISTA OMITIENDO LOS CARACTERES REPETIDOS/
			for (int x = 0; x < aCaracteres.length; x++) {

				if (textoArray.isEmpty()) {
					textoArray.add(String.valueOf(aCaracteres[x]));
				}
				if (!textoArray.contains(String.valueOf(aCaracteres[x]))) {
					textoArray.add(String.valueOf(aCaracteres[x]));
				}

			}

			for (int i = 0; i < textoArray.size(); i++) {
				numeroDeVeces = countChar(texto, textoArray.get(i).charAt(0));
				System.out.println(textoArray.get(i) + " " + numeroDeVeces);
			}

			if (jsonObject.isEmpty()) {

				/*
				 * INSTANCIAMOS LOS OBJETOS JSON QUE VAN A CONTENER LA INFO PARA LA ESCRITORA
				 * DEL ARCHIVO JSON
				 */

				JSONArray nuevoJsonArray = new JSONArray();
				JSONObject jsonObj1 = new JSONObject();
				/*
				 * ITERAMOS EL ARREGLO DE CARACTERES NO REPETIDOS Y LLAMAMOS A LA FUNCION DE
				 * CONTAR CARATERES Y GUARDAMOS EN EL OBJETO JSON
				 */
				for (int i = 0; i < textoArray.size(); i++) {
					numeroDeVeces = countChar(texto, textoArray.get(i).charAt(0));
					jsonObj1.put(textoArray.get(i), numeroDeVeces);

				}
				/* INSTANCIAMOS EL OBJETOS JSON PRINCIPAL QUE CONTENDRA EL ARREGLO */
				JSONObject jsonObj2 = new JSONObject();
				/* DEFINIMOS LA CUALIDAD DEL OBJETO JSON PRINPIPAL */
				jsonObj2.put("lineas", 1);
				/*
				 * AGREGAMOS EL OBJETO PREVIO LLENO CON LAS LETRAS Y SU NUMERO DE VECES
				 * REPETIDAS AL ARREGLO
				 */
				nuevoJsonArray.add(jsonObj1);
				
				/* AÑADIMOS EL ARREGLO AL OBJETO JSON PRINCIPAL */
				jsonObj2.put("frecuencias", nuevoJsonArray);

				try {
					/*
					 * INSTANCIAMOS EL OBJETO FILEWRITERCON LA RUTA DEL ARCHIVO A SOBRE ESCRIBIR Y
					 * LO ESCRIBIMSO CON EL OBJETO JSON PRINCIPAL
					 */
					FileWriter file = new FileWriter(archiv);
					file.write(jsonObj2.toJSONString());
					file.flush();
					file.close();

				} catch (Exception ex) {
					System.out.println("Error: " + ex.toString());
				} finally {

				}

			} else {

				long lineas = (long) jsonObject.get("lineas");

				// recorrer arreglo
				JSONArray jArray = (JSONArray) jsonObject.get("frecuencias");
				JSONArray nuevoJsonArray = new JSONArray();

				JSONObject jsonObj1 = new JSONObject();
				for (int json = 0; json < jArray.size(); json++) {

					JSONObject JsonObjetTemp = (JSONObject) jArray.get(json);

					for (int i = 0; i < textoArray.size(); i++) {

						long valJson = 0;
						int valString = 0;
						long result = 0;
						String nombre = "";

						if (jArray.toString().contains(textoArray.get(i))) {

							valJson = (Long) JsonObjetTemp.get(textoArray.get(i));
							valString = countChar(texto, textoArray.get(i).charAt(0));
							result = valJson + valString;
							nombre = textoArray.get(i);
							jsonObj1.put(nombre, result);

						} else {
							nombre = textoArray.get(i);
							valString = countChar(texto, textoArray.get(i).charAt(0));
							jsonObj1.put(nombre, valString);

						}

					}

				}

				JSONObject jsonObj2 = new JSONObject();
				long lineasnew = lineas + 1;
				jsonObj2.put("lineas", lineasnew);

				nuevoJsonArray.add(jsonObj1);

				jsonObj2.put("frecuencias", nuevoJsonArray);

				try {
					FileWriter file = new FileWriter(archiv);
					file.write(jsonObj2.toJSONString());
					file.flush();
					file.close();

				} catch (Exception ex) {
					System.out.println("Error: " + ex.toString());
				} finally {

				}

			} // fin else

		} catch (Exception ex) {
			System.err.println("Error: " + ex.toString());
		} finally {

		}

	}

}
