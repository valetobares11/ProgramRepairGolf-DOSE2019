package unrc.dose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Singleton{
    private int port;
    private static Singleton singleton;

    private Singleton(int portLisenig){
        this.port = portLisenig;
        Logger loggerSingleton = LoggerFactory.getLogger(Singleton.class);
        loggerSingleton.info("Lising at "+this.port);
    }

    public static Singleton getSingletonInstance(int portLisenig){
        if(singleton==null){
            singleton = new Singleton(portLisenig);
        }
        else{
            Logger loggerSingleton = LoggerFactory.getLogger(Singleton.class);
            loggerSingleton.info("Cannot hear at "+portLisenig+", Lising at "+singleton.port);

        }
        return singleton;
    }

    //Sobreescribimos el método clone, para que no se pueda clonar un objeto de esta clase
    @Override
    public Singleton clone(){
        try {
            Logger loggerSingleton = LoggerFactory.getLogger(Singleton.class);
            loggerSingleton.info("cloning attempt");
            throw new CloneNotSupportedException();
        } catch (CloneNotSupportedException ex) {
            System.out.println("Cannot clone an object of the Singleton class");
        }
        return null; 
    }
}