package org.kasador.realestatecompany.view;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.spotobjects.Machine;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;
import org.kasador.realestatecompany.domain.spotobjects.Stuff;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.ParingSpotService;
import org.kasador.realestatecompany.service.PersonService;
import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.tools.Restorer;
import org.kasador.realestatecompany.tools.Saver;
import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        sc = new Scanner(System.in);
        System.out.println("1.Data filler\n2.Information about the complex\n3.Log in\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                dataFillerMenu();
                break;
            case 2:
                break;
            case 3:
                login();
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Incorrect menu item");
                start();
        }
    }

    private void login() {
        System.out.println("Choose user 0.Exit:");
        showPersonList();
        int i = sc.nextInt();
        if (i < 0 || i > personPool.getAll().size() - 1) {
            System.out.println("Wrong person id");
            login();
        } else if (i == 0) {
            exit();
        }
        personPage(personPool.getAll().get(i - 1));
    }

    private void personPage(Person person) {
        System.out.println("Choose: \n1.Info \n2.Rent Area \n3. Apartments \n 4.Parking Spots \n5.Letters \n6. \n0.Exit");

    }

    private void showPersonInfo(Person person) {
        System.out.println("name: " + person.getName() + " " + person.getSurname());
        System.out.println("birthday: " + person.getBirthday());
        System.out.println("address: " + person.getAddress());
        System.out.println("apartments:");
        if (person.getApartments().isEmpty() || person.getApartments().size() == 0) {
            System.out.println("- none.");
        } else {
            List<Apartment> apartments = person.getApartments();

            for (Apartment apartment : apartments) {
                System.out.println("*id: " + apartment.getId());
                System.out.println(" rent start date: " + apartment.getRentStartDate());
                System.out.println(" rent end date: " + apartment.getRentEndDate());
                System.out.println(" space used: " + apartment.getUsableSpace() + "/" + apartment.getUsedSpace());
                System.out.println(" dwellers:");
                if (apartment.getDwellers().isEmpty() || apartment.getDwellers().size() == 0) {
                    System.out.println("  - none.");
                } else {
                    for (int j = 0; j < apartment.getDwellers().size(); j++) {
                        var dweller = apartment.getDwellers().get(j);
                        System.out.println("  id: " + dweller.getId() + " name: " + dweller.getName() + " " + dweller.getSurname());
                    }
                }
            }
        }
        if (person.getParkingSpot().isEmpty() || person.getParkingSpot().size() == 0) {
            System.out.println("- none.");
        } else {
            var parkingSpots = person.getParkingSpot();

            for (ParkingSpot parkSpot : parkingSpots) {
                System.out.println("*id: " + parkSpot.getId());
                System.out.println(" rent start date: " + parkSpot.getRentStartDate());
                System.out.println(" rent end date: " + parkSpot.getRentEndDate());
                System.out.println(" space used: " + parkSpot.getUsableSpace() + "/" + parkSpot.getUsedSpace());
                System.out.println(" spot stuff and vehicles:");
                var parkingSpotObjects = parkSpot.getParkingSpotObject();

                for (ParkingSpotObject parkingSpotObject : parkingSpotObjects) {

                    if (parkingSpotObject instanceof Stuff) {
                        Stuff stuff = (Stuff) parkingSpotObject;
                        System.out.println("  -id: " + stuff.getId());
                        System.out.println("  -name: " + stuff.getName());
                        System.out.println("  -space occupation: " + stuff.getSpaceOccupation());
                    } else if (parkingSpotObject instanceof Machine) {
                        Machine machine = (Machine) parkingSpotObject;
                        System.out.println("  -id: " + machine.getId());
                        System.out.println("  -name: " + machine.getName());
                        System.out.println("  -machine type: " + machine.getMachineType());
                        System.out.println("  -engine capacity: " + machine.getEngineCapacity());
                        System.out.println("  -engine type: " + machine.getEngineType());
                        System.out.println("  -weels: " + machine.getWeels());
                        System.out.println("  -space occupation" + machine.getSpaceOccupation());
                    }

                    System.out.println();
                }
                System.out.println();
            }
        }
        System.out.println("letters: " + person.getLetters().size());

    }


    private void showPersonList() {
        var persons = personPool.getAll();
        for (int i = 0; i < persons.size(); i++) {
            var person = persons.get(i);
            System.out.println(i + 1 + ". " + person.getName() + " " + person.getSurname());

        }
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
        person.setName(sc.next());
        System.out.println("Input surname:");
        person.setSurname(sc.next());
        System.out.println("Input birthday **/**/**** format:");
        person.setBirthday(LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        personPool.add(person);
        successfulSaveMessage(person);
        dataFillerMenu();
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
