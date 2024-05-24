/**
 * @author: Cristyan Morales Acevedo
 * @desc Clase Concesionario, gestiona y administra todos los vehículos de un concesionario, es la parte más robusta del programa. Agrega, elimina, lista, busca y modifica los kilómetros de un vehículo.
 */
package Controlador;

import Esquema.Vehiculo;

import static Esquema.VehiculoUtil.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Concesionario {
    private Vehiculo[] lista;

    //Constructores

    /**
     * Constructor que no solicita valores
     */
    public Concesionario() {
        this.lista = new Vehiculo[50];
    } //el arreglo será de 50 espacios por defecto si el usuario no ingresa un tamaño

    /**
     * Sobrecarga del constructor, recibe el tamaño del arreglo
     * @param tamanio tamaño actual
     */
    public Concesionario(int tamanio) {
        this.lista = new Vehiculo[tamanio];
    }//El usuario puede cambiar el tamaño de la memoria principal, eso si, recordadndo que luego no se puede modificar de nuevo (en desuso)

    /**
     * Método que permite crear un vehículo, calcado casi en su totalidad de la unidad 5.
     */
    public void agregarVehiculo() {
        int num = Vehiculo.getNumVehiculos();
        boolean error; //variable que alternará entre verdadero y falso si se produce un error de lectura
        String marca = pedirString("la marca");
        error = true;
        String matricula;
        do {
            matricula = verificarPatron("el número de matrícula, Formato: 1234AAA (Solo en mayúsculas)", "[0-9]{4}[A-Z]{3}", "Formato de matrícula no es correcto, vuelva a intentarlo");
            if(this.buscarVehiculo(matricula)==null)
                error = false;
            else
                System.out.println("La matrícula que intenta ingresar se encuentra asociada a otro vehículo. Vuelva a intentarlo.");
        }while(error);
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
                break;
            }
        } while (error);
        fecha = LocalDate.of(anio, mes, dia);
        String desc = pedirString("la descripción del vehículo");
        double precio = pedirDouble();
        String nombrePropietario;
        do{
            nombrePropietario = pedirString("el nombre del propietario, recuerde que debe tener un nombre y dos apellidos, no puede exceder los 40 caracteres en total (contando espacios)");
            error = verificarNombre(nombrePropietario);
        }while(error);
        String dni = verificarPatron("El número del DNI", "[XxYy0-9][0-9]{7}[A-Za-z]", "El DNI ingresado no es correcto, por favor vuelva a intentarlo");
        lista[num] = new Vehiculo(matricula, marca, kilometros, fecha, desc, precio, nombrePropietario, dni); //asignación de los datos al objeto global: vehiculo
        Vehiculo.setNumVehiculos(num + 1);
    }

    /**
     * Función que se encarga de realizar la busqueda del vehículo por medio de su matrícula
     *
     * @param matricula Recibe la matrícula del vehículo a tratar.
     * @return dato: tipo Vehículo: devuelve el elemento si se encuentra, si falla devuelve null.
     */
    public Vehiculo buscarVehiculo(String matricula) {
        Vehiculo dato;
        int cod = 0;
        int max = Vehiculo.getNumVehiculos();
        while (cod < max && !lista[cod].getMatricula().equals(matricula))
            cod++;
        dato = cod == max ? null : lista[cod];
        return dato;
    }

    /**
     * Función que crea una tabla de los vehículos actualmente disponibles
     *
     * @return El mensaje entero con el vehículo ya construido.
     */
    public String listarVehiculos() {
        StringBuffer mensaje;
        if (Vehiculo.getNumVehiculos() == 0)
            mensaje = new StringBuffer("La lista está vacía en este momento, ingrese al menos un dato para crearla");
        else {
            mensaje = new StringBuffer(
                    "|\tMarca\t|\tMatrícula\t|\tNombre Propietario\t|\tDNI/NIE\t|\tKilometraje\t|\tFecha de Compra\t|\tPrecio\t|\tDescripción\n"
            );
            int tam = mensaje.length();
            mensaje.append("-".repeat(tam));
            mensaje.append("\n");
            for (int i = 0; i < Vehiculo.getNumVehiculos();i++){
                mensaje.append("|\t" + lista[i].getMarca() + "\t|\t");
                mensaje.append(lista[i].getMatricula() + "\t|\t");
                mensaje.append(lista[i].getNombrePropietario() + "\t|\t");
                mensaje.append(lista[i].getDni() + "\t|\t");
                mensaje.append(lista[i].getKilometros() + "\t|\t");
                mensaje.append(lista[i].getFecha().toString() + "\t|\t");
                mensaje.append(lista[i].getPrecio() + "\t|\t");
                mensaje.append(lista[i].getDesc() + "\n");
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
            Vehiculo vehiculo = buscarVehiculo(matricula);
            boolean error = validarKilometros(nuevoKms, vehiculo.getKilometros());
            if (error)
                System.out.println("El kilometraje " + nuevoKms + "kms es menor al actual, solo se permite ingresar un kilometraje más alto.");
            else {
                vehiculo.setKilometros(nuevoKms);
                lista[vehiculo.getCodigo()] = vehiculo;
                System.out.println("Se ha actualizado el kilometraje con éxito.");
            }
        } catch (Exception e) {
            System.out.println("El vehículo al que trata de actualizar el kilometraje no existe.");
        }
    }

    /**
     * Método que se encarga de administrar la función de reconocimiento de patrones y contacta con Ejerc1Util.VehiculoUtil
     *
     * @param nombreDato nombre de la variable a verificar
     * @param patron     el patrón al que debe cumplir, de formato Regex.
     * @param mensaje    mensaje de error que sale al fallo
     * @return patrón ya verificado
     */
    public String verificarPatron(String nombreDato, String patron, String mensaje) {
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
     * Método que permite pedir números al usuario
     *
     * @param nombreDato mensaje que completa el nombre del parametro a completar
     * @return num: el número ya captado.
     */
    public int pedirNumero(String nombreDato) {
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

    /**
     * Función que muestra un solo vehículo, de manera estética.
     * @param vehiculo
     * @return Una cadena con el vehículo buscado.
     */
    public static String mostrarVehiculo(Vehiculo vehiculo){
        StringBuffer dato;
        if(vehiculo != null){
            dato = new StringBuffer(
                    "|\tMarca\t|\tMatrícula\t|\tNombre Propietario\t|\tDNI/NIE\t|\tKilometraje\t|\tFecha de Compra\t|\tPrecio\t|\tDescripción\n"
            );
            int tam = dato.length();
            dato.append("-".repeat(tam) + "\n");
            dato.append("|\t" + vehiculo.getMarca() + "\t|\t");
            dato.append(vehiculo.getMatricula() + "\t|\t");
            dato.append(vehiculo.getNombrePropietario() + "\t|\t");
            dato.append(vehiculo.getDni() + "\t|\t");
            dato.append(vehiculo.getKilometros() + "\t|\t");
            dato.append(vehiculo.getFecha().toString() + "\t|\t");
            dato.append(vehiculo.getPrecio() + "\t|\t");
            dato.append(vehiculo.getDesc() + "\n");
        }
        else{
            dato = new StringBuffer("El vehículo que ha ingresado no existe.");
        }
        return dato.toString();
    }

    /**
     * Método que permite pedir cadenas de caracteres al usuario
     *
     * @param nombreDato dato que completa el nombre del parametro a completar
     * @return la cadena ya recibida.
     */
    public String pedirString(String nombreDato) {
        System.out.print("Introduce " + nombreDato + ": ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Método que permite pedir números con punto flotante al usuario
     *
     * @return num: el número ya captado.
     */
    public double pedirDouble() {
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
     * Función que elimina vehículos y reorganiza los códigos de manera ascendente si uno es eliminado.
     *
     * @param matricula del vehículo a eliminar, si no se encuentra, arroja error al usuario.
     */
    public void eliminarVehiculo(String matricula) {
        int cod, max = Vehiculo.getNumVehiculos();
        Vehiculo[] temp;
        try {
            cod = buscarVehiculo(matricula).getCodigo();
            if(max>1){
                temp = new Vehiculo[max - 1];
                if (cod == 0) { //Comprueba si el vehículo a eliminar es el primero del arreglo
                    for (int i = 1; i < max; i++) {
                        temp[i - 1] = lista[i];
                        temp[i - 1].setCodigo(i - 1);
                    }
                } else if (cod > 0 && cod < max - 1) { //Comprueba si el vehículo a eliminar esta en el medio del arreglo
                    System.arraycopy(lista, 0, temp, 0, cod);
                    for (int i = cod; i < max; i++) {
                        temp[i] = lista[i + 1];
                        temp[i].setCodigo(i);
                    }
                }
                if (temp[0] != null) // si temp[0] es null quiere decir que el elemento a eliminar es el último, así que la lista no es necesario editarse
                    System.arraycopy(temp, 0, lista, 0, max - 1);
            }
            lista[max - 1] = null;
            Vehiculo.setNumVehiculos(max - 1);
            System.out.println("Se ha elimintado el dato exitosamente");
        } catch (Exception e) { //Se produce cuando trata de accederse a un
            System.out.println("El vehículo que ha solicitado eliminar no existe");
        }
    }
}
