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
import org.kasador.realestatecompany.exception.TooManyThingsException;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.ParingSpotService;
import org.kasador.realestatecompany.service.PersonService;
import org.kasador.realestatecompany.service.RentAreaService;
import org.kasador.realestatecompany.tools.MockItemAndVehicleGenerator;
import org.kasador.realestatecompany.tools.Saver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.*;

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
        sc = new Scanner(in);
    }

    public void start() {
        sc = new Scanner(in);
        out.println("1.Data filler\n2.Information about the complex\n3.Log in\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                dataFillerMenu();
                break;
            case 2:
                out.println("Soon...");
                start();
                break;
            case 3:
                login();
                break;
            case 0:
                exit();
                break;
            default:
                out.println("Incorrect menu item");
                start();
        }
    }

    private void login() {
        personPage(choosePersonFromList());
    }

    private Person choosePersonFromList() {
        out.println("Choose person 0.Exit:");
        var persons = personPool.getAll();
        for (int i = 0; i < persons.size(); i++) {
            var person = persons.get(i);
            out.println(i + 1 + ". " + person.getName() + " " + person.getSurname());

        }
        int i = sc.nextInt();
        if (i == 0) {
            exit();
        }
        if (i < 0 || i > personPool.getAll().size()) {
            out.println("Wrong person id");
            choosePersonFromList();
        }
        return personPool.getAll().get(i - 1);
    }

    private void personPage(Person person) {
        out.println("Choose: \n1.Info \n2.Rent Area \n3.Apartments \n4.Parking Spots \n5.Letters \n6.Back \n0.Exit");
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
                chooseParkingSpotForControlPanel(person);
                break;
            case 5:
                try {
                    showAllLetters(person);
                } catch (IOException e) {
                    out.println("Something went wrong");
                    personPage(person);
                }
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

    private void showAllLetters(Person person) throws IOException {
        var letters = person.getLetters();
        if (!letters.isEmpty()) {
            for (var letter : letters) {
                out.println("letter name: " + letter.getName().replace(".txt", "") + " text: ");
                for (String s : Files.readAllLines(letter.toPath())) {
                    out.println("    \t" + s);
                }
                personPage(person);
            }
        } else {
            out.println("You have no letters.");
            personPage(person);
        }
    }

    private void chooseParkingSpotForControlPanel(Person person) {
        out.println("Choose parking spot: (0.Back)");
        showPersonParkingSpotsList(person.getParkingSpot(), true);
        int choice = sc.nextInt();
        if (choice == 0) {
            personPage(person);
        }
        if (choice < 0 || choice > person.getParkingSpot().size()) {
            out.println("Cant find this apartments");
            chooseParkingSpotForControlPanel(person);
        }
        parkingSpotController(person.getParkingSpot().get(choice - 1));
    }

    private void chooseApartmentsForControlPanel(Person person) {
        out.println("Choose apartments: (0.Back)");
        showPersonApartmentList(person.getApartments(), true);
        int choice = sc.nextInt();
        if (choice == 0) {
            personPage(person);
        }
        if (choice < 0 || choice > person.getApartments().size()) {
            out.println("Cant find this apartments");
            chooseApartmentsForControlPanel(person);
        }
        apartmentController(person.getApartments().get(choice - 1));
    }

    private void apartmentController(Apartment a) {
        var tenant = a.getTenant();
        out.println("1.Add a dweller \n2.Remove dweller \n3.Pay rent.\n4.Evict\n5.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                out.println("Add dweller:");
                Person dwellerToAdd = choosePersonFromList();
                rentAreaService.addDweller(a, dwellerToAdd);
                out.println(dwellerToAdd.getName() + " " + dwellerToAdd.getSurname() + " added to apartment with id:" + a.getId());
                apartmentController(a);
                break;
            case 2:
                out.println("Remove dweller:");
                var dwellerToRemove = choosePersonFromList();
                rentAreaService.removeDweller(a, dwellerToRemove);
                out.println(dwellerToRemove.getName() + " " + dwellerToRemove.getSurname() + " removed from apartment with id:" + a.getId());
                apartmentController(a);
                break;
            case 3:
                out.println("Input credit card data:");
                sc.next();// LOL
                rentAreaService.extendRent(a, 30);
                out.println("Payment done!");
                personPage(tenant);
                break;
            case 4:
                out.println("Are you sure? ( Yes/No)");
                if ("yes".equals(sc.next().toLowerCase())) {
                    rentAreaService.evictRentArea(a);
                    out.println("You left the apartment with id:" + a.getId());
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
                out.println("Wrong argument");
                apartmentController(a);
        }
    }


    private void parkingSpotController(ParkingSpot parkingSpot) {
        var tenant = parkingSpot.getTenant();
        out.println("1.Add item or vehicle\n2.Remove item or vehicle\n3.Pay rent.\n4.Evict\n5.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 1:
                out.println("Add 1.item or 2.vehicle:");
                int addChoice = sc.nextInt();
                if (addChoice == 2) {
                    addMachineToParkingSpot(parkingSpot);
                } else if (addChoice == 1) {
                    addStuffToParkingSpot(parkingSpot);

                } else {
                    parkingSpotController(parkingSpot);
                }
                break;
            case 2:
                removeObjectFromParkingSpot(parkingSpot);
                break;
            case 3:
                out.println("Pay rent, input credit card data:");
                sc.next();// LOL
                rentAreaService.extendRent(parkingSpot, 30);
                out.println("Payment done!");
                personPage(tenant);
                break;
            case 4:
                out.println("Evict. Are you sure? ( Yes/No)");
                if ("yes".equals(sc.next().toLowerCase())) {
                    rentAreaService.evictRentArea(parkingSpot);
                    out.println("you stopped renting a parking space with id:" + parkingSpot.getId());
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
                out.println("Wrong argument");
                parkingSpotController(parkingSpot);
        }
    }

    private void addMachineToParkingSpot(ParkingSpot parkingSpot) {
        out.println("Choose a machine: (0.Back)");
        var machines = MockItemAndVehicleGenerator.machines;
        machines.sort(Comparator.comparing(Machine::getSpaceOccupation).reversed());
        for (int i = 0; i < machines.size(); i++) {
            out.println(i + 1 + ". " + machines.get(i));
        }
        int choiceMachine = sc.nextInt();
        if (choiceMachine == 0) {
            parkingSpotController(parkingSpot);
        }
        if (choiceMachine < 0 || choiceMachine > machines.size()) {
            err.println("No machine found with this index");
        }
        try {
            paringSpotService.add(parkingSpot, machines.get(choiceMachine - 1));
            out.printf("You add %s to your parking place, now %s/%s%n",
                    machines.get(choiceMachine - 1).getName(), parkingSpot.getUsableSpace(), parkingSpot.getUsedSpace());
            addMachineToParkingSpot(parkingSpot);
        } catch (TooManyThingsException e) {
            err.println("Too many things in parking spot, please remove something.");
            parkingSpotController(parkingSpot);
        }
        parkingSpotController(parkingSpot);
    }

    private void addStuffToParkingSpot(ParkingSpot parkingSpot) {
        out.println("Choose a stuff: (0.Back)");
        var stuffs = MockItemAndVehicleGenerator.stuffs;
        stuffs.sort(Comparator.comparing(Stuff::getSpaceOccupation).reversed());
        for (int i = 0; i < stuffs.size(); i++) {
            out.println(i + 1 + ". " + stuffs.get(i));
        }
        int choiceStuff = sc.nextInt();
        if (choiceStuff == 0) {
            parkingSpotController(parkingSpot);
        }
        if (choiceStuff < 0 || choiceStuff > stuffs.size()) {
            err.println("No stuff found with this index");
        }
        try {
            paringSpotService.add(parkingSpot, stuffs.get(choiceStuff - 1));
            out.printf("You add %s to your parking place, now %s/%s%n",
                    stuffs.get(choiceStuff - 1).getName(), parkingSpot.getUsableSpace(), parkingSpot.getUsedSpace());
            addStuffToParkingSpot(parkingSpot);
        } catch (TooManyThingsException e) {
            err.println("Too many things in parking spot, please remove something.");
            parkingSpotController(parkingSpot);
        }
        parkingSpotController(parkingSpot);
    }

    private void removeObjectFromParkingSpot(ParkingSpot parkingSpot) {
        out.println("Remove item or vehicle: (0.Back)");
        for (int i = 0; i < parkingSpot.getParkingSpotObject().size(); i++) {
            out.println(i + 1 + ". " + parkingSpot.getParkingSpotObject().get(i));
        }
        int choiceRemove = sc.nextInt();
        if (choiceRemove == 0) {
            parkingSpotController(parkingSpot);
        }
        if (choiceRemove < 0 || choiceRemove > parkingSpot.getParkingSpotObject().size()) {
            out.println("Wrong item index");
            removeObjectFromParkingSpot(parkingSpot);
        }
        out.println("You just removed " + parkingSpot.getParkingSpotObject().get(choiceRemove - 1).getName() + "!");
        paringSpotService.removeStuff(parkingSpot, parkingSpot.getParkingSpotObject().get(choiceRemove - 1));
        removeObjectFromParkingSpot(parkingSpot);
    }

    public void personAreaRenting(Person person) {
        out.println("Choose Area to rent: \n1.Apartments \n2.Parking Spot \n3.Back \n0.Exit");
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
            default:
                out.println("Wrong argument choose");
                personPage(person);
        }

    }

    public void showParkingSpotRenting(Person person) {
        out.println("Choose parking spot: 0.Back");
        var freeParkSpots = rentAreaPool.getAllFreeParkingSpots();
        freeParkSpots.sort((Comparator.comparing(ParkingSpot::getUsableSpace)));
        for (int i = 0; i < freeParkSpots.size(); i++)
            out.println((i + 1) + ". usable space: " + freeParkSpots.get(i).getUsableSpace() + ", cost: " + new Random().nextInt(500) + "$");

        int choice = sc.nextInt();
        if (choice == 0) {
            personAreaRenting(person);
            return;
        }
        if (choice < 0 || choice > freeParkSpots.size()) {
            showParkingSpotRenting(person);
        }

        var parkingSpot = freeParkSpots.get(choice - 1);
        try {
            personService.addRentArea(person, parkingSpot);
            out.println("You successful rent a parking spot.");
            personPage(person);
        } catch (ProblematicTenantException e) {
            err.println("You can`t rent parking spot without apartments.");
            personPage(person);
        } catch (PoolException e) {
            err.println("Couldn't rent more 5 areas");
            personPage(person);
        } catch (TooManyLettersException e) {
            err.println("You have more that 3 letters of warning. Pay for a rent your already renting areas.");
            personPage(person);
        }
    }

    public void showApartmentsRenting(Person person) {
        out.println("Choose apartments: 0.Back");
        var allFreeApartments = rentAreaPool.getAllFreeApartments();
        allFreeApartments.sort((Comparator.comparing(Apartment::getUsableSpace)));
        for (int i = 0; i < allFreeApartments.size(); i++) {
            out.println((i + 1) + ". usable space: " + allFreeApartments.get(i).getUsableSpace() + ", cost: " + new Random().nextInt(1000) + "$");
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
            out.println("You successful rent an apartment.");
            personPage(person);
        } catch (ProblematicTenantException e) {
            personPage(person);
            /* May not be, because this throws when you try rent parking spot without apartments */
        } catch (PoolException e) {
            err.println("Couldn't rent more 5 areas");
            personAreaRenting(person);
        } catch (TooManyLettersException e) {
            out.println("You have more that 3 letters of warning. Pay for a rent your already renting areas.");
            personPage(person);
        }
    }

    private void showPersonInfo(Person person) {
        out.println("---------------------------------------");
        out.println("name: " + person.getName() + " " + person.getSurname());
        out.println("birthday: " + person.getBirthday());
        out.println("address: " + person.getAddress());
        out.println("apartments:");
        if (person.getApartments().isEmpty() || person.getApartments().size() == 0) {
            out.println("- none.");
        } else {
            showPersonApartmentList(person.getApartments(), false);
        }
        out.println("parking spots:");
        if (person.getParkingSpot().isEmpty() || person.getParkingSpot().size() == 0) {
            out.println("- none.");
        } else {
            showPersonParkingSpotsList(person.getParkingSpot(), false);
        }
        out.println("letters: " + person.getLetters().size());
        out.println("---------------------------------------");
        personPage(person);
    }

    private void showPersonParkingSpotsList(List<ParkingSpot> parkingSpots, boolean indexPresenter) {
        parkingSpots.sort(Comparator.comparing(ParkingSpot::getUsableSpace).reversed());
        for (int i = 0; i < parkingSpots.size(); i++) {
            var parkSpot = parkingSpots.get(i);
            if (indexPresenter) out.println(i + 1 + ".");
            out.println("*id: " + parkSpot.getId());
            out.println(" rent start date: " + parkSpot.getRentStartDate());
            out.println(" rent end date: " + parkSpot.getRentEndDate());
            out.println(" space used: " + parkSpot.getUsableSpace() + "/" + parkSpot.getUsedSpace() + " square meters");
            out.println(" spot stuff and vehicles:");
            var parkingSpotObjects = parkSpot.getParkingSpotObject();
            if (parkingSpotObjects.isEmpty()) {
                out.println("  - none.");
                out.println();
                break;
            }
            for (var parkingSpotObject : parkingSpotObjects) {

                if (parkingSpotObject instanceof Stuff) {
                    var stuff = (Stuff) parkingSpotObject;
                    out.println("  -id: " + stuff.getId());
                    out.println("  -name: " + stuff.getName());
                    out.println("  -takes places: " + stuff.getSpaceOccupation() + " square meters");
                } else if (parkingSpotObject instanceof Machine) {
                    var machine = (Machine) parkingSpotObject;
                    out.println("  -id: " + machine.getId());
                    out.println("  -name: " + machine.getName());
                    out.println("  -machine type: " + machine.getMachineType());
                    out.println("  -engine capacity: " + machine.getEngineCapacity());
                    out.println("  -engine type: " + machine.getEngineType());
                    out.println("  -weels: " + machine.getWeels());
                    out.println("  -takes places: " + machine.getSpaceOccupation() + " square meters");
                }

                out.println();
            }
            out.println();
        }
    }

    private void showPersonApartmentList(List<Apartment> apartments, boolean indexShower) {
        apartments.sort(Comparator.comparing(Apartment::getUsableSpace).reversed());
        for (int i = 0; i < apartments.size(); i++) {
            if (indexShower)
                out.println(i + 1 + ".");
            var apartment = apartments.get(i);
            out.println("*id: " + apartment.getId());
            out.println(" rent start date: " + apartment.getRentStartDate());
            out.println(" rent end date: " + apartment.getRentEndDate());
            out.println(" apartment size: " + apartment.getUsableSpace() + " square meters");
            out.println(" dwellers:");
            if (apartment.getDwellers().isEmpty() || apartment.getDwellers().size() == 0) {
                out.println("  - none.");
            } else {
                for (int j = 0; j < apartment.getDwellers().size(); j++) {
                    var dweller = apartment.getDwellers().get(j);
                    out.println("  id: " + dweller.getId() + " name: " + dweller.getName() + " " + dweller.getSurname());
                }
            }
        }
    }

    private void dataFillerMenu() {
        out.println("What to fill?\n1.Apartment\n2.Parking Place\n3.Person\n4.Back\n0.Exit");
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
                out.println("Incorrect menu item");
                dataFillerMenu();
        }
    }


    private void fillPerson() throws PoolException {
        var person = new Person();
        out.println("Input name:");
        person.setName(sc.next());
        out.println("Input surname:");
        person.setSurname(sc.next());
        out.println("Input birthday **/**/**** format:");
        person.setBirthday(LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        personPool.add(person);
        successfulSaveMessage(person);
        dataFillerMenu();
    }

    private void fillApartment() {
        out.println("Area calculation:\n1.Set area\n2.Calculate by width,length,height\n3.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 0:
                exit();
                break;
            case 1:
                out.println("Input area:");
                var apartWithArea = new Apartment(null, null, sc.nextDouble());
                try {
                    rentAreaPool.add(apartWithArea);
                } catch (PoolException e) {
                    e.printStackTrace();
                }
                successfulSaveMessage(apartWithArea);
                break;
            case 2:
                out.println("Input length,width, height:");
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
                out.println("Incorrect menu item");
                fillApartment();
        }
        fillApartment();
    }

    private void fillParkingSpot() throws PoolException {
        out.println("Area calculation:\n1.Set area\n2.Calculate by width,length,height\n3.Back\n0.Exit");
        switch (sc.nextInt()) {
            case 0:
                exit();
                break;
            case 1:
                out.println("Input area:");
                var parkingSpot = new ParkingSpot(null, null, sc.nextDouble());
                rentAreaPool.add(parkingSpot);
                successfulSaveMessage(parkingSpot);
                break;
            case 2:
                out.println("Input length,width, height:");
                var parkingSpotCalc = new ParkingSpot(null, null, sc.nextDouble(), sc.nextDouble(), sc.nextDouble());
                successfulSaveMessage(parkingSpotCalc);
                rentAreaPool.add(parkingSpotCalc);
                break;
            case 3:
                dataFillerMenu();
                break;
            default:
                out.println("Incorrect menu item");
                fillApartment();
        }
        fillParkingSpot();
    }

    private void successfulSaveMessage(Apartment apartment) {
        out.println("Apartment with id:" + apartment.getId() + "was saved successful to pool!");
    }

    private void successfulSaveMessage(ParkingSpot parkingSpot) {
        out.println("Parking spot with id:" + parkingSpot.getId() + "was saved successful to pool!");
    }

    private void successfulSaveMessage(Person person) {
        out.println(
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
