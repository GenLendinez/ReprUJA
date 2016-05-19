package charlskin.repruja;

import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Esta clase corresponde a la actividad inicial que se ejecuta una vez entras en la aplicación y
 * basicamente es la encargada de coger todas los formatos que se puedan reproducir del dispositivo,
 * posteriormente guardar esos datos en un arrayList de canciones (listaCanciones) y responder cada
 * vez que se pulsa una canción para pasar a la otra actividad
 *
 * Variables:
 * listaCancionesN - Guarda una lista con todos los nombres de la cancion, la utilizaremos
 * para mostrarlas todas en la lista
 * listaCanciones - Guarda una lista con todos los parametros de todas las canciones
 * vistaCanciones - Lista que utilizamos para mostrar todos los nombres
 *
 * Autores:
 * Juan Lendinez Sanchez
 * Rafael Megales Anguita
 */


public class Audio extends AppCompatActivity {

    private ArrayList<String> listaCancionesN;
    private ArrayList<Cancion> listaCanciones;
    private ArrayAdapter<String> adaptador;
    private ListView vistaCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        vistaCanciones=(ListView)findViewById(R.id.listaCanciones);
        listaCancionesN=new ArrayList<String>();
        listaCanciones=new ArrayList<Cancion>();
        getListaCanciones();
        //Mediante el adaptador cogemos los datos del array listaCancionesN para mostrarlos por la lista
        //en forma de lista simple
        adaptador= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaCancionesN);
        vistaCanciones.setAdapter(adaptador);
        //Tambien establecemos un listener que vaya escuchando si alguien pulsa en la lista, para detectar
        //la posicion donde ha pulsado y poder pasar el titulo (como indice) y la lista de canciones
        //a la otra actividad (por eso implementamos la clase Cancion como parceable)
        vistaCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nombre=(TextView) view;
                //Mediante la clase Intent pasamos entre dos aplicaciones
                Intent transicion_menu_audio=new Intent(Audio.this,Menu_reproductor_audio.class);
                transicion_menu_audio.putExtra("Titulo",nombre.getText()); //Manda el dato nombre.getText() con nombre titulo a la siguiente actividad "menu_reproductor_audio
                transicion_menu_audio.putParcelableArrayListExtra("listaCanciones",listaCanciones);
                startActivity(transicion_menu_audio);
            }
        });
    }

    /**
     * En esta función mediante la clase ContentResolver utilizamos un cursor para ir seleccionando
     * todas las canciones de los dispositivos y posteriormente guardarlas en la lista
     */
    public void getListaCanciones() {
        ContentResolver musicResolver = getContentResolver();
        Cursor musicCursor = musicResolver.query(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //Vamos cogiendo del cursor el indice de cada parametro que necesitemos, titulo, artista,ruta de la imagen y de la cancion
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int path_fileC= musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int path_image = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            do {
                //Con el indice ya podemos acceder al dato
                String path_file= musicCursor.getString(path_fileC); // ruta absoluta
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String pathImage = "null";
                //En este caso teniamos que comprobar que el indice de la columna no fuese -1 lo cual
                //significaria que no hay portada para esta imagen
                if (path_image!=-1) {
                    pathImage = musicCursor.getString(path_image);
                }
                //Una vez tenemos todos los parametros los añadimos a la lista
                listaCanciones.add(new Cancion(thisTitle,thisArtist,path_file,pathImage));
                listaCancionesN.add(thisTitle);
            }
            while (musicCursor.moveToNext()); //Vamos iterando a lo largo del cursor
        }
        //Cerramos el cursor una vez acabemos de iterar
        musicCursor.close();


    }
}
