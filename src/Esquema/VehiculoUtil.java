/**
 * @author Cristyan Morales Acevedo.
 * @desc Clase vehiculoUtil, contiene los validadores de información del sistema
 */

package Esquema; //Paquete contenedor

import java.time.LocalDate; //Requerido para el reconocimiento de fechas y sus diferentes funciones, como el LocalDate.isBefore()
import java.util.Scanner;
import java.util.regex.*; //Requerido para el funcionamiento del Regex

public class VehiculoUtil {
    /**
     * Valida que se cumpla las características referentes al patrón a tratar, (funciona para NIE/DNI y para la matrícula)
     *
     * @param patron Recibe el formato en código Regex de cómo se debería ver la información solicitada
     * @param test   Recibe la información a comprobar
     * @return valido: Verdadero si cumple con las características, falso si no las cumple.
     */
    public static boolean validador(String patron, String test) {
        boolean valido;
        Pattern cadena = Pattern.compile(patron);
        Matcher comprobador = cadena.matcher(test);
        valido = comprobador.matches();
        return valido;
    }

    /**
     * Comprueba los valores de los kilometros tomando un número como mínimo, (funciona al ingresar los kilómetros y al actualizarlos)
     *
     * @param kilometros el que es mayor
     * @param min        comprueba el que es menor
     * @return error: verdadero si kilometros es mayor, falso en lo contrario o que se produzca un error de reconocimiento de datos
     */
    public static boolean validarKilometros(int kilometros, int min) {
        boolean error = true;
        try {
            error = kilometros <= min;
        } catch (Exception ignored) {
        }
        /*
        catch ignorado ya que si no se consigue ingresar no puede cambiar el valor de error a falso
        */
        return error;
    }

    /**
     * Verifica dos cosas:
     * 1. Que la fecha sea válida y no se produzca un error al ingresar un mes erroneo (como letras o que no se ingrese un número inválido en el día o el mes)
     * 2. Que la fecha sea anterior al día de hoy
     *
     * @param anio año de compra
     * @param mes  mes de compra (en número)
     * @param dia  de compra
     * @return error: falso si la fecha es válida y es anterior al día de hoy, verdadero si no cumple con ambas características, (ajustandose a la variable error de Principal)
     */
    public static boolean validarFecha(int anio, int mes, int dia) {
        LocalDate fecha; //parametro que va a albergar la fecha reconstruida en formato LocalDate(año, mes, día)
        try {
            fecha = LocalDate.of(anio, mes, dia);
            if (fecha.isBefore(LocalDate.now()))
                return false;
        } catch (Exception ignored) {}
       /*
       Se genera un catch en caso de que falle el ingreso de la fecha por valores incorrectos, como error sigue siendo true,
       se ignora dicho catch.
       */
        return true;
    }

    /**
     * Se nos solicita comprobar el nombre de la pesona exclusivamente usando métodos de la clase String, debe contar con al menos un nombre y dos apellidos (dos espacios intermedios) y un tamaño máximo de 40 caracteres
     * @param nombre de la persona a verificar
     * @return error: un booleano que será true si el nombre que ingresa no cumple con las condiciones, y falso si las cumple
     */
    public static boolean verificarNombre(String nombre) {
        boolean error = true;
        int[] spc = new int[3];
        spc[0]=-1;
        int i = 1;
        if (nombre.length() <= 40) {
            do {
                spc[i] = nombre.indexOf(" ", spc[i - 1]+1);
                i++;
            } while (spc[1] != -1 && i < 3);
            if (spc[1] != -1 && spc[2] != -1)
                error = false;
        }
        return error;
    }

    /**
     * Método que se encarga de administrar la función de reconocimiento de patrones y contacta con Ejerc1Util.VehiculoUtil
     *
     * @param nombreDato nombre de la variable a verificar
     * @param patron     el patrón al que debe cumplir, de formato Regex.
     * @param mensaje    mensaje de error que sale al fallo
     * @return patrón ya verificado
     */
    public static String verificarPatron(String nombreDato, String patron, String mensaje) {
        boolean valido; //booleano, verdadero si no hay error en el ingreso, falso si lo hay
        String dato;
        do {
            dato = pedirString(nombreDato);
            valido = validador(patron, dato);
            if (!valido)
                System.out.println(mensaje);
        } while (!valido);
        return dato;
    }

    /**
     * Método que permite pedir cadenas de caracteres al usuario
     *
     * @param nombreDato dato que completa el nombre del parametro a completar
     * @return la cadena ya recibida.
     */
    public static String pedirString(String nombreDato) {
        System.out.print("Introduce " + nombreDato + ": ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Método que recibe la matrícula y valida si está bien implementada
     * @param matricula del vehículo
     * @return La matrícula con el formato correcto
     */
    public static String ingresarMatricula(String matricula) {
        Scanner ingreso = new Scanner(System.in);
        String dato;
        boolean error = true;
        do {
            System.out.println("Por favor ingrese el número de matrícula " + matricula);
            dato = ingreso.next();
            if (validador("[0-9]{4}[A-Z]{3}", dato))
                error = false;
        } while (error);
        return dato;
    }

    /**
     * Método que permite pedir números con punto flotante al usuario
     *
     * @return num: el número ya captado.
     */
    public static double pedirDouble() {
        boolean doble = false;
        double num = 0;
        while (!doble) {
            System.out.println("Introduce el precio del vehículo: ");
            Scanner sc = new Scanner(System.in);
            try {
                num = sc.nextDouble();
                doble = true;
            } catch (Exception e) {
                System.out.println("El valor ingresado no es correcto, por favor vuelva a intentarlo");
            }
        }
        return num;
    }

    /**
     * Método que permite pedir números al usuario
     *
     * @param nombreDato mensaje que completa el nombre del parametro a completar
     * @return num: el número ya captado.
     */
    public static int pedirNumero(String nombreDato) {
        boolean numero = false;
        int num = 0;
        while (!numero) {
            System.out.print("Introduce " + nombreDato + ": ");
            Scanner sc = new Scanner(System.in);
            try {
                num = sc.nextInt();
                numero = true;

            } catch (Exception e) {
                System.out.println("El dato ingresado no es correcto, por favor vuelva a intentarlo.");
            }
        }
        return num;
    }
}//Fin de clase
