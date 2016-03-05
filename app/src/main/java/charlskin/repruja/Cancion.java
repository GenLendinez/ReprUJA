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
    public Cancion(Parcel dentro){
        String[] datosC= new String[3];
        dentro.readStringArray(datosC);
        datosC[0]=this.titulo;
        datosC[1]=this.artista;
        datosC[2]=this.ruta;
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
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.titulo,
                this.artista,
                this.ruta});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Cancion createFromParcel(Parcel in) {
            return new Cancion(in);
        }
        public Cancion[] newArray(int tama) {
            return new Cancion[tama];
        }
    };
}
