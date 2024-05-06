/**
 * @author Cristyan Morales Acevedo.
 * @desc Clase vehiculoUtil, contiene los validadores de información del sistema
 */

package Esquema; //Paquete contenedor

import java.time.LocalDate; //Requerido para el reconocimiento de fechas y sus diferentes funciones, como el LocalDate.isBefore()
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
     * @return error: verdadero si la fecha es válida y es anterior al día de hoy, falso si no cumple con ambas características
     */
    public static boolean validarFecha(int anio, int mes, int dia) {
        boolean error = true;
        LocalDate fecha; //parametro que va a albergar la fecha reconstruida en formato LocalDate(año, mes, día)
        try {
            fecha = LocalDate.of(anio, mes, dia);
            if (fecha.isBefore(LocalDate.now()))
                error = false;
        } catch (Exception ignored) {
        }
       /*
       Se genera un catch en caso que falle el ingreso de la fecha por valores incorrectos, como error sigue siendo true,
       se ignora dicho catch.
       */
        return error;
    }

    /**
     * Se nos solicita comprobar el nombre de la pesona exclusivamente usando métodos de la clase String, debe contar con al menos un nombre y dos apellidos (dos espacios intermedios) y un tamaño máximo de 40 caracteres
     *
     * @param nombre
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

}
