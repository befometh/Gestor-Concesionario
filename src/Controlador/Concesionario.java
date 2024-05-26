/**
 * @author: Cristyan Morales Acevedo
 * @desc Clase Concesionario, gestiona y administra todos los vehículos de un concesionario, es la parte más robusta del programa. Agrega, elimina, lista, busca y modifica los kilómetros de un vehículo.
 */
package Controlador;

import Esquema.Vehiculo;

import static Esquema.VehiculoUtil.*;

import java.time.LocalDate;
import java.util.TreeMap;

public class Concesionario {
    private final TreeMap<String, Vehiculo> lista = new TreeMap<>();

    /**
     * Método que permite crear un vehículo, calcado casi en su totalidad de la unidad 5.
     */
    public void agregarVehiculo() {
        boolean error; //variable que alternará entre verdadero y falso si se produce un error de lectura
        String marca = pedirString("la marca");
        error = true;
        String matricula;
        do {
            matricula = verificarPatron("el número de matrícula, Formato: 1234AAA (Solo en mayúsculas)", "[0-9]{4}[A-Z]{3}", "Formato de matrícula no es correcto, vuelva a intentarlo");
            if (lista.containsKey(matricula))
                System.out.println("La matrícula que intenta ingresar se encuentra asociada a otro vehículo. Vuelva a intentarlo.");
            else
                error = false;
        } while (error);
        int kilometros; //recibirá los kilómetros que harán parte del objeto vehículo
        do {
            kilometros = pedirNumero("el número de kilómetros");
            error = validarKilometros(kilometros, 0);
            if (error) {
                System.out.println("El valor ingresado es incorrecto, vuelva a intentarlo");
            }
        } while (error);
        int anio, mes, dia; //recibirá el año, mes y día del objeto fecha
        LocalDate fecha; // recibirá la fecha que hará parte del objeto vehículo
        do {
            anio = pedirNumero("el año de matriculación");
            mes = pedirNumero("el mes de matriculación, (en números)");
            dia = pedirNumero("el dia de matriculación");

            error = validarFecha(anio, mes, dia);
            if (error) {
                System.out.println("La fecha que ingresó no es valida, intentelo de nuevo");
            }
        } while (error);
        fecha = LocalDate.of(anio, mes, dia);
        String desc = pedirString("la descripción del vehículo");
        double precio = pedirDouble();
        String nombrePropietario;
        do {
            nombrePropietario = pedirString("el nombre del propietario, recuerde que debe tener un nombre y dos apellidos, no puede exceder los 40 caracteres en total (contando espacios)");
            error = verificarNombre(nombrePropietario);
        } while (error);
        String dni = verificarPatron("El número del DNI", "[XxYy0-9][0-9]{7}[A-Za-z]", "El DNI ingresado no es correcto, por favor vuelva a intentarlo");
        Vehiculo dato = new Vehiculo(matricula, marca, kilometros, fecha, desc, precio, nombrePropietario, dni); //asignación de los datos al objeto global: vehiculo
        lista.put(matricula, dato);
    }

    /**
     * Función que crea una tabla de los vehículos actualmente disponibles
     *
     * @return El mensaje entero con el vehículo ya construido.
     */
    public String listarVehiculos() {
        StringBuilder mensaje;
        int contador = 0;
        if (lista.isEmpty())
            mensaje = new StringBuilder("La lista está vacía en este momento, ingrese al menos un dato para crearla");
        else {
            mensaje = new StringBuilder();
            for (Vehiculo dato : lista.values()) {
                contador ++;
                mensaje.append(buscarVehiculo(dato.getMatricula()));
                if(contador != lista.size())
                    mensaje.append("\n");
            }
        }
        return mensaje.toString();
    }

    /**
     * Función que actualiza el kilometraje del vehículo siempre y cuando:
     * -El kilometraje esté bien escrito
     * -El kilometraje nuevo sea menor que el actual
     *
     * @param nuevoKms  nuevo valor que quiere ingresar el usuario
     * @param matricula parámetro que permite saber el código de ingreso del usuario, (resultado del método buscarVehiculo())
     */
    public void actualizarKms(int nuevoKms, String matricula) {
        try {
            Vehiculo vehiculo = lista.get(matricula);
            boolean error = validarKilometros(nuevoKms, vehiculo.getKilometros());
            if (error)
                System.out.println("El kilometraje " + nuevoKms + "kms es menor al actual, solo se permite ingresar un kilometraje más alto.");
            else {
                vehiculo.setKilometros(nuevoKms);
                lista.put(matricula, vehiculo);
                System.out.println("Se ha actualizado el kilometraje con éxito.");
            }
        } catch (Exception e) {
            System.out.println("El vehículo al que trata de actualizar el kilometraje no existe.");
        }
    }

    /**
     * Función que muestra un solo vehículo, de manera estética.
     *
     * @param matricula la matrícula a buscar
     * @return Una cadena con el vehículo buscado.
     */
    public String buscarVehiculo(String matricula) {
        StringBuilder dato;
        if (lista.containsKey(matricula)) {
            Vehiculo vehiculo = lista.get(matricula);
            dato = new StringBuilder();
            dato.append("|\t").append(vehiculo.getMarca()).append("\t|\t");
            dato.append(vehiculo.getMatricula()).append("\t|\t");
            dato.append(vehiculo.getNombrePropietario()).append("\t|\t");
            dato.append(vehiculo.getDni()).append("\t|\t");
            dato.append(vehiculo.getKilometros()).append("\t|\t");
            dato.append(vehiculo.getFecha().toString()).append("\t|\t");
            dato.append(vehiculo.getPrecio()).append("\t|\t");
            dato.append(vehiculo.getDesc()).append("\t|");

        } else {
            dato = new StringBuilder("El vehículo que ha ingresado no existe.");
        }
        return dato.toString();
    }

    /**
     * Función que elimina vehículos y reorganiza los códigos de manera ascendente si uno es eliminado.
     *
     * @param matricula del vehículo a eliminar, si no se encuentra, arroja error al usuario.
     */
    public void eliminarVehiculo(String matricula) {
        if (lista.containsKey(matricula)) {
            System.out.println("A continuación va a eliminar el vehículo: ");
            System.out.println(buscarVehiculo(matricula));
            System.out.println("¿Está seguro de querer eliminarlo? \n1.Si\t2.No");
            int opcion = pedirNumero("opción: ");
            if (opcion == 1) {
                lista.remove(matricula);
                System.out.println("El vehículo ha sido eliminado de la base de datos.");
            }
        }
    }
}
