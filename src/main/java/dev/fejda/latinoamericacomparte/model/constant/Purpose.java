package dev.fejda.latinoamericacomparte.model.constant;

public enum Purpose {
    SERVICE("Servicio"),
    EDIFICA_PROGRAM("Programa EDIFICA"),
    SHOWS_AND_CONFERENCES("Shows y conferencias") ;

    private final String visualName;

    Purpose(String visualName){
        this.visualName =visualName;
    }

   public String getVisualName(){
        return visualName;
   }


}
