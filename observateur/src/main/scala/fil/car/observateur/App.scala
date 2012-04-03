package fil.car.observateur
import java.rmi.registry.LocateRegistry

/**
 * Hello world!
 *
 */
object App extends Application {
  val prettyObserver = new ObserverImpl {
    def notify(content: java.io.Serializable) {
      println("RECEIVED >> " + content)
    }
  }

  LocateRegistry createRegistry 1099
  println("Registry started on port 1099")

  Subject create "test" // Server-side (subject holder)
  println("Subject created")

  Subject("test") { s => // Client-side (subject observer)
    s attach prettyObserver
    println("Attaching an observer")
    s notify "Hello World!"
    s detach prettyObserver
    println("Detaching an observer")
    s notify "Hello World again!"
  }
  Subject destroy "test" // Server-side (subject holder)
  println("Subject destroyed")
}
