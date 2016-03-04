package charlskin.repruja;

/**
 * Created by Aniki on 27/02/2016.
 */
public class Cancion {
    private String titulo;
    private String artista;
    private long ID;
    private String ruta;
    public Cancion(String titu,String arti, long IDS,String path){
        titulo=titu;
        artista=arti;
        ID=IDS;
        ruta=path;
    }
    public String getTitulo(){

        return titulo;
    }
    public String getArtista(){
        return artista;
    }
    public String getRuta(){
        return ruta;
    }
    public long getID(){
        return ID;
    }
}
