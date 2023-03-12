import java.io.PrintStream;
//Se importa la libreria del hashtable
import java.util.Hashtable;
import java.lang.String;
import java.util.ArrayList;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ClaseSemantica{
	//Se hace una instancia de la clase Hashtable con un objeto llamado tabla
    public static Hashtable <String, Integer> tabla = new Hashtable();
	//Se declara una lista para cada tipo de dato: Entero, Decimal y String
	//En el se guardaran las key de los tokens
    private static ArrayList<Integer> intComp = new ArrayList();
	private static ArrayList<Integer> decComp = new ArrayList();
	private static ArrayList<Integer> strComp = new ArrayList();

    public static void InsertarSimbolo(Token identificador, int tp)
	{
		//En este metodo se agrega a la tabla de tokens el identificador(lexema para el nombre de las variable)
		//que esta siendo declarado junto con su tipo de dato. (el tipo de dato es su id dentro de la tabla de lexemas)
		//
		tabla.put(identificador.image, tp);
	 }


	 public static void SetTables()
	{
		/*En este metodo se inicializan las tablas, las cuales almacenaran los tipo de datos compatibles con:		
		 entero = intComp
		 decimal = decComp
		 cadena = strComp

		 Esto nos sirve para comparar luego si un dato declarado (ej C_ cadenadetexto) es compatible con el valor al que
		 // se le esta asignando (que sea una "cadena de texto" y no un numero o true/false booleano )
		*/
		intComp.add(34);
		intComp.add(38);
		
		decComp.add(34);
		decComp.add(35);
		decComp.add(38);
		decComp.add(39);

		
		strComp.add(36);
		strComp.add(40);

		/* TOKENS Y SU KEY
		 * <ENTERO: "E_">//34
		|<FLOTANTE: "D_">//35
		|<ID_CADENA: "C_">//36
		|<BOOLEANO: "L_">//37
		
		|<NUMERO: (["0"-"9"])+>//38
		|<NUMDECIMAL: (["0"-"9"])+ "." (["0"-"9"])+ | (["0"-"9"])+ "." | "." (["0"-"9"])+>//39
		|<CADENAS: ("\""(~["\"","#","\n","\r","#","#","\r","\n"])*"\"")>//40
		|<EST_LOGIC:("falso"|"verdad")>//41
}   

TOKEN: 
{
		<IDENTIFICADOR:["A"-"Z","a"-"z"](["A"-"Z","a"-"z","0"-"9"])*>//42

}

TOKEN: 
{ 
		<UNKNOW : ~[]> //43
}
		 */
		
	}

	// **** ESTE METODO SOLO COMPRUEBA SI UNA ASIGNACION SE ESTA EJECUTANDO CON VALORES CONGRUENTES
	//		NO HACE COMPROBACIONES DE SI EL TOKEN YA HA SIDO DECLARADO O NO, DE ESO SE ENCARGA EL METODO DE ABAJO CheckVariable

								// nombre variable    valor de la variable (entero,decimal,cadena de texto)
	public static String checkAsing(Token TokenIzq, Token TokenAsig)
	{
		//variables en las cuales se almacenara el tipo de dato del identificador 
		//y de las asignaciones (ejemplo: n1(tipoIdent1) = 2(tipoIdent2) + 3(tipoIdent2))
		int tipoIdent1;
		int tipoIdent2;		
							/* De la tabla de simbolos obtenemos el tipo de dato del identificador  
								asi como, si el token enviado es diferente a algun tipo que no se declara como los numeros(32), los decimales(35),
								caracteres(38) y cadenas(31)
								entonces tipoIdent1 = tipo_de_dato, ya que TokenAsig es un objeto tipo token*/

		//Aqui estamos comprobando si el token declarado no es un valor, sino el nombre de una variable
		if(TokenIzq.kind != 38 && TokenIzq.kind != 39 && TokenIzq.kind != 40)		
		{
			try 
			{	//confirmamos que el token sea de un <IDENTIFICADOR>
				// Y ahora comprobamos que ya haya sido declarada en la tabla de simbolos

				//Si el TokenIzq.image existe dentro de la tabla de tokens,
				// entonces tipoIdent1 toma el tipo de dato con el que TokenIzq.image fue declarado
				tipoIdent1 = (Integer)tabla.get(TokenIzq.image);	
			}
			catch(Exception e)
			{
				//Si TokenIzq.image no se encuentra en la tabla en la cual se agregan los tokens,
				// el token no ha sido declarado, y se manda un error.
				//Se envia un espacio string, porque todos estos metodos retornan un string
				//usualmente de errores
				return " ";
			}
		}
		else 		
			tipoIdent1 = 0; //Si el token no es un identificador, entonces existe un error de sintaxis en el codigo
			
		//TokenAsig.kind != 48 && TokenAsig.kind != 50 && TokenAsig.kind != 51 && TokenAsig.kind != 52
		//Se verifica si el token que esta del lado derecho de la asignacion (a = b;) es un identificador
		if(TokenAsig.kind == 42)	
		{
			/*Si el tipo de dato que se esta asignando, es algun identificador(kind == 39) 
			se obtiene su tipo de la tabla de tokens para poder hacer las comparaciones
			PARA ESTO, LA VARIABLE YA DEBIO HABER SIDO DECLARADA
			*/
			try
			{
				tipoIdent2 = (Integer)tabla.get(TokenAsig.image);
			} // SI NO SE ENCUENTRA, NO HA SIDO DECLARADA Y ES MANEJADA INDEPENDIENTEMENTE POR EL METODO DE ABAJO
			catch(Exception e)
			{
				//si el identificador no existe manda el string de error
				return " ";
			}
		}
				//Si el dato es entero(35) o decimal(36) o cadena(31)
				// a la variable para hacer comparaciones, se le asigna su id de tipo de dato
				//tipoIdent2 = id_tipo_del_dato dentro de la lista de LEXEMAS
		else if(TokenAsig.kind == 38 || TokenAsig.kind == 39 || TokenAsig.kind == 40)
			tipoIdent2 = TokenAsig.kind;
		else //Si no, se inicializa en algun valor "sin significado(con respecto a los tokens)", para que la variable este inicializada y no marque error al comparar
			tipoIdent2 = 0; 

			
	  
		
		if(tipoIdent1 == 34) //Int
		{
			//Si la lista de enteros(intComp) contiene el valor de tipoIdent2, entonces es compatible y se puede hacer la asignacion
			if(intComp.contains(tipoIdent2))
				return " ";
			else //Si el tipo de dato no es compatible manda el error
				return "Error semantico en la linea "+TokenAsig.beginLine+", columna "+TokenAsig.beginColumn+ " no se puede convertir " + TokenAsig.image + " a Entero\r\n";
		}
		else if(tipoIdent1 == 35 || tipoIdent1 == 34) //decimal
		{
			if(decComp.contains(tipoIdent2))
				return " ";
			else
				return "Error semantico en la linea "+TokenAsig.beginLine+", columna "+TokenAsig.beginColumn+ " no se puede convertir " + TokenAsig.image + " a Decimal \r\n";
		}
		else if(tipoIdent1 == 36) //string
		{
			if(strComp.contains(tipoIdent2))
				return " ";
			else
				return "Error semantico en la linea "+TokenAsig.beginLine+", columna "+TokenAsig.beginColumn+ " no se puede convertir " + TokenAsig.image + " a Cadena \r\n";
		}else
		{ //SI UNA DE LAS DOS VARIABLES tipoIdent es un cero, manda el string de error, debido a que no han sido declaradas 
			return " ";
		}
	}	  




	/*Metodo que verifica si un identificador YA HA sido declarado, 
	ej cuando se declaran las asignaciones: E_ numero1: 4, C_ cadena1 : "este es un string",i++, i--)

	//Este metodo es llamado desde el compilador desde dos gramaticas
	// DECLARACION() y ASIGNACION() 
	*/ 
		public static String checkVariable(Token checkTok)
		{
			try
			{
				//Intenta obtener el token a verificar(Token enviado desde la gramatica declaracion) 
				// y verifica en la tabla hash los tokens ya existentes
				//Verifica si ya fue declarado con la image del token
				
				//Aqui hay de dos, por eso existe un try-catch.
				// Si al verificar dentro de la tabla no existe dicha variable
				//entonces enviara un string explicando el error en el catch
				// pero si no hay error, entonces regresa un string vacio porque si existe la variable en la tabla de simbolos
				int tipoIdent1 = (Integer)tabla.get(checkTok.image);
				return "";

			}// SI NO SE ENCONTRO EL TOKEN
			//Desde la perspectiva de la gramatica DECLARACION(), no hay problema, solo lo inserta a la tabla de simbolos en la clase del compilador.
			//Pero desde la perspectiva de la gramatica ASIGNACION(), entonces si hay problema
			//	debido a que no existe una variable a la cual asignarle y relacionarle datos
			catch(Exception e)
			{				//Si no lo puede obtener, manda el error
				//								    Linea donde esta el token		  Columna donde esta el token	Imagen del token
				return "Error semantico en la linea " +checkTok.beginLine +", columna  "+checkTok.beginColumn +", "+ checkTok.image + " no ha sido declarado \r\n";
			}// La gramatica DECLARACION() no hace nada con este string
			// La gramatica ASIGNACION() la guarda con los otros errores
		}
	


	public static void Crear_txt(){
		try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tabla_symbol.txt"));
            for (String key : tabla.keySet()) {
                int value = tabla.get(key);
                writer.write(key + "=" + value);
                writer.newLine();
            }
            writer.close();
            System.out.println("Se ha creado la tabla de simbolos exitosamente dentro del directorio");
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error durante la creacion del archivo: " + e.getMessage());
        }
	}
}