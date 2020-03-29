package org.kasador.realestatecompany;

import org.kasador.realestatecompany.modeling.TaskInit;
import org.kasador.realestatecompany.pool.PersonPool;
import org.kasador.realestatecompany.pool.RentAreaPool;
import org.kasador.realestatecompany.service.impl.LetterServiceImpl;
import org.kasador.realestatecompany.service.impl.ParingSpotServiceImpl;
import org.kasador.realestatecompany.service.impl.PersonServiceImpl;
import org.kasador.realestatecompany.service.impl.RentAreaServiceImpl;
import org.kasador.realestatecompany.tools.ContextPool;
import org.kasador.realestatecompany.tools.Saver;
import org.kasador.realestatecompany.tools.StandardRestorer;
import org.kasador.realestatecompany.tools.StandardSaver;
import org.kasador.realestatecompany.view.View;

import java.io.IOException;

public class Main {
    private static View view = new View(
            PersonPool.getInstance(),
            RentAreaPool.getInstance(),
            new StandardSaver(),
            new ParingSpotServiceImpl(),
            new PersonServiceImpl(),
            new RentAreaServiceImpl(new LetterServiceImpl()));
    private static ContextPool contextPool = new ContextPool(
            new StandardRestorer(),
            PersonPool.getInstance(),
            RentAreaPool.getInstance());

    public static void main(String[] args) throws IOException {
        new TaskInit(new RentAreaServiceImpl(new LetterServiceImpl())).init();
        contextPool.init();
        view.start();
        //    Runtime.getRuntime().addShutdownHook(new Thread(() -> new StandardSaver().saveAllData()));
    }

}
