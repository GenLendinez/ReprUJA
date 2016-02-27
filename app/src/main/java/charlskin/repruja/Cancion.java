package charlskin.repruja;

/**
 * Created by Aniki on 27/02/2016.
 */
public class Cancion {
    private String titulo;
    private String artista;
    private long ID;
    public Cancion(String titu,String arti, long IDS){
        titulo=titu;
        artista=arti;
        ID=IDS;
    }
    public String getTitulo(){

        return titulo;
    }
    public String getArtista(){
    // esto es una prueba
        return artista;
    }
    public long getID(){
        return ID;
    }
}
