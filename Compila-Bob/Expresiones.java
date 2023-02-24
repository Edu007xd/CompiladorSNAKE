
import java.util.ArrayList;
import java.util.Scanner;

public class Expresiones {
    private static Nodo inicio,fin;
    private static int size;
    private static String epos="";
    private static ArrayList<String> temp= new ArrayList();
    
    
    public static void inserta(String valor){
        temp.add(" ");
        if(inicio==null){
            inicio=fin= new Nodo(valor,null);
        }else{
            Nodo aux=inicio;
            inicio = new Nodo(valor,aux);
        }
    }
    //este método no se usa
    public static void muestra(){
        if(inicio!=null){
            String datos ="~~";
            Nodo  auxiliar=inicio;
            while(auxiliar!=null){
                datos = datos+"["+auxiliar.valor+"]~~";
                auxiliar=auxiliar.sig;
            }
            System.out.println("--------------------------- COLA --------------------------\n"+datos);
        }else{
            System.out.println(" NO HAY DATOS ! :(");
        }
    }
    
    public static void elimina(){
        inicio=inicio.sig;
    }
    
    
    public static void Convierte(String infija){
        epos="";
        char x;
        int cont=0;
        String c;
        StringBuilder inFija = new StringBuilder(infija);//Objeto para hacer el decremento 
        inicio=null;                                //de los caracteres en la expresión
        while(cont<infija.length()){
            c = Character.toString(infija.charAt(cont));//Casteo de CHAR a STRING ya que 
            inFija.deleteCharAt(0);                  //el dato que nos devuelve la lectura es CHAR
            cont++; //En la linea anterior se elimina ese caracter de la cadena
            if(c.equals("(")){
                inserta(c);
            }else{
                if(c.equals(")")){
                    //Si es un parentesis derecho se agregan a EPOS 
                    //todos los operadores que tengamos en pila -->
                    while(!"(".equals(inicio.valor)){
                        epos=epos+inicio.valor;
                        elimina();
                    }
                    elimina();
                }else{
                    if(Character.isDigit(c.charAt(0))){
                        epos=epos+c;
                    }else{
                        if(inicio!=null){
                            x =inicio.valor.charAt(0);
                            //En la siguiente linea se compara el digito 
                            //actual con la jerarquía de operadores mediante el código ASCII
                            while((inicio!=null && (infija.charAt(cont)<=x))){
                                epos=epos+inicio.valor;
                                elimina();
                            }
                            inserta(c);
                        }else{//else para agregar a la pila operadores que se encuentran después de un
                            //parentesis derecho, al estar vacía la pila no entra en el if de la linea 50.
                            inserta(c);
                        }
                    }
                }
            }
            
        }//While para agregar a EPOS todos los operadores finales que deban ir a la derecha
        while(inicio!=null){
            epos=epos+inicio.valor;
            elimina();
        }
        System.out.println("La expresión en posfija es: "+epos);
    }
    
    public void resultado(){
        String exp="";
        exp=epos;
        String respila;
        int i=0,op1,op2,res;
        while(i<epos.length()){
            exp= Character.toString(epos.charAt(i));
            i++;
            if(Character.isDigit(exp.charAt(0))){
                //Si este caracter es un operando se agrega a la pila
                        inserta(exp);
            }else{
                op2=Integer.parseInt(inicio.valor);//Casteo del valor que en pila es String para
                elimina();                         //poder hacer los calculos necesarios
                op1=Integer.parseInt(inicio.valor);
                elimina();//Se eliminan ambos valores de la pila y se guarda el resultado
                res=calcula(op1,exp,op2);
                inserta(respila=Integer.toString(res));//Este cast es necesario porque 
                                                       //la pila sólo acepta valores String
            }
        }
        System.out.println("El resultado es: "+inicio.valor);
        
    }
    //metodo que hace el cálculo corresponiente entre dos operandos
    public int calcula(int op1,String operador,int op2){//Los operandos se leen al revés pero se capturan 
        int res=0;  
        String imprime = "";                                    //en orden ( op1 afecta a op2 y no al revés)
        if(operador.equals("+")){
            res=op1+op2;
        }
        if(operador.equals("-")){
            res=op1-op2;
        }
        if(operador.equals("*")){
            res=op1*op2;
        }
        if(operador.equals("/")){
            res=(op1/op2);
        }
        if(operador.equals("^")){
            res=op1^op2;
        }
        imprime = operador+" | "+Integer.toString(op1)+" | "+Integer.toString(op2);
        temp.add(imprime);
        return res;
    } 
    
    
    public static class Nodo{
        String valor;
        Nodo sig;
        Nodo(int n){
            
        }
        Nodo(String dato,Nodo siguiente){
            valor=dato;
            sig = siguiente;
        } 
    }
}
