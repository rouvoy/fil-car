package fil.car.annuaire.app {
  import fil.car.annuaire.tcp.AnnuaireFactory
  import fil.car.annuaire.AnnuaireImpl

  object Serveur extends Application {
    println("Serveur starting...")
    AnnuaireFactory.squelette(9999, new AnnuaireImpl)
  }
}