package charlskin.repruja;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aniki on 27/02/2016.
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
