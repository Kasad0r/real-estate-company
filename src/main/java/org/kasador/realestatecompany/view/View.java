package org.kasador.realestatecompany.view;

import org.kasador.realestatecompany.domain.Apartment;
import org.kasador.realestatecompany.domain.ParkingSpot;
import org.kasador.realestatecompany.domain.Person;
import org.kasador.realestatecompany.domain.spotobjects.Machine;
import org.kasador.realestatecompany.domain.spotobjects.ParkingSpotObject;
import org.kasador.realestatecompany.domain.spotobjects.Stuff;
import org.kasador.realestatecompany.exception.PoolException;
import org.kasador.realestatecompany.exception.ProblematicTenantException;
import org.kasador.realestatecompany.exception.TooManyLettersException;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.ParingSpotService;
import org.kasador.realestatecompany.service.PersonService;
import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.tools.Saver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class View {
    private PersonPool personPool;
    private RentAreaPool rentAreaPool;
    private Saver saver;
    private ParingSpotService paringSpotService;
    private PersonService personService;
    private RentAreaService rentAreaService;
    private Scanner sc;

    public View(PersonPool personPool,
                RentAreaPool rentAreaPool,
                Saver saver,
                ParingSpotService paringSpotService,
                PersonService personService,
                RentAreaService rentAreaService) {
        this.personPool = personPool;
        this.rentAreaPool = rentAreaPool;
        this.saver = saver;
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
                System.out.println("Soon...");
                start();
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
        personPage(choosePersonFromList());
    }

    private Person choosePersonFromList() {
        System.out.println("Choose person 0.Exit:");
        var persons = personPool.getAll();
        for (int i = 0; i < persons.size(); i++) {
            var person = persons.get(i);
            System.out.println(i + 1 + ". " + person.getName() + " " + person.getSurname());

        }
        int i = sc.nextInt();
        if (i == 0) {
            exit();
        }
        if (i < 0 || i > personPool.getAll().size()) {
            System.out.println("Wrong person id");
            choosePersonFromList();
        }
        return personPool.getAll().get(i - 1);
    }

    private void personPage(Person person) {
        System.out.println("Choose: \n1.Info \n2.Rent Area \n3.Apartments \n4.Parking Spots \n5.Letters \n6.Back \n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                showPersonInfo(person);
                break;
            case 2:
                personAreaRenting(person);
                break;
            case 3:
                chooseApartmentsForControlPanel(person);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                start();
                break;
            case 0:
                exit();
                break;
            default:
                personPage(person);

        }
    }

    private void chooseApartmentsForControlPanel(Person person) {
        System.out.println("Choose apartments: (0.Back)");
        showPersonApartmentList(person.getApartments(), true);
        int choice = sc.nextInt();
        if (choice == 0) {
            personPage(person);
        }
        if (choice < 0 || choice > person.getApartments().size()) {
            System.out.println("Cant find this apartments");
            chooseApartmentsForControlPanel(person);
        }
        apartmentController(person.getApartments().get(choice - 1));
    }

    private void apartmentController(Apartment a) {
        var tenant = a.getTenant();
        System.out.println("1.Add a dweller \n2.Remove dweller \n3.Pay rent.\n4.Evict\n5.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("Add dweller:");
                Person dwellerToAdd = choosePersonFromList();
                rentAreaService.addDweller(a, dwellerToAdd);
                System.out.println(dwellerToAdd.getName() + " " + dwellerToAdd.getSurname() + " added to apartment with id:" + a.getId());
                apartmentController(a);
                break;
            case 2:
                System.out.println("Remove dweller:");
                Person dwellerToRemove = choosePersonFromList();
                rentAreaService.removeDweller(a, dwellerToRemove);
                System.out.println(dwellerToRemove.getName() + " " + dwellerToRemove.getSurname() + " removed from apartment with id:" + a.getId());
                apartmentController(a);
                break;
            case 3:
                System.out.println("Input credit card data:");
                sc.next();// LOL
                rentAreaService.extendRent(a, 30);
                System.out.println("Payment done!");
                personPage(tenant);
                break;
            case 4:
                System.out.println("Are you sure? ( Yes/No)");
                if ("yes".equals(sc.next().toLowerCase())) {
                    rentAreaService.evictRentArea(a);
                    System.out.println("You left the apartment with id:" + a.getId());
                }
                chooseApartmentsForControlPanel(tenant);
                break;
            case 5:
                chooseApartmentsForControlPanel(tenant);
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Wrong argument");
                apartmentController(a);
        }
    }


    private void parkingSpotController(ParkingSpot parkingSpot) {
        var tenant = parkingSpot.getTenant();
        System.out.println("1.Add item or vehicle\n2.Remove item or vehicle\n3.Pay rent.\n4.Evict\n5.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                System.out.println("Add item or vehicle:");

                break;
            case 2:
                System.out.println("Remove item or vehicle:");

                break;
            case 3:
                System.out.println("Pay rent, input credit card data:");
                sc.next();// LOL
                rentAreaService.extendRent(parkingSpot, 30);
                System.out.println("Payment done!");
                personPage(tenant);
                break;
            case 4:
                System.out.println("Evict. Are you sure? ( Yes/No)");
                if ("yes".equals(sc.next().toLowerCase())) {
                    rentAreaService.evictRentArea(parkingSpot);
                    System.out.println("you stopped renting a parking space with id:" + parkingSpot.getId());
                }
                chooseApartmentsForControlPanel(tenant);
                break;
            case 5:
                chooseApartmentsForControlPanel(tenant);
                break;
            case 0:
                exit();
                break;
            default:
                System.out.println("Wrong argument");
                parkingSpotController(parkingSpot);
        }
    }

    public void personAreaRenting(Person person) {
        System.out.println("Choose Area to rent: \n1.Apartments \n2.Parking Spot \n3.Back \n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                showApartmentsRenting(person);
                break;
            case 2:
                showParkingSpotRenting(person);
                break;
            case 3:
                personPage(person);
                break;
            case 0:
                exit();
                break;
        }

    }

    public void showParkingSpotRenting(Person person) {
        System.out.println("Choose parking spot: 0.Back");
        var freeParkSpots = rentAreaPool.getAllFreeParkingSpots();
        freeParkSpots.sort((Comparator.comparing(ParkingSpot::getUsableSpace)));
        for (int i = 0; i < freeParkSpots.size(); i++)
            System.out.println((i + 1) + ". usable space: " + freeParkSpots.get(i).getUsableSpace() + ", cost: " + new Random().nextInt(500) + "$");

        int choice = sc.nextInt();
        if (choice == 0) {
            personAreaRenting(person);
            return;
        }
        if (choice < 0 || choice > freeParkSpots.size()) {
            showParkingSpotRenting(person);
        }

        ParkingSpot parkingSpot = freeParkSpots.get(choice - 1);
        try {
            personService.addRentArea(person, parkingSpot);
            System.out.println("You successful rent a parking spot.");
            personPage(person);
        } catch (ProblematicTenantException e) {
            System.err.println("You can`t rent parking spot without apartments.");
            personPage(person);
        } catch (PoolException e) {
            System.err.println("Couldn't rent more 5 areas");
            personPage(person);
        } catch (TooManyLettersException e) {
            System.err.println("You have more that 3 letters of warning. Pay for a rent your already renting areas.");
            personPage(person);
        }
    }

    public void showApartmentsRenting(Person person) {
        System.out.println("Choose apartments: 0.Back");
        List<Apartment> allFreeApartments = rentAreaPool.getAllFreeApartments();
        allFreeApartments.sort((Comparator.comparing(Apartment::getUsableSpace)));
        for (int i = 0; i < allFreeApartments.size(); i++) {
            System.out.println((i + 1) + ". usable space: " + allFreeApartments.get(i).getUsableSpace() + ", cost: " + new Random().nextInt(1000) + "$");
        }
        int choice = sc.nextInt();
        if (choice == 0) {
            personAreaRenting(person);
            return;
        }
        if (choice < 0 || choice > allFreeApartments.size()) {
            showApartmentsRenting(person);
        }

        Apartment apartment = allFreeApartments.get(choice - 1);
        try {
            personService.addRentArea(person, apartment);
            System.out.println("You successful rent an apartment.");
            personPage(person);
        } catch (ProblematicTenantException e) {
            personPage(person);
            /* May not be, because this throws when you try rent parking spot without apartments */
        } catch (PoolException e) {
            System.err.println("Couldn't rent more 5 areas");
            personAreaRenting(person);
        } catch (TooManyLettersException e) {
            System.out.println("You have more that 3 letters of warning. Pay for a rent your already renting areas.");
            personPage(person);
        }
    }

    private void showPersonInfo(Person person) {
        System.out.println("---------------------------------------");
        System.out.println("name: " + person.getName() + " " + person.getSurname());
        System.out.println("birthday: " + person.getBirthday());
        System.out.println("address: " + person.getAddress());
        System.out.println("apartments:");
        if (person.getApartments().isEmpty() || person.getApartments().size() == 0) {
            System.out.println("- none.");
        } else {
            showPersonApartmentList(person.getApartments(), false);
        }
        System.out.println("parking spots:");
        if (person.getParkingSpot().isEmpty() || person.getParkingSpot().size() == 0) {
            System.out.println("- none.");
        } else {
            showPersonParkingSpotsList(person.getParkingSpot(), false);
        }
        System.out.println("letters: " + person.getLetters().size());
        System.out.println("---------------------------------------");
        personPage(person);
    }

    private void showPersonParkingSpotsList(List<ParkingSpot> parkingSpots, boolean indexPresenter) {
        for (int i = 0; i < parkingSpots.size(); i++) {
            ParkingSpot parkSpot = parkingSpots.get(i);
            if (indexPresenter)
                System.out.println(i + ".");
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

    private void showPersonApartmentList(List<Apartment> apartments, boolean indexShower) {
        for (int i = 0; i < apartments.size(); i++) {
            if (indexShower)
                System.out.println(i + 1 + ".");
            Apartment apartment = apartments.get(i);
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

    private void dataFillerMenu() {
        System.out.println("What to fill?\n1.Apartment\n2.Parking Place\n3.Person\n4.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                fillApartment();
                break;
            case 2:
                try {
                    fillParkingSpot();
                } catch (PoolException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    fillPerson();
                } catch (PoolException e) {
                    e.printStackTrace();
                }
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


    private void fillPerson() throws PoolException {
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
                try {
                    rentAreaPool.add(apartWithArea);
                } catch (PoolException e) {
                    e.printStackTrace();
                }
                successfulSaveMessage(apartWithArea);
                break;
            case 2:
                System.out.println("Input length,width, height:");
                var apartCalculation = new Apartment(null, null, sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                successfulSaveMessage(apartCalculation);
                try {
                    rentAreaPool.add(apartCalculation);
                } catch (PoolException e) {
                    e.printStackTrace();
                }
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

    private void fillParkingSpot() throws PoolException {
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
        fillParkingSpot();
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
