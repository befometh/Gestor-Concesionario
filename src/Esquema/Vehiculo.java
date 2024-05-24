/**
 * @author Cristyan Morales Acevedo
 * @desc Clase vehículo, clase instanciable que albergará la información del vehículo a tratar.
 */

package Esquema;

import java.time.*;

import static Esquema.VehiculoUtil.*;

public class Vehiculo {
    private int cod;
    private String matricula;
    private String marca;
    private int kilometros;
    private LocalDate fecha;
    private String desc;
    private double precio;
    private String nombrePropietario;
    private String dni;
    private static int numVehiculos = 0;

    public Vehiculo(){

    }

    /**
     * Constructor del objeto vehículo
      * @param matricula del vehículo
     * @param marca del vehículo
     * @param kilometros recorridos del vehículo
     * @param fecha de adquisición del vehículo
     * @param desc descripción física del vehículo
     * @param precio del vehículo
     * @param nombrePropietario nombre del propietario del vehículo
     * @param dni del propietario del vehículo
     */
    public Vehiculo(String matricula, String marca, int kilometros, LocalDate fecha, String desc, double precio, String nombrePropietario, String dni) {
        this.cod = numVehiculos;
        this.matricula = matricula;
        this.marca = marca;
        this.kilometros = kilometros;
        this.fecha = fecha;
        this.desc = desc;
        this.precio = precio;
        this.nombrePropietario = nombrePropietario;
        this.dni = dni;
    }

   //Lista de Getters
    public int getCodigo() { return cod; }

    public String getMarca() {
        return marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public int getKilometros() {
        return kilometros;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public String getDni() {
        return dni;
    }

    public static int getNumVehiculos() {
        return numVehiculos;
    }

    //Lista de Setters

    public void setCodigo(int cod) { this.cod = cod; }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setKilometros(int kilometros) {
        this.kilometros = kilometros;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public static void setNumVehiculos(int numVehiculos) {
        Vehiculo.numVehiculos = numVehiculos;
    }

    /**
     * función que permite saber la antigüedad del vehículo
     * @return el tiempo en años cursado.
     */
    public int get_Anios(){
        return LocalDate.now().getYear() - this.fecha.getYear();
    }

    /**
     * Función que permite actualizar los kilómetros del usuario, contacta con Ejerc1Util.VehiculoUtil para realizar la validación
     * de la información. Verifica que el kilómetro a actualizar sea mayor que el actual, y siempre mayor a 0
     * @param nuevo número de kilómetros que va a hacer el cambio
     * @return
     */
    public boolean actualizarKilometros(int nuevo){
        boolean error = true;
        if(validarKilometros(this.kilometros,nuevo)){
            this.kilometros = nuevo;
            error = false;
        }
        return error;
    }
}
