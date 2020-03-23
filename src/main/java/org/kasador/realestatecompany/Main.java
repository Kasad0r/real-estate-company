package org.kasador.realestatecompany;

import org.kasador.realestatecompany.modeling.TaskInit;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.impl.LetterServiceImpl;
import org.kasador.realestatecompany.service.impl.ParingSpotServiceImpl;
import org.kasador.realestatecompany.service.impl.PersonServiceImpl;
import org.kasador.realestatecompany.service.impl.RentAreaServiceImpl;
import org.kasador.realestatecompany.tools.StandardRestorer;
import org.kasador.realestatecompany.tools.StandardSaver;
import org.kasador.realestatecompany.view.View;

public class Main {
    private static View view = new View(
            PersonPool.getInstance(),
            RentAreaPool.getInstance(),
            new StandardSaver(),
            new StandardRestorer(),
            new ParingSpotServiceImpl(),
            new PersonServiceImpl(),
            new RentAreaServiceImpl(new LetterServiceImpl()));

    public static void main(String[] args) {
        //   new TaskInit(rentAreaService).init();
        view.start();
        new Main().choseMessage();
    }
    public  void choseMessage() {
        System.out.println("Choose menu item");
    }
}
