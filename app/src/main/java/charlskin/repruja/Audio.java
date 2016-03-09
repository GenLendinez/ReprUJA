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
        adaptador= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,listaCancionesN);
        vistaCanciones.setAdapter(adaptador);
        vistaCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nombre=(TextView) view;
                Intent transicion_menu_audio=new Intent(Audio.this,Menu_reproductor_audio.class);
                transicion_menu_audio.putExtra("Titulo",nombre.getText()); //Manda el dato nombre.getText() con nombre titulo a la siguiente actividad "menu_reproductor_audio
                transicion_menu_audio.putParcelableArrayListExtra("listaCanciones",listaCanciones);
                startActivity(transicion_menu_audio);
            }
        });
    }
    public void getListaCanciones() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int path_fileC= musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String path_file= musicCursor.getString(path_fileC); // ruta absoluta
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                listaCanciones.add(new Cancion(thisTitle,thisArtist,path_file));
                listaCancionesN.add(thisTitle);
            }
            while (musicCursor.moveToNext());
        }
        musicCursor.close();

    }
}
