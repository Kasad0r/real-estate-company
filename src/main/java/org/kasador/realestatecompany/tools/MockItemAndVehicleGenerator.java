package org.kasador.realestatecompany.tools;

import org.kasador.realestatecompany.domain.spotobjects.EngineType;
import org.kasador.realestatecompany.domain.spotobjects.Machine;
import org.kasador.realestatecompany.domain.spotobjects.MachineType;
import org.kasador.realestatecompany.domain.spotobjects.Stuff;

import java.util.ArrayList;
import java.util.List;

public class MockItemAndVehicleGenerator {
    public static List<Machine> machines = new ArrayList<>() {{
        add(new Machine("Lada Kalina", 10.0, 4, MachineType.CITY_CAR, EngineType.Physical, 1.5));
        add(new Machine("Interfisher 600", 15.0, 0, MachineType.BOAT, EngineType.External_Combustion, 1.1));
        add(new Machine("BMW 2020 F 900 XR", 5.1, 2, MachineType.MOTORCYCLE, EngineType.Internal_Combustion, 0.9));
        add(new Machine("Ford GPA", 13.2, 4, MachineType.AMPHIBIAN, EngineType.External_Combustion, 2.2));
    }};
    public static List<Stuff> stuffs = new ArrayList<>() {{
        add(new Stuff("toothbrush", 0.01));
        add(new Stuff("TV", 0.9));
        add(new Stuff("toilet", 1.0));
        add(new Stuff("bottle with cola (20 pcs)", 0.15));
        add(new Stuff("bag of potatoes", 0.7));
        add(new Stuff("incomprehensible trash", 3.0));
        add(new Stuff("past collection of cowboy boots(6 pairs)", 1.15));
        add(new Stuff("winter tires (4 pcs)", 2.1));
        add(new Stuff("canned cucumbers and tomatoes (mothers-in-law)", 5.0));
        add(new Stuff("wąsy Pana Tomaszewskiego", 0.01));
    }};
}
