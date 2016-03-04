package charlskin.repruja;

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
        adaptador= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listaCancionesN);
        vistaCanciones.setAdapter(adaptador);
        vistaCanciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView nombre=(TextView) view;
                for (int i=0;i<listaCanciones.size();i++){
                    if(listaCanciones.get(i).getTitulo()==nombre.getText()){
                        MediaPlayer reproductor= new MediaPlayer();
                        try {
                            reproductor.setDataSource(listaCanciones.get(i).getRuta());
                            reproductor.prepare();
                            reproductor.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int path_fileC= musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                long thisId = musicCursor.getLong(idColumn);
                String path_file= musicCursor.getString(path_fileC);
                System.out.println(path_file);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                listaCanciones.add(new Cancion(thisTitle,thisArtist,thisId,path_file));
                listaCancionesN.add(thisTitle);
            }
            while (musicCursor.moveToNext());
        }

    }
}
