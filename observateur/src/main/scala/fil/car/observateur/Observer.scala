package fil.car.observateur {
  import java.rmi.Remote
  import java.rmi.server.UnicastRemoteObject
  import java.rmi.RemoteException
  import java.rmi.Naming
  import java.io.{ Serializable => jSerializable }

  /**
   * Interface exposée par un observateur RMI.
   */
  trait Observer extends Remote {
    @throws(classOf[RemoteException])
    def notify(content: jSerializable)
  }

  /**
   * Implantation partielle d'un observateur RMI.
   */
  trait ObserverImpl extends UnicastRemoteObject with Observer

  /**
   * Interface exposée par un sujet observable.
   */
  trait Subject extends Observer {
    /**
     * Abonner un nouvel observateur
     */
    @throws(classOf[RemoteException])
    def attach(o: Observer)

    /**
     * Désabonner un observateur
     */
    @throws(classOf[RemoteException])
    def detach(o: Observer)
  }

  /**
   * Implantation d'un sujet observable qui est lui-même observateur d'un sujet
   * concret.
   */
  class SubjectImpl extends UnicastRemoteObject with Subject {
    var obs = Set.empty[Observer]

    def attach(o: Observer) {
      obs += o
    }

    def detach(o: Observer) {
      obs -= o
    }

    def notify(content: jSerializable) {
      obs foreach { _.notify(content) }
    }
  }

  /**
   * Méthodes utilitaires pour gérer des sujets observables
   */
  object Subject {
    def create(name: String) {
//      new Thread {
//        override def run {
          Naming.rebind(name, new SubjectImpl)
//        }
//      }.start
    }

    def retrieve(name: String) = Naming.lookup(name).asInstanceOf[Subject]

    def apply(name: String)(f: Subject => Unit) {
      try {
        f(retrieve(name))
      } catch {
        case e => println(e)
      }
    }

    def destroy(name: String) {
      Naming.unbind(name)
    }
  }
}