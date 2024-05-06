/**
 * @author Cristyan Morales Acevedo
 * @desc Clase principal, contiene el main() y se encarga de comunicarse con el usuario
 */
package Vista;

import Interfaz.Concesionario;

import static Esquema.VehiculoUtil.validador;
import static Interfaz.Concesionario.mostrarVehiculo;

import java.util.Scanner;

/**
 *
 */
public class Principal {

    static Concesionario oficina = new Concesionario();

    public static void main(String[] args) {

        int op=6;
        boolean error = true;
        Scanner teclado;
        do {
            teclado = new Scanner(System.in);
            try{
                System.out.println("""
                        Bienvenido/a al programa de edición de vehículos.
                        Por favor elija una opción:
                        1. Nuevo Vehículo.
                        2. Listar Vehiculos.
                        3. Buscar Vehiculo.
                        4. Modificar kms Vehículo.
                        5. Eliminar Vehiculo.
                        6. Salir.
                        Elija una opción:""");
                op = teclado.nextInt();
                String dato;
                switch (op) {
                    case 1:
                        System.out.println("Ha seleccionado agregar un vehículo:");
                        oficina.agregarVehiculo();
                        break;
                    case 2:
                        System.out.println(oficina.listarVehiculos());
                        break;
                    case 3:
                        dato = ingresarMatricula("a buscar");
                        System.out.println(mostrarVehiculo(oficina.buscarVehiculo(dato)));
                        break;
                    case 4:
                        dato = ingresarMatricula("al que va a modificar el kilometraje");
                        System.out.println("Ahora ingrese la cantidad de kilómetros a modificar");
                        try {
                            int km = teclado.nextInt();
                            oficina.actualizarKms(km, dato);
                        } catch (Exception e) {
                            System.out.println("ha ingresado un dato no válido, vuelva a intentarlo.");
                        }
                        break;
                    case 5:
                        dato = ingresarMatricula("del vehículo a eliminar");
                        oficina.eliminarVehiculo(dato);
                        break;
                    case 6:
                        error = false;
                        break;
                }

            }catch(Exception e){
                System.out.println("La opción que está ingresando no es válida, vuelva a intentarlo");
            }
        } while (op != 6||error);
    }//Fin del main

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
}
