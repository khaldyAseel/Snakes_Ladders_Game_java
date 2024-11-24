package Model;

public interface GameSubject {
 void registerObserver(GameObserver observer);
 void removeObserver(GameObserver observer);
 void notifyObservers(String name, String difficulty, String tim);
}





