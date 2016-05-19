package charlskin.repruja;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Esta clase contiene todos los atributos que hemos considerado necesarios para una cancion
 *
 * Variables:
 * titulo - nombre de la canción
 * artista - nombre del grupo o artista
 * ruta - ruta absoluta de la canción en el dispositivo
 * rutaImagen - ruta absoluta de la portada del album en caso de que tenga, si no tiene sera "null"
 *
 *
 * Autores:
 * Juan Lendinez Sanchez
 * Rafael Megales Anguita
 */
public class Cancion implements Parcelable{
    private String titulo;
    private String artista;
    private String ruta;
    private String rutaImagen;
    public Cancion(String titu,String arti, String path, String path_i){
        titulo=titu;
        artista=arti;
        ruta=path;
        rutaImagen=path_i;
    }
    protected Cancion(Parcel in) {
        titulo = in.readString();
        artista = in.readString();
        ruta = in.readString();
        rutaImagen=in.readString();
    }
    public String getTitulo(){
        return titulo;
    }
    public String getArtista(){
        return artista;
    }
    public String getRuta(){ return ruta; }
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Al implementar la clase como Parceable necesitabamos sobrecargar las siguientes funciones
     * con el fin de pasar canciones entre actividades, estas funciones permiten el paso de array de
     * objetos de la clase cancion entre actividades
     */

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(artista);
        dest.writeString(ruta);
        dest.writeString(rutaImagen);
    }
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cancion> CREATOR = new Parcelable.Creator<Cancion>() {
        @Override
        public Cancion createFromParcel(Parcel in) {
            return new Cancion(in);
        }
        @Override
        public Cancion[] newArray(int size) {
            return new Cancion[size];
        }
    };
}
