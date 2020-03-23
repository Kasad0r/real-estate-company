package org.kasador.realestatecompany.view;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.RentArea;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.ParingSpotService;
import org.kasador.realestatecompany.service.PersonService;
import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.tools.Restorer;
import org.kasador.realestatecompany.tools.Saver;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class View {
    private PersonPool personPool;
    private RentAreaPool rentAreaPool;
    private Saver saver;
    private Restorer restorer;
    private ParingSpotService paringSpotService;
    private PersonService personService;
    private RentAreaService rentAreaService;
    private Scanner sc;

    public View(PersonPool personPool,
                RentAreaPool rentAreaPool,
                Saver saver,
                Restorer restorer,
                ParingSpotService paringSpotService,
                PersonService personService,
                RentAreaService rentAreaService) {
        this.personPool = personPool;
        this.rentAreaPool = rentAreaPool;
        this.saver = saver;
        this.restorer = restorer;
        this.paringSpotService = paringSpotService;
        this.personService = personService;
        this.rentAreaService = rentAreaService;
        sc = new Scanner(System.in);
    }

    public void start() {

        System.out.println("1.Data filler\n2.Information about the complex\n3.Log in\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                dataFillerMenu();
                break;
            case 2:
                break;
            case 3:
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Incorrect menu item");
                start();
        }
    }

    public static void choseMessage() {
        System.out.println("Choose menu item");
    }

    private void dataFillerMenu() {
        System.out.println("What to fill?\n1.Apartment\n2.Parking Place\n3.Person\n4.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                fillApartment();
                break;
            case 2:
                fillParkingSpot();
                break;
            case 3:
                fillPerson();
                break;
            case 4:
                start();
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Incorrect menu item");
                dataFillerMenu();
        }
    }
    

    private void fillPerson() {
        Person person = new Person();
        System.out.println("Input name:");
        person.setName(sc.nextLine());
        System.out.println("Input surname");
        person.setSurname(sc.nextLine());
        System.out.println("Input birthday **/**/**** format");
        person.setBirthday(LocalDate.parse(sc.nextLine()));
        personPool.add(person);
        successfulSaveMessage(person);
    }

    private void fillApartment() {
        System.out.println("Area calculation:\n1.Set area\n2.Calculate by width,length,height\n3.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 0:
                exit();
                break;
            case 1:
                System.out.println("Input area:");
                var apartWithArea = new Apartment(null, null, sc.nextDouble());
                rentAreaPool.add(apartWithArea);
                successfulSaveMessage(apartWithArea);
                break;
            case 2:
                System.out.println("Input length,width, height:");
                var apartCalculation = new Apartment(null, null, sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                successfulSaveMessage(apartCalculation);
                rentAreaPool.add(apartCalculation);
                break;
            case 3:
                dataFillerMenu();
                break;
            default:
                System.out.println("Incorrect menu item");
                fillApartment();
        }
        fillApartment();
    }

    private void fillParkingSpot() {
        System.out.println("Area calculation:\n1.Set area\n2.Calculate by width,length,height\n3.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 0:
                exit();
                break;
            case 1:
                System.out.println("Input area:");
                var parkingSpot = new ParkingSpot(null, null, sc.nextDouble());
                rentAreaPool.add(parkingSpot);
                successfulSaveMessage(parkingSpot);
                break;
            case 2:
                System.out.println("Input length,width, height:");
                var parkingSpotCalc = new ParkingSpot(null, null, sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                successfulSaveMessage(parkingSpotCalc);
                rentAreaPool.add(parkingSpotCalc);
                break;
            case 3:
                dataFillerMenu();
                break;
            default:
                System.out.println("Incorrect menu item");
                fillApartment();
        }
        fillApartment();
    }

    private void successfulSaveMessage(Apartment apartment) {
        System.out.println("Apartment with id:" + apartment.getId() + "was saved successful to pool!");
    }

    private void successfulSaveMessage(ParkingSpot parkingSpot) {
        System.out.println("Parking spot with id:" + parkingSpot.getId() + "was saved successful to pool!");
    }

    private void successfulSaveMessage(Person person) {
        System.out.println(
                "Person " + person.getName() +
                        " " + person.getSurname() +
                        " with id:" + person.getId() +
                        "was saved successful to pool!");
    }

    private void exit() {
        saver.saveAllData();
        System.exit(0);
    }
}
