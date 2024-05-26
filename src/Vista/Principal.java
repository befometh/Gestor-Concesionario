/**
 * @author Cristyan Morales Acevedo
 * @desc Clase principal, contiene el main() y se encarga de comunicarse con el usuario
 */
package Vista;

import Controlador.Concesionario;

import static Esquema.VehiculoUtil.*;

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
                        cabecero();
                        System.out.println(oficina.listarVehiculos());
                        break;
                    case 3:
                        dato = ingresarMatricula("a buscar");
                        cabecero();
                        System.out.println(oficina.buscarVehiculo(dato));
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
            separador();
        } while (op != 6||error);
    }//Fin del main
    static void separador(){
        System.out.println("-".repeat(129));
    }

    static void cabecero(){
        separador();
        System.out.println("|\tMarca\t|\tMatrícula\t|\tNombre Propietario\t|\tDNI/NIE\t|\tKilometraje\t|\tFecha de Compra\t|\tPrecio\t|\tDescripción\t|");
        separador();
    }
}
