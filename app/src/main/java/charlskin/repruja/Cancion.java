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
    public Cancion(String titu,String arti, String path){
        titulo=titu;
        artista=arti;
        ruta=path;
    }
    protected Cancion(Parcel in) {
        titulo = in.readString();
        artista = in.readString();
        ruta = in.readString();
    }
    public String getTitulo(){
        return titulo;
    }
    public String getArtista(){
        return artista;
    }
    public String getRuta(){ return ruta; }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(artista);
        dest.writeString(ruta);
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
