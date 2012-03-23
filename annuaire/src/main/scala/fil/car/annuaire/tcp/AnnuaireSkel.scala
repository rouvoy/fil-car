package fil.car.annuaire.tcp {
  import java.net.{ ServerSocket, Socket }
  import java.io.{ BufferedReader, DataOutputStream, InputStreamReader }
  import fil.car.annuaire.{ Adresse, AnnuaireItf }

  class AnnuaireSkel(port: Int, delegate: AnnuaireItf) {
    lazy val ss = new ServerSocket(port)

    while (true) {
      val clt = new Thread(new AnnuaireClient(ss.accept))
      clt.start
    }

    class AnnuaireClient(socket: Socket) extends Runnable {
      lazy val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
      lazy val out = new DataOutputStream(socket.getOutputStream)

      def run {
        def envoyer[T](data: T) = out writeBytes data.toString + "\n"

        while (true) {
          val requete = in.readLine
          if (requete != null) {
            val request = requete split ";"
            request(0) match {
              case "AJOUTER" =>
                envoyer(delegate.ajouter(request(1), Adresse decode request(2)))
              case "RETIRER" =>
                envoyer(delegate.retirer(request(1)))
              case "MODIFIER" =>
                envoyer(delegate.modifier(request(1), Adresse decode request(2)))
              case "LISTER" => {
                val liste = delegate.lister
                envoyer(liste.size)
                liste foreach { envoyer(_) }
              }
              case "CHERCHER" =>
                delegate chercher request(1) match {
                  case None => envoyer(false)
                  case Some(x) => {
                    envoyer(true)
                    envoyer(x.encode)
                  }
                }
              case x => println("[ERREUR] Méthode non supportée : " + x)
            }
          }
        }
      }
    }
  }
}