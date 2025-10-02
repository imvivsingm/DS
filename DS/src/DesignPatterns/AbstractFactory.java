package DesignPatterns;
interface Car{
      void drive();
}
interface Bike{
    void drive();
}
class ElectricCar implements Car{
    @Override
    public void drive() {
        System.out.println("ElectricCar drive");
    }
}
class ElectricBike implements Bike{
    @Override
    public void drive() {
        System.out.println("ElectricBike drive");
    }
}
class GasCar implements Car{
    @Override
    public void drive() {
        System.out.println("GasCar drive");
    }
}
class GasBike implements Bike{
    @Override
    public void drive() {
        System.out.println("GasBike drive");
    }
}

interface VehicleFactory{
    Car createCar();
    Bike createBike();
}

// Create Electric vehicles
class ElectricVehicle implements VehicleFactory{
    @Override
    public Car createCar() {
        return new ElectricCar();
    }
    @Override
    public Bike createBike() {
        return new ElectricBike();
    }
}
class GasVehicle implements VehicleFactory{
    @Override
    public Car createCar() {
        return new GasCar();
    }
    @Override
    public Bike createBike() {
        return new GasBike();
    }
}
class Client{
    private final Car car;
    private final Bike bike;
    VehicleFactory vehicleFactory;
    public Client(VehicleFactory vehicleFactory){
        car = new ElectricCar();
        bike = new ElectricBike();
    }
    public void drive(){
        car.drive();
        bike.drive();
    }
}
public class AbstractFactory {

    public static void main(String[] args) {
        Client client = new Client(new ElectricVehicle());
        client.drive();
    }
}
