package fil.car
import java.net.{ ServerSocket, Socket }
import java.io.{ BufferedReader, DataOutputStream, InputStreamReader }

class AnnuaireSkel(port: Int, delegate: IAnnuaire) {
  lazy val ss = new ServerSocket(port)

  while (true) {
    val clt = new Thread(new AnnuaireClient(ss.accept))
    clt.start
  }

  class AnnuaireClient(socket: Socket) extends Runnable {
    lazy val input = new BufferedReader(new InputStreamReader(socket.getInputStream))
    lazy val output = new DataOutputStream(socket.getOutputStream)

    def run {
      def send[T](data: T) = output writeBytes data.toString + "\n"

      while (true) {
        val in = input.readLine
        if (in != null) {
          val request = in split ";"
          request(0) match {
            case "AJOUTER" => send(delegate.ajouter(request(1), Adresse decode request(2)))
            case "RETIRER" => send(delegate.retirer(request(1)))
            case "MODIFIER" => send(delegate.modifier(request(1), Adresse decode request(2)))
            case "LISTER" => {
              val res = delegate.lister
              send(res.size)
              res foreach { send(_) }
            }
            case "CHERCHER" => delegate chercher request(1) match {
              case None => send(false)
              case Some(x) => {
                send(true)
                send(x.encode)
              }
            }
            case x => println("[ERREUR] La méthode suivante n'est pas supportée : " + x)
          }
        }
      }
    }
  }
}